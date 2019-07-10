package co.appdev.boilerplate.data.remote

import co.appdev.boilerplate.data.model.UserPost
import io.reactivex.Observable
import retrofit2.http.GET

interface RemoteApi {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    @GET("/posts")
    fun getPosts(): Observable<List<UserPost>>
}