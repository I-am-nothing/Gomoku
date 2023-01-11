package com.example.gomoku.service;

public class FirebaseGomokuProvider extends GomokuProvider{

    public static FirebaseGomokuProvider firestore() {
        return new FirebaseGomokuProvider();
    }

    private FirebaseGomokuProvider() {

    }
}
