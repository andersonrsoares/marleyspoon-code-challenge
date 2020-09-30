package br.com.anderson.marleyspooncodechallenge.dto
import com.google.gson.annotations.SerializedName

data class RecipeResponseDTO(
    @SerializedName("data")
    var recipeRootDTO: RecipeRootDTO? = null
)

data class RecipeCollencionResponseDTO(
    @SerializedName("data")
    var recipeCollectionRootDTO: RecipeCollectionRootDTO? = null
)


data class RecipeRootDTO(
    @SerializedName("recipe")
    var recipe: RecipeDTO? = null
)

data class RecipeCollectionRootDTO(
    @SerializedName("recipeCollection")
    var recipeCollection: RecipeCollectionDTO? = null
)

data class RecipeCollectionDTO(
    @SerializedName("items")
    var items: List<RecipeDTO>? = null,
    @SerializedName("total")
    var total: Int = 0
)

data class RecipeDTO(
    @SerializedName("photo")
    var photo: PhotoDTO? = null,
    @SerializedName("sys")
    var sys: SysDTO? = null,
    @SerializedName("tagsCollection")
    var tagsCollection: TagsCollectionDTO? = null,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("chef")
    var chef: ChefDTO? = null
)


data class ChefDTO(
    @SerializedName("name")
    var name: String = ""
)

data class TagsCollectionDTO(
    @SerializedName("items")
    var items: List<TagDTO>? = null
)

data class TagDTO(
    @SerializedName("name")
    var name: String? = null
)

data class PhotoDTO(
    @SerializedName("url")
    var url: String = ""
)

data class SysDTO(
    @SerializedName("id")
    var id: String = ""
)

