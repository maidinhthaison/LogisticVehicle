package com.linkon.data.local

import com.linkon.domain.local.LocalCache
import com.linkon.domain.local.UserAppSession
import com.linkon.domain.model.user.SessionDataModel
import com.linkon.domain.model.user.UserInfoModel
import com.linkon.utils.KEY_SMS_CODE_LOGIN
import com.linkon.utils.KEY_SMS_CODE_REGISTER
import com.linkon.utils.KEY_USER
import com.linkon.utils.KEY_USER_INFO
import com.linkon.utils.SMS_VERIFICATION_TYPE

class UserAppSessionImpl(private val cache: LocalCache) : UserAppSession {

    private var sessionDataModel: SessionDataModel? = null
    private var userInfoModel: UserInfoModel? = null
    private var smsCodeLogin: String? = null
    private var smsCodeRegister: String? = null

    init {
        sessionDataModel = cache.get(KEY_USER, SessionDataModel::class.java)
        userInfoModel = cache.get(KEY_USER_INFO, UserInfoModel::class.java)
        smsCodeLogin = cache.getString(KEY_SMS_CODE_LOGIN)
        smsCodeRegister = cache.getString(KEY_SMS_CODE_REGISTER)
    }


    override fun getClient(): SessionDataModel? {
        return cache.get(KEY_USER, SessionDataModel::class.java)
    }

    override fun saveClient(sessionDataModel: SessionDataModel?) {
        this.sessionDataModel = sessionDataModel
        cache.put(KEY_USER, this.sessionDataModel)
    }

    override fun clearClient() {
        cache.put(KEY_USER, null)
        sessionDataModel = null
    }

    override fun getUserInfo(): UserInfoModel? {
        return cache.get(KEY_USER_INFO, UserInfoModel::class.java)
    }

    override fun saveUserInfo(userInfoModel: UserInfoModel?) {
        this.userInfoModel = userInfoModel
        cache.put(KEY_USER_INFO, this.userInfoModel)
    }

    override fun clearUserInfo() {
        cache.put(KEY_USER_INFO, null)
        userInfoModel = null
    }

    override fun getSmsCode(type: String): String? {
        if (type == SMS_VERIFICATION_TYPE.LOGIN.value) {
            return cache.getString(KEY_SMS_CODE_LOGIN)
        } else if (type == SMS_VERIFICATION_TYPE.REGISTER.value) {
            return cache.getString(KEY_SMS_CODE_REGISTER)
        }
        return ""
    }

    override fun saveSmsCode(type: String, smsCode: String?) {
        if (type == SMS_VERIFICATION_TYPE.LOGIN.value) {
            this.smsCodeLogin = smsCode
            cache.putString(KEY_SMS_CODE_LOGIN, this.smsCodeLogin)
        } else if (type == SMS_VERIFICATION_TYPE.REGISTER.value) {
            this.smsCodeRegister = smsCode
            cache.putString(KEY_SMS_CODE_REGISTER, this.smsCodeRegister)
        }
    }

    override fun clearSmsCode() {
        cache.putString(KEY_SMS_CODE_LOGIN, null)
        cache.putString(KEY_SMS_CODE_REGISTER, null)
        smsCodeLogin = null
        smsCodeRegister = null
    }

}