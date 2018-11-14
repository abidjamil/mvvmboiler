package co.appdev.boilerplate.data.remote;

import co.appdev.boilerplate.data.model.GetUsersResponse;
import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "http://www.mocky.io/";
    String API_LEVEL = "";
    String VERSION = "v2/";

    String API_GET_USERS = API_LEVEL + VERSION + "5bebfde23300007a00fbc170";

    @POST(API_GET_USERS)
    Observable<GetUsersResponse> getUsers();
}
