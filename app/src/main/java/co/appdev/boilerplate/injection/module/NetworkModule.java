package co.appdev.boilerplate.injection.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import co.appdev.boilerplate.data.local.SharedPrefHelper;
import co.appdev.boilerplate.data.remote.ApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(HttpLoggingInterceptor interceptor, SharedPrefHelper sharedPrefHelper) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization","Bearer "+sharedPrefHelper.getToken())
                            .build();
                    return chain.proceed(request);
                }).build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiService providesApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
