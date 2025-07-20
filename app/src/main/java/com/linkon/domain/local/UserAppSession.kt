package com.linkon.domain.local

import com.linkon.domain.model.user.SessionDataModel
import com.linkon.domain.model.user.SessionModel
import com.linkon.domain.model.user.UserInfoModel

interface UserAppSession {

    fun getClient(): SessionDataModel?
    fun saveClient(sessionDataModel: SessionDataModel?)
    fun clearClient()

    fun getUserInfo(): UserInfoModel?
    fun saveUserInfo(userInfoModel: UserInfoModel?)
    fun clearUserInfo()

    fun getSmsCode(type: String): String?
    fun saveSmsCode(type: String, smsCode: String?)
    fun clearSmsCode()
}