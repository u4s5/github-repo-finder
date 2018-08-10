package u4s5.githubrepofinder.rest.model

data class NetworkSearchItem(
        val name: String,
        val language: String?,
        val owner: NetworkUser
)
