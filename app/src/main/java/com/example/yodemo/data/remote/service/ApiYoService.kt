package com.example.yodemo.data.remote.service

import com.example.yodemo.common.Constants.API_KEY
import com.example.yodemo.common.Constants.DETAILS
import com.example.yodemo.common.Constants.LIST_OF_VIDEOS
import com.example.yodemo.common.Constants.MOST_POPULAR
import com.example.yodemo.common.Constants.REGION_CODE
import com.example.yodemo.common.Constants.SNIPPET
import com.example.yodemo.common.Constants.STATISTICS
import com.example.yodemo.data.dto.model.YoutubeListResposeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author : Mingaleev D
 * @data : 27/01/2023
 */

interface ApiYoService {

   @GET(LIST_OF_VIDEOS)
   suspend fun fetchVideos(
       @Query("part") part: String = "$SNIPPET,$DETAILS,$STATISTICS",
       @Query("chart") chart: String = MOST_POPULAR,
       @Query("regionCode") regionCode: String = REGION_CODE,
       @Query("key") apiKey: String = API_KEY
   ):Response<YoutubeListResposeDto>
}