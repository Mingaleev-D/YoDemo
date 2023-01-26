package com.example.yodemo.repository

import com.example.yodemo.data.dto.model.YoutubeListResposeDto
import com.example.yodemo.data.remote.service.ApiYoService
import retrofit2.Response
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 27/01/2023
 */

class GetPopularVideoRepository @Inject constructor(
    private val api: ApiYoService
) {

   suspend fun getPopularVideos(): Response<YoutubeListResposeDto> {
      return api.fetchVideos()
   }
}