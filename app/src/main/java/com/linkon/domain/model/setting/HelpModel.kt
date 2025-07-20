package com.linkon.domain.model.setting

data class HelpModel (
    val code : Int? = null,
    val msg : String? = null,
    val dataResponse : List<HelpItemModel>? = null
)