package com.quangnguyen.stackoverflowclient.data.repository;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.model.User;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public interface QuestionDataSource {
  Flowable<List<Question>> loadQuestions(boolean forceRemote);

  void addQuestion(Question question);

  void clearData();
}
