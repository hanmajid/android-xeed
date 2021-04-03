package com.hanmajid.android.xeed.util.security

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature

object SecurityUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun generateNewAlias(
        keyStoreAlias: String,
        purposes: Int = KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY,
        digests: List<String> = listOf(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512),
    ) {
        val keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC,
            "AndroidKeyStore"
        )
        val parameterSpec: KeyGenParameterSpec =
            KeyGenParameterSpec.Builder(keyStoreAlias, purposes).run {
                setDigests(*digests.toTypedArray())
                build()
            }
        keyPairGenerator.initialize(parameterSpec)
        keyPairGenerator.generateKeyPair()
    }

    private fun checkKeyStorePrivateEntryExists(
        keyStore: KeyStore,
        keyStoreAlias: String,
    ): KeyStore.PrivateKeyEntry {
        try {
            val entry: KeyStore.Entry = keyStore.getEntry(keyStoreAlias, null)
            if (entry !is KeyStore.PrivateKeyEntry) {
                throw Exception("'$keyStoreAlias' is not an instance of a PrivateKeyEntry UwU")
            }
            return entry
        } catch (e: NullPointerException) {
            throw Exception("'$keyStoreAlias' does not exist in KeyStore UwU")
        }
    }

    fun signData(
        keyStore: KeyStore,
        keyStoreAlias: String,
        data: ByteArray,
    ): ByteArray {
        val entry = checkKeyStorePrivateEntryExists(keyStore, keyStoreAlias)
        return Signature.getInstance("SHA256withECDSA").run {
            initSign(entry.privateKey)
            update(data)
            sign()
        }
    }

    fun verifyData(
        keyStore: KeyStore,
        keyStoreAlias: String,
        signature: ByteArray,
        data: ByteArray,
    ): Boolean {
        val entry = checkKeyStorePrivateEntryExists(keyStore, keyStoreAlias)
        return Signature.getInstance("SHA256withECDSA").run {
            initVerify(entry.certificate)
            update(data)
            verify(signature)
        }
    }
}