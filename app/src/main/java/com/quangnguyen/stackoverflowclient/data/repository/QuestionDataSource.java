package com.quangnguyen.stackoverflowclient.data.repository;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import java.util.List;

public interface QuestionDataSource {
  Flowable<List<Question>> loadQuestions(boolean forceRemote);

  void addQuestion(Question question);

  void clearData();
}
