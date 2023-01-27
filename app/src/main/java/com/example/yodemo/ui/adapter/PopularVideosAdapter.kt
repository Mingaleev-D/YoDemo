package com.example.yodemo.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.yodemo.data.dto.model.Item
import com.example.yodemo.databinding.ItemVideoBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * @author : Mingaleev D
 * @data : 27/01/2023
 */

class PopularVideosAdapter : Adapter<PopularVideosAdapter.MyViewHolder>() {

   private val callback = object : DiffUtil.ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item
      ): Boolean { return oldItem.id == newItem.id }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean { return oldItem == newItem }
   }
   val differ = AsyncListDiffer(this, callback)

   inner class MyViewHolder(val binding: ItemVideoBinding) : ViewHolder(binding.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemPopularVideosBinding =
          ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return MyViewHolder(itemPopularVideosBinding)
   }

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val video = differ.currentList[position]
      val thumbnailUrl = video.snippet.thumbnails.medium.url
      val channelLogo = video.snippet.thumbnails.medium.url
      val title = video.snippet.title
      val channel = video.snippet.channelTitle
      val videoViews = viewsCount(video.statistics.viewCount.toInt())
      val publishedAt = convert(video.snippet.publishedAt)

      holder.binding.apply {
         videoThumbnail.load(thumbnailUrl)
         channelPicture.load(channelLogo)
         videoTitle.text = title
         channelName.text = channel
         views.text = videoViews
         publishedTime.text = publishedAt
      }
   }
   @RequiresApi(Build.VERSION_CODES.O)
   private fun convert(publishedDay: String): String {
      val formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
      val publishedAt = LocalDateTime.parse(publishedDay, formatPattern)

      val currentDate = LocalDateTime.now().withNano(0)

      val differenceInSeconds = ChronoUnit.SECONDS.between(publishedAt, currentDate)
      val differenceInDays = ChronoUnit.DAYS.between(publishedAt, currentDate)
      val differenceInMonths = ChronoUnit.MONTHS.between(publishedAt, currentDate)
      return findDifference(differenceInSeconds, differenceInDays, differenceInMonths)
   }

   @RequiresApi(Build.VERSION_CODES.O)
   private fun viewsCount(views: Int): String {
      return when {
         views >= 1000000000 -> {
            val formattedViews = views / 1000000
            "${formattedViews}B views"
         }
         views >= 1000000    -> {
            val formattedViews = views / 10000
            "${formattedViews}M views"
         }
         views >= 1000       -> {
            val formattedViews = views / 10000
            "${formattedViews}K views"
         }
         else                -> {
            "$views views"
         }
      }
   }

   private fun findDifference(
       differenceInSeconds: Long,
       differenceInDays: Long,
       differenceInMonths: Long
   ): String {
      val hours = differenceInSeconds / 3600
      when (differenceInDays) {
         in 21..31 -> {
            return "3 weeks ago"
         }
         in 14..20 -> {
            return "2 weeks ago"
         }
         in 2..13  -> {
            return "$differenceInDays days ago"
         }
         in 0..1   -> {
            return "$hours hours ago"
         }
      }
      if (differenceInMonths in 0..1) {
         return "$differenceInMonths month ago"
      }
      return "$differenceInMonths months ago"
   }

   override fun getItemCount(): Int { return  differ.currentList.size }


}