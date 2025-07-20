package com.linkon.utils

import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.linkon.R

fun loadImageFitCenter(context: Context, url: String?, imageView: AppCompatImageView) {
    val options: RequestOptions = RequestOptions()
        .fitCenter()
        .placeholder(R.drawable.ic_shipping_receipt_thumb)
        .error(R.drawable.ic_launcher_background)
    Glide.with(context)
        .load(url)
        .transform(FitCenter())
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)

}

fun loadImageCenterCrop(context: Context, url: String?, imageView: AppCompatImageView) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_shipping_receipt_thumb)
        .error(R.drawable.ic_launcher_background)
    Glide.with(context)
        .load(url)
        .transform(CenterCrop())
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)

}

fun loadImageShippingReceiptFromUri(context: Context, fileUri: Uri, imageView: AppCompatImageView) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_shipping_receipt_thumb)
        .error(R.drawable.ic_launcher_background)
    Glide.with(context)
        .load(fileUri)
        .transform(CenterCrop())
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
}