import 'package:flutter/material.dart';
// Amplify Flutter Packages
import 'package:amplify_flutter/amplify_flutter.dart';
import 'package:amplify_datastore/amplify_datastore.dart';
import 'package:amplify_api/amplify_api.dart'; // UNCOMMENT this line after backend is deployed
import 'package:flutter_reactive_ble/flutter_reactive_ble.dart';
import 'package:heartmonitor2/SerachforDeviceScreen.dart';
import 'package:geolocator/geolocator.dart';
import 'amplifyconfiguration.dart';
 import 'models/ModelProvider.dart';
// Generated in previous step

void main() async{
  WidgetsFlutterBinding.ensureInitialized();
  LocationPermission permission;
  permission = await Geolocator.requestPermission();
  runApp(const MyApp());
}
const Color  activeColor =  Color(0xff323244 ) ;
const Color inactiveColor = Color(0xff24263b) ;

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ) ,
      home:  MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool _amplifyConfigured = false;

  final flutterble = FlutterReactiveBle();
  void _configureAmplify() async {

     await Amplify.addPlugin(AmplifyAPI());
    await Amplify.addPlugin(AmplifyDataStore(modelProvider: ModelProvider.instance));

    // Once Plugins are added, configure Amplify
    await Amplify.configure(amplifyconfig);
    try {
      setState(() {
        _amplifyConfigured = true;
      });
    } catch (e) {
      print(e);
    }
  }
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _configureAmplify();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: Color(0XFF16213E),
      body: SafeArea( 
        
        child: Column(
          children: [
            Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    decoration: BoxDecoration(
                      color: Color(0XFF16213E).withOpacity(0.3),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: GestureDetector(
                      child: Text('Unbound watch ' , style: TextStyle(fontSize: 18, color: Colors.white) , ),
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context)=>SecondScreen() ));
                      }),
                  ),
                  Icon(Icons.arrow_right_outlined, color: Colors.white,)
                ],
              ),
            ),
            SizedBox(height: 10,) ,

          ],
        ),
      )

    );
  }
}
