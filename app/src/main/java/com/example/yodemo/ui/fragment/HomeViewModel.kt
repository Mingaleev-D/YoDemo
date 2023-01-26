package com.example.yodemo.ui.fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yodemo.R
import com.example.yodemo.common.NetworkResource
import com.example.yodemo.common.isOnline
import com.example.yodemo.data.dto.model.YoutubeListResposeDto
import com.example.yodemo.repository.GetPopularVideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 26/01/2023
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val repository: GetPopularVideoRepository
) : ViewModel() {

   private val _popularVideos: MutableLiveData<NetworkResource<YoutubeListResposeDto>> = MutableLiveData()
   val popularVideos: LiveData<NetworkResource<YoutubeListResposeDto>> get() = _popularVideos

  private fun fetchPopularVideos() = viewModelScope.launch {
      _popularVideos.value = NetworkResource.Loading()

      if (isOnline(app)) {
         try {
            val reposne = repository.getPopularVideos()
            if (reposne.isSuccessful) {
               reposne.body()?.let { res ->
                  _popularVideos.value = NetworkResource.Success(res)
               }
            } else {
               _popularVideos.value = NetworkResource.Error(message = reposne.message())
            }

         } catch (e: Exception) {
            _popularVideos.value = NetworkResource.Error(R.string.data_not_found.toString())
         }
      } else {
         _popularVideos.value = NetworkResource.Error(R.string.no_internet_connected.toString())
      }
   }

}