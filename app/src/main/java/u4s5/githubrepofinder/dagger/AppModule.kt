package u4s5.githubrepofinder.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import u4s5.githubrepofinder.GithubRepoFinderApp
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideContext(application: GithubRepoFinderApp): Context = application.applicationContext
    }
}
