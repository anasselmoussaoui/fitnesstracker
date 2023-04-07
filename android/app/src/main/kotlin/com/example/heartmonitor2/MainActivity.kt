package com.example.heartmonitor2
import android.Manifest
import android.bluetooth.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Log.println
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.*
import com.example.heartmonitor2.blesdk1810.Util.BleSDK
import com.example.heartmonitor2.blesdk1810.callback.DataListener1810

class MainActivity: FlutterActivity() {
    private var CHANNEL = "com.example.bluetooth"
    private var methodChannel : MethodChannel? = null ;
    private lateinit var bleHandler: BLEDeviceHandler
    private var mContext : Context? = null ;
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        bleHandler = BLEDeviceHandler(this)
       MethodChannel(flutterEngine.dartExecutor.binaryMessenger , CHANNEL).setMethodCallHandler(bleHandler)
    }




}



class BLEDeviceHandler(private val context: Context) : MethodChannel.MethodCallHandler  {

    private var bluetoothGatt: BluetoothGatt? = null
    private var callback: MethodChannel.Result? = null

    companion object : DataListener1810 {
        private const val SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb"
        private const val CHARACTERISTIC_UUID = "0000fff7-0000-1000-8000-00805f9b34fb"
        override fun dataCallback(map: MutableMap<String, Any>?) {
            if (map != null) {
                map.get(key = "dataType")
                Log.i(map.toString(),map.toString())
            } else{

            }
        }

    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "connectToDevice" -> {
                val deviceAddress = call.argument<String>("deviceAddress")!!
                connectToDevice(deviceAddress, result)
            }
            "readCharacteristic" -> {
                readCharacteristic(result)
            }
            "disconnect" -> {
                disconnect(result)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    private fun connectToDevice(deviceAddress: String, result: MethodChannel.Result) {
        callback = result
        val bluetoothAdapter: BluetoothAdapter

        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothAdapter = bluetoothManager!!.adapter


        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        bluetoothGatt = bluetoothDevice.connectGatt(context, false, gattCallback)
    }

    private fun readCharacteristic(result: MethodChannel.Result) {
        val service = bluetoothGatt?.getService(UUID.fromString(SERVICE_UUID))
        val characteristic = service?.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID))

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        bluetoothGatt?.setCharacteristicNotification(characteristic, true)



        callback = result
    }

    private fun disconnect(result: MethodChannel.Result) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        bluetoothGatt?.disconnect()
        bluetoothGatt = null

        result.success("Device disconnected")
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                bluetoothGatt?.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                callback?.error("Connection failed", "Device disconnected", null)
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            if (status == BluetoothGatt.GATT_SUCCESS) {
                val characteristic = gatt?.getService(UUID.fromString(SERVICE_UUID))
                    ?.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID))

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                bluetoothGatt?.setCharacteristicNotification(characteristic, true)



                callback?.success("Device connected and characteristic notification set up.")
            } else {
                callback?.error("Service discovery failed", "Status: $status", null)
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            if (characteristic != null) {
                if (gatt != null) {
                    if (value != null) {
                        super.onCharacteristicChanged(gatt, characteristic, value)
                    }
                }
            }
            if (characteristic?.uuid.toString() == CHARACTERISTIC_UUID) {
                BleSDK.DataParsingWithData(value,BLEDeviceHandler)
            }
        }


    }


}

//
//override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
//    if (newState == STATE_CONNECTED) {
//        mIsConnected = true
//        mCallback.onConnected()
//
//        Log.i(TAG, "Connected to GATT server.")
//    } else if (newState == STATE_DISCONNECTED) {
//        mIsConnected = false
//        mCallback.onDisconnected()
//
//        Log.i(TAG, "Disconnected from GATT server.")
//    }
//}
//
//override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
//    if (status == GATT_SUCCESS) {
//        val service: BluetoothGattService? = gatt.getService(UUID.fromString("YOUR_SERVICE_UUID"))
//        val characteristic: BluetoothGattCharacteristic? = service?.getCharacteristic(UUID.fromString("YOUR_CHARACTERISTIC_UUID"))
//
//        if (ActivityCompat.checkSelfPermission(
//                mContext,
//                Manifest.permission.BLUETOOTH_CONNECT
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        gatt.readCharacteristic(characteristic)
//    } else {
//        Log.w(TAG, "onServicesDiscovered received: $status")
//    }
//}
//
//@Deprecated("Deprecated in Java")
//override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
//    if (status == GATT_SUCCESS) {
//        val data: ByteArray? = characteristic.value
//        data?.let {
//            mCallback.onCharacteristicRead(it)
//        }
//    } else {
//        Log.w(TAG, "onCharacteristicRead received: $status")
//    }
//}



