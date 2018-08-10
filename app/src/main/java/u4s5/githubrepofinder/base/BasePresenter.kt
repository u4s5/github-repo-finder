package u4s5.githubrepofinder.base

import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BasePresenter {

    private val disposables = CompositeDisposable()

    protected fun clearDisposables() = disposables.clear()

    @Suppress("unused")
    protected fun <E> Maybe<E>.execute(onSuccess: (E) -> Unit = emptySuccess(),
                                       onComplete: () -> Unit = emptyComplete(),
                                       onError: (Throwable) -> Unit = emptyError(),
                                       onSubscribe: (Disposable) -> Unit = emptySubscribe(),
                                       onFinished: () -> Unit = emptyFinished()) {

        observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : MaybeObserver<E> {
                    override fun onSuccess(t: E) {
                        onSuccess(t)
                        onFinished()
                    }

                    override fun onComplete() {
                        onComplete()
                        onFinished()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                        onSubscribe(d)
                    }

                    override fun onError(e: Throwable) {
                        onError(e)
                        onFinished()
                    }
                })
    }

    @Suppress("unused")
    protected fun Completable.execute(onComplete: () -> Unit = emptyComplete(),
                                      onError: (Throwable) -> Unit = emptyError(),
                                      onSubscribe: (Disposable) -> Unit = emptySubscribe(),
                                      onFinished: () -> Unit = emptyFinished()) {
        observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        onComplete()
                        onFinished()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                        onSubscribe(d)
                    }

                    override fun onError(e: Throwable) {
                        onError(e)
                        onFinished()
                    }
                })
    }

    @Suppress("unused")
    protected fun <E> Observable<E>.execute(onNext: (E) -> Unit = emptySuccess(),
                                            onComplete: () -> Unit = emptyComplete(),
                                            onError: (Throwable) -> Unit = emptyError(),
                                            onSubscribe: (Disposable) -> Unit = emptySubscribe(),
                                            onFinished: () -> Unit = emptyFinished()) {
        observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<E> {
                    override fun onComplete() {
                        onComplete()
                        onFinished()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                        onSubscribe(d)
                    }

                    override fun onNext(t: E) {
                        onNext(t)
                    }

                    override fun onError(e: Throwable) {
                        onError(e)
                        onFinished()
                    }
                })
    }

    @Suppress("unused")
    protected fun <E> Single<E>.execute(onSuccess: (E) -> Unit = emptySuccess(),
                                        onError: (Throwable) -> Unit = emptyError(),
                                        onSubscribe: (Disposable) -> Unit = emptySubscribe(),
                                        onFinished: () -> Unit = emptyFinished()) {
        observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : SingleObserver<E> {
                    override fun onSuccess(t: E) {
                        onSuccess(t)
                        onFinished()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                        onSubscribe(d)
                    }

                    override fun onError(e: Throwable) {
                        onError(e)
                        onFinished()
                    }
                })
    }

    private fun <E> emptySuccess(): (E) -> Unit = {}

    private fun emptyComplete(): () -> Unit = {}

    private fun emptyError(): (Throwable) -> Unit = {
        Log.e(BasePresenter::class.java.simpleName, it.message ?: "", it)
    }

    private fun emptySubscribe(): (Disposable) -> Unit = {}

    private fun emptyFinished(): () -> Unit = {}
}
