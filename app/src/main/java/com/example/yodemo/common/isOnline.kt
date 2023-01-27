package com.example.yodemo.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

/**
 * @author : Mingaleev D
 * @data : 27/01/2023
 */

fun isOnline(context: Context): Boolean {
   val connectivityManager =
       context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      val capabilities =
          connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
      if (capabilities != null) {
         when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
               Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
               return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     -> {
               Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
               return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
               Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
               return true
            }
         }
      }
   } else {
      val activeNetworkInfo = connectivityManager.activeNetworkInfo
      if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
         return true
      }
   }
   return false
}