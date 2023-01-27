package com.example.yodemo.common

import com.example.yodemo.BuildConfig

object Constants {

   const val BASE_URL = "https://youtube.googleapis.com/youtube/v3/"
   const val API_KEY = BuildConfig.API_KEY

   //This endpoint gets a list of videos
   const val LIST_OF_VIDEOS = "videos"

   //PART PROPERTIES
   const val SNIPPET = "snippet"
   const val DETAILS = "contentDetails"
   const val STATISTICS = "statistics"

   //CHART PROPERTY
   const val MOST_POPULAR = "mostPopular"

   //REGION_CODE
   const val REGION_CODE = "US"
}