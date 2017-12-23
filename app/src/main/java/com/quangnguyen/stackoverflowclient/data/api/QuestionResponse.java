package com.quangnguyen.stackoverflowclient.data.api;

import com.google.gson.annotations.SerializedName;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import java.util.List;

public class QuestionResponse {
  @SerializedName("items") private List<Question> questions;

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }
}
