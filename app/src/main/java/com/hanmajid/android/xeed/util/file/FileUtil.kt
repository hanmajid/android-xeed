package com.hanmajid.android.xeed.util.file

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets

object FileUtil {

    private fun getEncryptedFile(
        context: Context,
        directory: File,
        filename: String,
        masterKey: MasterKey,
    ): EncryptedFile {
        return EncryptedFile.Builder(
            context,
            File(directory, filename),
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }

    private fun checkFileExists(
        directory: File,
        filename: String,
    ): Boolean {
        val existingFile = File(directory, filename)
        if (!existingFile.exists()) {
            throw Exception("File does not exist UwU.")
        }
        return true
    }

    fun writeEncryptedFile(
        context: Context,
        masterKey: MasterKey,
        filename: String,
        directory: File,
        content: String,
    ) {
        // Delete the file first if it exists.
        val existingFile = File(directory, filename)
        if (existingFile.exists()) {
            Log.wtf(TAG, "File exists, deleting first...")
            existingFile.delete()
        }

        val encryptedFile = getEncryptedFile(
            context,
            directory,
            filename,
            masterKey,
        )

        val fileContent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            content
                .toByteArray(StandardCharsets.UTF_8)
        } else {
            content.toByteArray()
        }
        encryptedFile.openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }
    }

    fun readEncryptedFile(
        context: Context,
        masterKey: MasterKey,
        filename: String,
        directory: File,
    ): String {
        // Check first whether the file exists.
        checkFileExists(directory, filename)

        val encryptedFile = getEncryptedFile(
            context,
            directory,
            filename,
            masterKey,
        )

        val inputStream = encryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String(byteArray, StandardCharsets.UTF_8)
        } else {
            String(byteArray)
        }
    }

    fun writeFileInternalStorage(
        context: Context,
        filename: String,
        content: String,
    ) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }


    fun readFileInternalStorage(
        context: Context,
        filename: String,
    ): String {
        // Check first whether the file exists.
        checkFileExists(context.filesDir, filename)

        context.openFileInput(filename).bufferedReader().useLines { lines ->
            return lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }

    @Suppress("unused")
    private const val TAG = "FileUtil"
}