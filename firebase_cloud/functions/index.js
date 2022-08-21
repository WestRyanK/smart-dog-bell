const functions = require("firebase-functions");
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//

// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

exports.helloWorld = functions.https.onRequest(async (request, response) => {
    functions.logger.info("Hello logger world!");
    response.send("Firebase says hello!");
});

exports.sendDogAlert = functions.https.onRequest(async (request, response) => {
    const FCMToken = "";
    var message = "This is not a drill";
    const payload = {
        token: FCMToken,
        notification: {
            title: 'cloud function demo',
            body: message
        },
        data: {
            body: message,
        }
    };
    admin.messaging().send(payload).then((response) => {
            console.log('Successfully sent message:', response);
            return {success: true};
    }).catch();
    response.send("Send Dog Alert!");
});