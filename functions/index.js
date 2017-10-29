'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// This registration token comes from the client FCM SDKs.
var registrationToken = "df2u3DscV-E:APA91bEya5Wayk2qtR_0X_nhw6_bakDA3blsgv5n9xghi4PQmtq4jOYzhcIRsz0b6sO-VMlVpehrTh-Ek67GHCsk7pDo5cvA1N-CLp50dcDNi652AnTNVZcVa3heNqERBVvA4SLslNpL";

// See the "Defining the message payload" section below for details
// on how to define a message payload.
var payload = {
  data: {
    message: "850",
    key: "2:45",
    userId: "123"
  }
};

// Send a message to the device corresponding to the provided
// registration token.
exports.sendNotification = admin.messaging().sendToDevice(registrationToken, payload)
  .then(function(response) {
    // See the MessagingDevicesResponse reference documentation for
    // the contents of response.
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });
