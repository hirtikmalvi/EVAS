package com.example.evas.Notifications;

import androidx.annotation.NonNull;

import com.example.evas.MyFirebaseInstanceIDService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseIdService extends MyFirebaseInstanceIDService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseMessaging messaging = FirebaseMessaging.getInstance();
//    String token = messaging.getToken();

}
