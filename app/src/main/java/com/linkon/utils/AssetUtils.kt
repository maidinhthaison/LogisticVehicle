package com.linkon.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream

object AssetUtils {
    fun loadImageFromAssets(context: Context, fileName: String, imageView: ImageView){
        val assetManager = context.assets
        try {
            val ims: InputStream = assetManager.open(fileName)
            val d : Drawable? = Drawable.createFromStream(ims, null)
            imageView.setImageDrawable(d)
        }catch (e : IOException){
            return
        }
    }
}