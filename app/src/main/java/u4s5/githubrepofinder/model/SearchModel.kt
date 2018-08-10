package u4s5.githubrepofinder.model

import io.reactivex.Maybe
import u4s5.githubrepofinder.rest.GithubService
import u4s5.githubrepofinder.rest.NetworkSearchItemToSearchItemMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchModel @Inject constructor(
        private val githubService: GithubService,
        private val networkSearchItemToSearchItemMapper: NetworkSearchItemToSearchItemMapper
) {

    var lastData: List<SearchItem>? = null
        private set
    private var lastQuery: String? = null

    fun search(query: String): Maybe<List<SearchItem>> {
        lastData?.let {
            if (lastQuery != null && query == lastQuery) {
                return Maybe.fromCallable { it }
            }
        }

        return githubService.getRepos(query)
                .map { networkSearchItemToSearchItemMapper.map(it.items) }
                .doOnSuccess {
                    lastQuery = query
                    lastData = it
                }
    }
}
