package com.linkon.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.linkon.domain.local.LocalCache
import androidx.core.content.edit

class LocalCacheImpl (private val sharedPreferences: SharedPreferences) : LocalCache {
    override fun putString(key: String, value: String?) {
        sharedPreferences.edit() { putString(key, value) }
    }

    override fun getString(key: String, default: String?): String? {
        return sharedPreferences.getString(key, default)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit() { putBoolean(key, value) }
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    override fun <T> put(key: String, value: T?) {
        val json = Gson().toJson(value)
        sharedPreferences.edit() { putString(key, json) }
    }

    override fun <T> get(key: String, classOfT: Class<T>, default: T?): T? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            Gson().fromJson(json, classOfT)
        } else {
            default
        }
    }

    companion object {
        fun getSharedPreferences(context: Context, name: String): SharedPreferences {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            return EncryptedSharedPreferences.create(
                name, // A file name for the preferences
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

}