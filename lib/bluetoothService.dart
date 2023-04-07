
import 'package:flutter/services.dart';

class BluetoothService {
  static const MethodChannel _channel =
  const MethodChannel('com.example.bluetooth');

   Future connectTDevice (String deviceId)async{
    try {
     var result  =  await _channel.invokeMethod('connectToDevice', deviceId);
     return result ;
    } on PlatformException catch (e) {
      print('Failed to connect to device: ${e.message}');
    }
  Future readCharacterstics ()async{
      var response ;
      try {
        response = await _channel.invokeMethod('readCharacteristic') ;
      } on PlatformException catch(e){
        print('failed to read characteristic : ${e.message}');
      }
  }
  }

}
