package com.example.meowspace.view

import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
@Composable
fun PaymentScreen(snapToken: String, navController: NavController) {
    val context = LocalContext.current
    val previousRoute = navController.previousBackStackEntry?.destination?.route

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        if (url?.contains("finish-success") == true) {
                            when (previousRoute) {
                                "detail" -> {
                                    navController.navigate("market") {
                                        popUpTo("detail") { inclusive = true }
                                    }
                                }
                                "bookhealth" -> {
                                    navController.navigate("chat") {
                                        popUpTo("bookhealth") { inclusive = true }
                                    }
                                }
                                else -> {
                                    navController.navigate("home")
                                }
                            }
                        }
                    }

                }

                loadUrl("https://app.sandbox.midtrans.com/snap/v3/redirection/$snapToken")
            }
        }
    )
}


