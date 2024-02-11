package com.app.hapis.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<GenresComingItem>? = null
) : Parcelable

@Parcelize
data class GenresComingItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
