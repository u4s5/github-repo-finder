package u4s5.githubrepofinder

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_search.*
import u4s5.githubrepofinder.base.isVisible
import u4s5.githubrepofinder.base.middleToast
import u4s5.githubrepofinder.base.showKeyboard
import u4s5.githubrepofinder.base.string
import u4s5.githubrepofinder.base.visible
import u4s5.githubrepofinder.contract.SearchView
import u4s5.githubrepofinder.model.SearchItem
import u4s5.githubrepofinder.utils.ErrorType
import javax.inject.Inject

class SearchActivity : DaggerActivity(), SearchView {

    @Inject
    lateinit var presenter: SearchPresenter

    @Inject
    lateinit var searchResultsAdapter: SearchResultsAdapter

    private var userInfoFragment: UserInfoFragment? = null
    private var openedUserInfoPosition: Int? = null

    private val searchClickListener = View.OnClickListener {
        presenter.onSearchClicked(search_edit.text.toString())
    }

    private val searchItemClickListener = object : SearchResultsAdapter.ItemClickListener {
        override fun onItemClicked(position: Int) {
            presenter.onSearchItemClicked(position)
            openedUserInfoPosition = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_button.setOnClickListener(searchClickListener)
        search_edit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search_button.performClick()
                return@setOnEditorActionListener true
            }
            false
        }
        searchResultsAdapter.itemClickListener = searchItemClickListener

        search_results.apply {
            val manager = LinearLayoutManager(this@SearchActivity)
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(context, manager.orientation))
            adapter = searchResultsAdapter
        }

        presenter.subscribe(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        search_results.visible(false)
        error_text.visible(false)
        search_button.isClickable = false
        progress_bar.visible(true)
    }

    override fun showResults() {
        progress_bar.visible(false)
        error_text.visible(false)
        search_button.isClickable = true
        search_results.visible(true)
    }

    override fun showError(errorType: ErrorType) {
        search_results.visible(false)
        progress_bar.visible(false)
        search_button.isClickable = true

        error_text.text = string(when (errorType) {
            ErrorType.REQUEST_LIMIT -> R.string.request_limit_error
            ErrorType.NO_CONNECTION -> R.string.no_connection_error
            ErrorType.UNKNOWN -> R.string.unknown_error
        })
        error_text.visible(true)
    }

    override fun setSearchResults(items: List<SearchItem>) {
        searchResultsAdapter.data = items
    }

    override fun showKeyboard(visible: Boolean) = search_button.showKeyboard(false)

    override fun showEmptyQueryWarning() = middleToast(R.string.empty_query_warning)

    override fun showNoResultsWarning() = middleToast(R.string.no_results_warning)

    override fun showUserName(userName: String) {
        userInfoFragment = fragmentManager.findFragmentByTag(UserInfoFragment.DIALOG_TAG) as? UserInfoFragment
        if (userInfoFragment == null) {
            userInfoFragment = UserInfoFragment()
            userInfoFragment?.show(fragmentManager, UserInfoFragment.DIALOG_TAG)
        }
        userInfoFragment?.setUserName(userName)
    }

    override fun showUserAvatar(avatar: Bitmap) {
        userInfoFragment?.setUserAvatar(avatar)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putBoolean(KEY_ERROR_STATE, error_text.isVisible())
            putString(KEY_ERROR_TEXT, error_text.text.toString())
            putBoolean(KEY_RESULTS_STATE, search_results.isVisible() && searchResultsAdapter.data.isNotEmpty())
            putBoolean(KEY_SEARCH_IN_PROCESS, progress_bar.isVisible())
            userInfoFragment?.let { putBoolean(KEY_USER_INFO_OPENED, it.isVisible) }
            openedUserInfoPosition?.let { putInt(KEY_USER_INFO_OPENED_POSITION, it) }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.apply {
            if (getBoolean(KEY_ERROR_STATE)) {
                error_text.visible(true)
                error_text.text = getString(KEY_ERROR_TEXT)
                showKeyboard(false)
            }
            if (getBoolean(KEY_RESULTS_STATE) || getBoolean(KEY_SEARCH_IN_PROCESS)) {
                search_button.performClick()
                showKeyboard(false)
            }
            if (getBoolean(KEY_USER_INFO_OPENED)) getInt(KEY_USER_INFO_OPENED_POSITION).let {
                presenter.onSearchItemClicked(it)
                openedUserInfoPosition = it
            }
        }
    }

    companion object {
        const val KEY_ERROR_STATE = "error"
        const val KEY_ERROR_TEXT = "error_text"
        const val KEY_RESULTS_STATE = "results"
        const val KEY_SEARCH_IN_PROCESS = "search_in_process"
        const val KEY_USER_INFO_OPENED = "user_info_opened"
        const val KEY_USER_INFO_OPENED_POSITION = "user_info_opened_position"
    }
}
