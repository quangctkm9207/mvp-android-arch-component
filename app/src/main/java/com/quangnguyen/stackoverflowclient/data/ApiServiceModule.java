package com.quangnguyen.stackoverflowclient.data;

import com.quangnguyen.stackoverflowclient.data.api.HeaderInterceptor;
import com.quangnguyen.stackoverflowclient.data.api.QuestionService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiServiceModule {
  private static final String BASE_URL = "base_url";

  @Provides
  @Named(BASE_URL)
  String provideBaseUrl() {
    return Config.API_HOST;
  }

  @Provides
  @Singleton
  HeaderInterceptor provideHeaderInterceptor() {
    return new HeaderInterceptor();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
  }

  @Provides
  @Singleton
  OkHttpClient provideHttpClient(HeaderInterceptor headerInterceptor,
      HttpLoggingInterceptor httpInterceptor) {
    return new OkHttpClient.Builder().addInterceptor(headerInterceptor)
        .addInterceptor(httpInterceptor)
        .build();
  }

  @Provides
  @Singleton
  Converter.Factory provideGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton
  CallAdapter.Factory provideRxJavaAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
      CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(client)
        .build();
  }

  @Provides
  @Singleton
  QuestionService provideQuestionService(Retrofit retrofit) {
    return retrofit.create(QuestionService.class);
  }
}
