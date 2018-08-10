package u4s5.githubrepofinder.rest.model

import com.google.gson.annotations.SerializedName

data class SearchAnswer(
        @SerializedName("total_count") val totalCount: Int,
        val items: List<NetworkSearchItem>
)
