import 'dart:async';
import 'InfoScreen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_reactive_ble/flutter_reactive_ble.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'main.dart';
class SecondScreen extends StatefulWidget {
  const SecondScreen({Key? key}) : super(key: key);

  @override
  State<SecondScreen> createState() => _SecondScreenState();
}

class _SecondScreenState extends State<SecondScreen> {
  StreamController _controller = StreamController();
  FlutterBluePlus flutterBlue = FlutterBluePlus.instance;
  final flutterblereactive = FlutterReactiveBle();
   var _foundBleUARTDevices = [];
  final  String idddd = 'D3:58:8C:C1:5F:F3';
   BluetoothDevice device = BluetoothDevice.fromId('D3:58:8C:C1:5F:F3');
  bool isloading = true;
  List<int> response  = [];
  @override
  void initState() {
    // TODO: implement initState
    flutterblereactive.initialize();
     flutterblereactive.scanForDevices(withServices: []).listen((device) {

       if (_foundBleUARTDevices.every((element) => element.id != device.id)) {
             setState(() {
               isloading = false;
             });
         _foundBleUARTDevices.add(device);
       }
     });

  }

  void getServices()async {

  }
  @override
  Widget build(BuildContext context) {
    var size = MediaQuery.of(context).size;
    return Scaffold(
      backgroundColor: Color(0XFF16213E),
      body: SafeArea(
        child:
          Column(
            children: [
              Center(
                child: Text('Search for device', style: TextStyle(
                    fontWeight: FontWeight.w500 ,
                    fontSize: 30,
                  color: Colors.white
                  ),),
              ),
          isloading ? RefreshProgressIndicator() : Expanded(
            child: ListView.builder(
              itemCount: _foundBleUARTDevices.length,
              itemBuilder: (BuildContext context, int index) {
              if(_foundBleUARTDevices.isNotEmpty) {
                String deviceId = _foundBleUARTDevices[index].id;
                return GestureDetector(
                  onTap: () {
                   Navigator.push(context , MaterialPageRoute(builder: (context)=> InfoScreen(deviceId: deviceId) ));
                  },
                  child: Row(
                    children: [
                      Container(
                        width : size.width - 50,
                        height: 70,
                        margin : EdgeInsets.all(24),
                        decoration: BoxDecoration(  
                          color : Color(0xff323244),
                          borderRadius: BorderRadius.circular(12)
                        ),
                        child: Center(
                          child: Text(
                          deviceId, style: TextStyle( 
                            color: Colors.white,  
                            fontSize: 20,
                            
                          ),
                          ),
                        ),
                      )
                    ],
                  ),
                );
              }
             },),
          )
      ],
    )));
  }
}
//
// flutterblereactive.connectToDevice(id: deviceId).listen((connectionstate) async{
//   print(connectionstate.connectionState);
//     if(connectionstate.connectionState == DeviceConnectionState.connecting){
//       print('Device is connecting');
//       flutterblereactive.discoverServices(deviceId).then((services) {
//         var ser = services[0];
//           for (var char in ser.characteristicIds) {
//             final characteristic = QualifiedCharacteristic(characteristicId:  char, serviceId: ser.serviceId, deviceId: deviceId);
//             flutterblereactive.readCharacteristic(characteristic).then((value) {
//                                for (var rsp in value){
//                              print(' this is response ${rsp}');
//
//                               }
//             });
//         }
//       });
// final characterisric = QualifiedCharacteristic(characteristicId: characteristicuuid, serviceId: serviceuuid , deviceId: deviceId);
//     flutterblereactive.subscribeToCharacteristic(characterisric).listen((event) {
//     for (var rsp in event){
//       print(rsp);
//     }
//    });

