package co.appdev.boilerplate.data.remote;

import co.appdev.boilerplate.data.model.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "http://udharmaster.vteamslabs.com/";
    String API_LEVEL = "api/";
    String VERSION = "v1/";

    String API_LOGIN = API_LEVEL + VERSION + "login";
    @FormUrlEncoded
    @POST(API_LOGIN)
    Observable<LoginResponse> requestAccessCode(@Field("mobile") String phoneNumber);


}
