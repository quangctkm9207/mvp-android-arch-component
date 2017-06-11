package com.quangnguyen.stackoverflowclient.data.api;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class HeaderInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    request = request.newBuilder()
        .addHeader("Accept", "application/json")
        .addHeader("Content-type", "application/json")
        .build();
    return chain.proceed(request);
  }
}
