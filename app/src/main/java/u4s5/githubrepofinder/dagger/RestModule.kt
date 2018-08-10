package u4s5.githubrepofinder.dagger

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import u4s5.githubrepofinder.rest.GithubService
import javax.inject.Singleton

@Module
abstract class RestModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @JvmStatic
        @Provides
        @Singleton
        fun provideGithubService(retrofit: Retrofit): GithubService =
                retrofit.create(GithubService::class.java)
    }
}
