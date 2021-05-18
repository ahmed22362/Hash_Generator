package com.example.hashapp

import android.net.IpSecAlgorithm
import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel() {

    fun getHash(plainText: String , algorithm: String): String{
        val bytes = MessageDigest.getInstance(algorithm).digest(plainText.toByteArray())
        return toHex(bytes)

    }

    private fun toHex(byteArray: ByteArray): String{ val hashed  =
        Log.d("TAG", "toHex: "+ byteArray.joinToString("") { "%02x".format(it) })

        return byteArray.joinToString("") { "%02x".format(it) }
    }

}