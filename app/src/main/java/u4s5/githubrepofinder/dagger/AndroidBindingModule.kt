package u4s5.githubrepofinder.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import u4s5.githubrepofinder.SearchActivity
import u4s5.githubrepofinder.UserInfoFragment

@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector
    @Suppress("unused")
    abstract fun searchActivityInjector(): SearchActivity

    @ContributesAndroidInjector
    @Suppress("unused")
    abstract fun userInfoFragmentInjector(): UserInfoFragment
}
