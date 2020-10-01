package br.com.anderson.marleyspooncodechallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.anderson.mar.persistence.typeconverters.ListStringTypeConverter
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(value = [ListStringTypeConverter::class])
data class Recipe(
    @SerializedName("photo")
    var photo: String? = null,
    @SerializedName("sys")
    @PrimaryKey
    var id: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("chef")
    var chefName: String? = null,
    @SerializedName("tags")
    var tags: List<String>? = null
)

