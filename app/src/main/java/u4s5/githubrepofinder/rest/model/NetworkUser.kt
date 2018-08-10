package u4s5.githubrepofinder.rest.model

import com.google.gson.annotations.SerializedName

data class NetworkUser(
        val login: String,
        @SerializedName("avatar_url") val avatarUrl: String
)
