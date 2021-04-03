package com.hanmajid.android.xeed.ui

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.security.crypto.MasterKey
import com.hanmajid.android.xeed.R
import com.hanmajid.android.xeed.util.security.SecurityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.security.KeyStore
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var masterKey: MasterKey

    @Inject
    lateinit var keyStore: KeyStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        with(sharedPreferences.edit()) {
//            putString("DUMMY", "Hello World!")
//            commit()
//        }
//        Log.wtf(TAG, sharedPreferences.getString("DUMMY", "NULL"))

//        FileUtil.writeEncryptedFile(
//            requireContext(),
//            masterKey,
//            "hello_enc.txt",
//            requireContext().filesDir,
//            "Hello world replaced"
//        )
//        try {
//            val text = FileUtil.readEncryptedFile(
//                requireContext(),
//                masterKey,
//                "hello_enc.txt",
//                requireContext().filesDir,
//            )
//            Log.wtf(TAG, text)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        FileUtil.writeFileInternalStorage(
//            requireContext(),
//            "hello_unenc.txt",
//            "Hello world unencrypted replaced"
//        )
//        try {
//            val text = FileUtil.readFileInternalStorage(
//                requireContext(),
//                "hello_unenc.txt",
//            )
//            Log.wtf(TAG, text)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        val aliases: Enumeration<String> = keyStore.aliases()
        while (aliases.hasMoreElements()) {
            Log.wtf(TAG, aliases.nextElement())
        }

        val newKeyStoreAlias = "newAlias4"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SecurityUtil.generateNewAlias(newKeyStoreAlias)
        }

        val data = "Sign this data please"
        try {
            val signature = SecurityUtil.signData(
                keyStore,
                newKeyStoreAlias,
                data.toByteArray()
            )
            val valid = SecurityUtil.verifyData(
                keyStore,
                newKeyStoreAlias,
                signature,
                data.toByteArray(),
            )
            Log.wtf(TAG, "valid: $valid")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "HomeFragment"
    }
}