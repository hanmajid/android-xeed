# android-xeed

⚙️ Work in progress ⚙️

## Hilt DI Module Classes

1. SecurityModule: provides `MasterKey`, Android `KeyStore`
2. SharedPreferencesModule: provides `EncryptedSharedPreferences` for API level >= 21 (`SharedPreferences` otherwise)

## Util Classes

1. FileUtil: read/write files & read/write encrypted files in internal storage
2. SecurityUtil: generate new private key (API level >= 23) & sign/verify data