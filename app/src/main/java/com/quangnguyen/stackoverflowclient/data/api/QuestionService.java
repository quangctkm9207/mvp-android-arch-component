package com.quangnguyen.stackoverflowclient.data.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public interface QuestionService {
  @GET("questions?site=stackoverflow")
  Flowable<QuestionResponse> loadQuestionsByTag(@Query("tagged") String tag);
}
