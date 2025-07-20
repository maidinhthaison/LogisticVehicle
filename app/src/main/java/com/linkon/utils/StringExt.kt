package com.linkon.utils

import androidx.core.text.HtmlCompat

fun splitString(productImage: String?): List<String> {
    return  productImage!!.split(CHAR_SPLIT)
}

fun formatHtml(html: String): String {
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}