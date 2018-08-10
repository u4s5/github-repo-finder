package u4s5.githubrepofinder

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.DaggerDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.SingleSubject
import kotlinx.android.synthetic.main.fragment_user_info.*
import u4s5.githubrepofinder.base.visible

class UserInfoFragment : DaggerDialogFragment() {

    private var name: SingleSubject<String> = SingleSubject.create()
    private var avatar: SingleSubject<Bitmap> = SingleSubject.create()

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        disposables.addAll(
                name.doOnSuccess { user_name.text = it }
                        .subscribe(),
                avatar.doOnSubscribe {
                    user_avatar.visible(false)
                    progress_bar.visible(true)
                }
                        .doOnSuccess {
                            user_avatar.setImageBitmap(it)
                            progress_bar.visible(false)
                            user_avatar.visible(true)
                        }
                        .subscribe()
        )
    }

    override fun onDestroy() {
        name = SingleSubject.create()
        avatar = SingleSubject.create()
        disposables.clear()
        super.onDestroy()
    }

    fun setUserName(userName: String) {
        name.onSuccess(userName)
    }

    fun setUserAvatar(userAvatar: Bitmap) {
        avatar.onSuccess(userAvatar)
    }

    companion object {
        val DIALOG_TAG: String = UserInfoFragment::class.java.simpleName
    }
}
