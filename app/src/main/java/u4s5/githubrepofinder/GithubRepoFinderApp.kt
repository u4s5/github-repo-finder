package u4s5.githubrepofinder

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import u4s5.githubrepofinder.dagger.DaggerAppComponent

class GithubRepoFinderApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)
}
