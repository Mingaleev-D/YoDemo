package com.example.yodemo.data.dto.model


import com.google.gson.annotations.SerializedName

data class RegionRestriction(
    @SerializedName("allowed")
    val allowed: List<String>,
    @SerializedName("blocked")
    val blocked: List<String>
)