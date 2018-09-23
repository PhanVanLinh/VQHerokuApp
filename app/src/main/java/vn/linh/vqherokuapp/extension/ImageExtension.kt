package vn.linh.vqherokuapp.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?, placeHolder: Int = -1,
    isCircle: Boolean = false) {
    loadImage(this, url, null, placeHolder, isCircle)
}

private fun loadImage(imageView: ImageView, url: String?, uri: Uri?,
    placeHolder: Int = -1,
    isCircle: Boolean = false) {
    var requestBuilder: RequestBuilder<*> = Glide.with(imageView.context).load(uri ?: url)
    var requestOptions = RequestOptions()
    if (placeHolder != -1) {
        requestOptions = requestOptions.placeholder(placeHolder)
    }
    if (isCircle) {
        requestBuilder.apply(RequestOptions.circleCropTransform())
    }
    requestBuilder = requestBuilder.apply(requestOptions)
    requestBuilder.into(imageView)
}
