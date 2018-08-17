package u4s5.githubrepofinder.contract

import android.graphics.Bitmap
import u4s5.githubrepofinder.model.SearchItem
import u4s5.githubrepofinder.utils.ErrorType

interface SearchView {

    fun showProgress()

    fun showResults()

    fun showError(errorType: ErrorType)

    fun setSearchResults(items: List<SearchItem>)

    fun showKeyboard(visible: Boolean)

    fun showEmptyQueryWarning()

    fun showNoResultsWarning()

    fun showUserInfoArea()

    fun showUserName(userName: String)

    fun showUserAvatar(avatar: Bitmap)
}
