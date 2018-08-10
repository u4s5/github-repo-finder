package u4s5.githubrepofinder.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import u4s5.githubrepofinder.GithubRepoFinderApp
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AndroidBindingModule::class,
    AppModule::class,
    RestModule::class
])
interface AppComponent : AndroidInjector<GithubRepoFinderApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubRepoFinderApp>()
}
