import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart' ;
import 'package:flutter_reactive_ble/flutter_reactive_ble.dart';
import 'package:amplify_flutter/amplify_flutter.dart';
import 'models/HeartRateModel.dart';
const Color  activeColor =  Color(0xff323244 ) ;
const Color inactiveColor = Color(0xff24263b) ;

class InfoScreen extends StatefulWidget {
  late String deviceId ;
  late String deviceName ;
  InfoScreen({ required this.deviceId, required this.deviceName});

  @override
  State<InfoScreen> createState() => _InfoScreenState();
}

class _InfoScreenState extends State<InfoScreen> {
  late String deviceid ;
  List<int> response  = [];
 bool responseisloaded = true ;
  late int Data1 ;
 late int Data2 ;
 late int bpmInt;
 final decoder = utf8.decoder ;
  FlutterReactiveBle flutterrecativeble = FlutterReactiveBle();
  // Uuid uuid = Uuid.parse('0000fff0-0000-1000-8000-00805f9b34fb');
  // Uuid txuuid = Uuid.parse('0000fff6-0000-1000-8000-00805f9b34fb');
  // Uuid rxuuid = Uuid.parse('0000fff7-0000-1000-8000-00805f9b34fb');
  Future discoverServices1 ( String deviceid)async{
    BluetoothDevice device = BluetoothDevice.fromId(deviceid);
    await device.connect();
    List<BluetoothService> services = await device.discoverServices();
    services.forEach((service) {
      print(' services : ${service.uuid}');
        for (var char in service.characteristics){
          Guid uid = Guid('0000fff7-0000-1000-8000-00805f9b34fb');
          if(char.uuid == uid){
            char.setNotifyValue(true);
            char.read().then((value) {
              print('the reading ${value}');
            }
            );
            char.value.listen((response) {
              print('the response ${response}');
            });
          }
              // char.setNotifyValue(true);
              // char.value.listen((event) {
              //   print('the response ${event}');
              // });
        }
     });
    

  }

  @override
  void initState() {
    // TODO: implement initState
     deviceid = widget.deviceId ;
    discoverServices1(deviceid);
     // flutterrecativeble.connectToDevice(id: deviceid).listen((connectionstate) {
  //   int index = 0;
  //   if (connectionstate.connectionState == DeviceConnectionState.connecting) {
  //     flutterrecativeble.discoverServices(deviceid).then((services) {
  //       for (var ser in services) {
  //         for (var chara in ser.characteristics) {
  //           if (chara.isReadable) {
  //             var characteristic = QualifiedCharacteristic(
  //                 characteristicId: chara.characteristicId,
  //                 serviceId: ser.serviceId,
  //                 deviceId: deviceid);
  //             flutterrecativeble.readCharacteristic(characteristic).then((
  //                 response) {
  //               print('readable $response');
  //             });
  //             print(
  //                 'this characteristic ${chara.characteristicId} is readable');
  //           } else if (chara.isIndicatable) {
  //             print('this characteristic ${chara
  //                 .characteristicId} is indicatable');
  //             var charateristic = QualifiedCharacteristic(
  //                 characteristicId: chara.characteristicId,
  //                 serviceId: ser.serviceId,
  //                 deviceId: deviceid);
  //             flutterrecativeble.subscribeToCharacteristic(charateristic)
  //                 .listen((response) {
  //               print('indicatable $response');
  //             });
  //           } else if (chara.isNotifiable) {
  //             var charateristic = QualifiedCharacteristic(
  //                 characteristicId: chara.characteristicId,
  //                 serviceId: ser.serviceId,
  //                 deviceId: deviceid);
  //             flutterrecativeble.subscribeToCharacteristic(charateristic)
  //                 .listen((response) {
  //               print('notifiable $response');
  //             },onError: (dynamic error) {
  //               // code to handle errors
  //               print('Subscribe error $error');
  //             });
  //             print('this characteristic ${chara
  //                 .characteristicId} is notifiable');
  //           }
  //         }
  //       }
  //     });

      //   flutterrecativeble.discoverServices(deviceid).then((services){
      //       for(var ser in services){
      //          for(var char in ser.characteristics){
      //            if(char.isReadable){
      //              print(' is ${char.isReadable}');
      //     final characteristic =  QualifiedCharacteristic(characteristicId: char.characteristicId, serviceId: ser.serviceId, deviceId: deviceid);
      //       flutterrecativeble.readCharacteristic(characteristic).then((value){
      //         if (value.isNotEmpty) {
      //           setState(() {
      //             responseisloaded = false;
      //             for(var rsp in value) {
      //               print('the response without decoding $rsp');
      //               print('the response with decoding ${String.fromCharCode(rsp)}');
      //             }
      //             // for (int i = 0 ; i<=3 ; i++) {
      //             //   if(i == 0){
      //             //     Data1 = value[i];
      //             //   } else if  (i == 3){
      //             //     bpmInt = value[i];
      //             //   } else if (i == 1){
      //             //     Data2 = value[i];
      //             //   }
      //             // }
      //           });
      //
      //         }
      //       });
      //            }
      //          }
      //       }
      //   });
  //   }
  // });
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    String deviceid = widget.deviceId ;

    return Scaffold(
      backgroundColor: inactiveColor,
      body: SafeArea(
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Container(
                  child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
          children: [
            responseisloaded ? Text('0', style: TextStyle(color: Colors.white, fontSize: 25),) : Text(Data2.toString(), style: TextStyle(
                color: Colors.white,
                fontSize: 25
            ),),

          ],
        ),
                  height : 200 ,
                  width: 150,
                  margin: EdgeInsets.all(15),
                  decoration: BoxDecoration(
                    color: activeColor,
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                Container(
                  height: 200,
                  width: 150,
                  margin: EdgeInsets.all(15),
                  decoration: BoxDecoration(
                    color: activeColor,
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                     responseisloaded ? Text('0', style: TextStyle(fontSize: 25, color: Colors.white),) : Text(bpmInt.toString(), style: TextStyle(
                          color: Colors.white,
                          fontSize: 25
                      ),),
                      Text('Bpm',style: TextStyle(
                          color: Colors.white ,
                          fontSize: 18
                      ),),
                    ],
                  ),
                ),
              ],
            ),
            Container(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  responseisloaded ? Text('0', style: TextStyle(color: Colors.white, fontSize: 25),) : Text(Data1.toString(), style: TextStyle(
                      color: Colors.white,
                      fontSize: 25
                  ),),

                ],
              ),
              height: 200,
              width: 150,
              margin: EdgeInsets.all(15),
              decoration: BoxDecoration(
                color: activeColor,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            SizedBox(height: 100,) ,
           responseisloaded ? RefreshProgressIndicator()
               : Container(

              decoration: BoxDecoration(
                color: Color(0XFFE94560)
              ),
              child:  TextButton(onPressed: ()async{

                final item = HeartRateModel(
                  data1: Data1.toString() ,
                  data2: Data2.toString(),
                  data : bpmInt.toString()
                );
               await Amplify.DataStore.save(item);
              } , child: Text('Save data', style: TextStyle(color: Colors.white, fontSize: 15),)),
            )
          ],
        ),
      ),
    );
  }
}
// flutterrecativeble.readCharacteristic(event.characteristic).then((
//     value) {
//   print(value.length);
//   if (value.isNotEmpty) {
//     setState(() {
//       responseisloaded = false;
//       for (var rsp in value) {
//         print('the response without decoding $rsp');
//         print('the response with decoding ${String.fromCharCode(rsp)}');
//       }
//     });
//   }
// });



//   print('this is the service ${ser.serviceId}');
