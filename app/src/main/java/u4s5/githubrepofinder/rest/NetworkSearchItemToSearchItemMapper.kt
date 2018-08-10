package u4s5.githubrepofinder.rest

import u4s5.githubrepofinder.base.Mapper
import u4s5.githubrepofinder.model.SearchItem
import u4s5.githubrepofinder.rest.model.NetworkSearchItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkSearchItemToSearchItemMapper @Inject constructor() : Mapper<NetworkSearchItem, SearchItem>() {

    override fun map(from: NetworkSearchItem) =
            SearchItem(from.name, from.language, from.owner.login, from.owner.avatarUrl)
}
