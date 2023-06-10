importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js'); // Initialize

const firebaseConfig = {
    apiKey: "AIzaSyCbMS7cEdVZ7FPm2c55mBhsSPtFc0uGJek",
    authDomain: "fcmtest-b8dc9.firebaseapp.com",
    projectId: "fcmtest-b8dc9",
    storageBucket: "fcmtest-b8dc9.appspot.com",
    messagingSenderId: "933837403536",
    appId: "1:933837403536:web:3b0fdf5c3836e56bd72f15",
    measurementId: "G-CZSN4BQ2NT"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function (payload) {
    const title = "Hello World";
    const options = { body: payload.data.status };
    return self.registration.showNotification(title, options); });