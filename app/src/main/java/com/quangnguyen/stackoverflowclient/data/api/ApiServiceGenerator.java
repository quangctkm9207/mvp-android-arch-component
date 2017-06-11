package com.quangnguyen.stackoverflowclient.data.api;

import com.quangnguyen.stackoverflowclient.data.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A retrofit service generator.
 *
 * @author QuangNguyen (quangctkm9207).
 */

public class ApiServiceGenerator {
  private Retrofit retrofit;

  private HttpLoggingInterceptor httpLoggingInterceptor =
      new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

  private OkHttpClient httpClient =
      new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor())
          .addInterceptor(httpLoggingInterceptor)
          .build();

  private static ApiServiceGenerator INSTANCE;

  private ApiServiceGenerator() {
    retrofit = new Retrofit.Builder().baseUrl(Config.API_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build();
  }

  public static ApiServiceGenerator getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ApiServiceGenerator();
    }
    return INSTANCE;
  }

  public <S> S createService(Class<S> service) {
    return retrofit.create(service);
  }
}
