package u4s5.githubrepofinder.rest

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query
import u4s5.githubrepofinder.rest.model.SearchAnswer
import javax.inject.Singleton

@Singleton
interface GithubService {

    @GET("search/repositories")
    fun getRepos(@Query("q") query: String): Maybe<SearchAnswer>
}
