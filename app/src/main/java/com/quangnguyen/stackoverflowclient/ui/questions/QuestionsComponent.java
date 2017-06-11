package com.quangnguyen.stackoverflowclient.ui.questions;

import com.quangnguyen.stackoverflowclient.di.component.QuestionRepositoryComponent;
import dagger.Component;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Component(modules = QuestionsPresenterModule.class, dependencies = QuestionRepositoryComponent.class)
public interface QuestionsComponent {
  void inject(QuestionsActivity questionsActivity);
}
