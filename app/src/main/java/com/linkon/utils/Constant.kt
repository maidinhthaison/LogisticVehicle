package com.linkon.utils


const val APP_SESSION_PREFS_SECURE = "app_session_prefs_secure"

const val LIMIT_DEFAULT : Int = 10
const val TOP_LIMIT_DEFAULT : Int = 5

// User

//const val MOCK_PHONE = "17754947926" // 1XX-XXXX-XXXX
//const val MOCK_CODE = "7890"
const val KEY_USER = "user"
const val KEY_USER_INFO = "user_info"
const val KEY_ORDER_NO = "orderNo"
const val KEY_SMS_CODE_LOGIN = "sms_code_login"
const val KEY_SMS_CODE_REGISTER = "sms_code_register"
const val CLIENT_ID = "app-driver"

// Datetime const
const val DATE_TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
const val DATE_ORDER_DATETIME = "yyyy-MM-dd HH:mm"
const val DATE_BAR_CHART_DATETIME = "MM-dd"
const val DATE_ORDER_DATE = "yyyy-MM-dd"
const val DATE_COMMON_FORMAT = "yyyy-MM-dd"

const val DATE_TIME_ACTIVITY_LOG_SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val DATE_TIME_ACTIVITY_LOG_FORMAT = "yyyy/M/dd\nHH:mm"

const val CHAR_SPLIT = "@@"

const val RETRY_GET_CODE_TIME : Long = 30000

enum class SMS_VERIFICATION_TYPE(val value: String) {
    LOGIN("login"),
    REGISTER("register")
}

enum class SORT{
    ASC,
    DESC
}