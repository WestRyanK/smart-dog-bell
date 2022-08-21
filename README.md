# About the Project

Smart Dog Bell allows you to use an old Android smart phone as a doggy doorbell. When the screen is touched, a custom sound effect is played on the device. Eventually, the app will support sending push alerts to connected phones via Firebase Cloud Messaging, and broadcasting a message on connected Google Home devices.

While it may seem like overkill, an old smartphone is the perfect device for this application:
* A smart phone has a touch screen which is very sensitive compared to many of the cheap buttons out there. Easy feedback means easier dog training.
* A smart phone can play arbitrary mp3 files. Most "dog buttons" use a cheap microphone to record audio, which results in awful sound quality. Most IoT buttons don't have a speaker.
* A smart phone connects directly to wifi, and it is easy to switch the network it is connected to. Most IoT programmable buttons either connect via Bluetooth to your phone when an app is open, or connect to a hub which must be plugged into a router. Those solutions are difficult to bring to someone else's house for a day. However, once you connect to someone's wifi network with a phone, it will auto-connect on the next visit.
* A smart phone has high build quality. Since there aren't really other products that meet the requirements listed above, I would need to build my own IoT device using something like an ESP8266 wifi board with a button and speaker. However, that would take a lot of work to build a case for it that can withstand a dog licking and scratching it.
* I already had this old phone, so I didn't need to buy and build any hardware. That really simplifies the process and is cheaper.

# How to Use
1. Open the Android Studio `android_app` project.
2. Build and deploy the app to your phone.

## How to Pin the Window
Since the dog will be licking, scratching, and booping the phone, there is a chance he/she can navigate away from the app. You can pin the app window to prevent accidental navigation:

1. On the Android phone, navigate to: Settings > Lock Screen & Security > Other Security Settings > Pin Windows.
2. Turn "Pin Windows" on.
3. Open the Smart Dog Bell app.
4. Press the "Recent Apps" button and click the pin on the bottom right corner of the app window.
5. Click "start" in the popup window.
6. To unpin the app window, press and hold both the "Recent Apps" and "Back" buttons for several seconds.