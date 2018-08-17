package u4s5.githubrepofinder

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import retrofit2.HttpException
import u4s5.githubrepofinder.base.BasePresenter
import u4s5.githubrepofinder.contract.SearchView
import u4s5.githubrepofinder.model.SearchModel
import u4s5.githubrepofinder.utils.ErrorType
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class SearchPresenter @Inject constructor(
        private val model: SearchModel,
        private val context: Context
) : BasePresenter() {

    private var searchView: SearchView? = null

    fun subscribe(view: SearchView) {
        searchView = view
    }

    fun onSearchClicked(query: String) = searchView?.apply {
        if (query.isBlank()) {
            searchView?.showEmptyQueryWarning()
            return@apply
        }

        showProgress()

        model.search(query).execute(
                onSuccess = {
                    setSearchResults(it)
                    showResults()
                    if (it.isEmpty()) showNoResultsWarning() else showKeyboard(false)
                },
                onError = {
                    showError(when {
                        it is HttpException && it.code() == 403 -> ErrorType.REQUEST_LIMIT
                        it is UnknownHostException || it is SocketTimeoutException -> ErrorType.NO_CONNECTION
                        else -> ErrorType.UNKNOWN
                    })
                    showKeyboard(false)
                    Log.d(javaClass.simpleName, it.message, it)
                })
    }

    fun onSearchItemClicked(position: Int) {
        searchView?.showUserInfoArea()

        model.lastData?.get(position)?.apply {
            searchView?.showUserName(userName)
            Glide.with(context)
                    .asBitmap()
                    .load(userAvatar)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            searchView?.showUserAvatar(resource)
                        }
                    })
        }
    }

    fun onDestroy() {
        searchView = null
        clearDisposables()
    }
}
