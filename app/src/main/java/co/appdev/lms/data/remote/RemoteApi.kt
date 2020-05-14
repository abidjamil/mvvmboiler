package co.appdev.lms.data.remote

import co.appdev.lms.data.model.UserPostModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RemoteApi {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    @GET("/posts")
    fun getPosts(): Observable<List<UserPostModel>>
}