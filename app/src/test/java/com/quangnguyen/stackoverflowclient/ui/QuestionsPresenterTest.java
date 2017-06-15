package com.quangnguyen.stackoverflowclient.ui;

import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsContract;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * @author QuangNguyen (quangctkm9207).
 */

public class QuestionsPresenterTest {
  @Mock
  QuestionRepository repository;

  @Mock
  QuestionsContract.View view;

  private QuestionsPresenter presenter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    presenter = new QuestionsPresenter(repository, view);
  }

  @Test
  public void loadQuestions() {
    presenter.loadQuestions(false);
    verify(repository).loadQuestions(false);
  }
}
