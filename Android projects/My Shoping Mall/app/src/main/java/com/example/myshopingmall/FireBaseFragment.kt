package com.example.myshopingmall

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

abstract  class FireBaseFragment :Fragment(){

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mContext: Context
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        val user = firestore.collection("USERS")
    }

    fun showToast(message :String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

}