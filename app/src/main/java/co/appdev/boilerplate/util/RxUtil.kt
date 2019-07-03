package co.appdev.boilerplate.util


import io.reactivex.disposables.Disposable

object RxUtil {

    fun dispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

}