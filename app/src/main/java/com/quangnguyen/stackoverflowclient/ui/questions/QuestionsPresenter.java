package com.quangnguyen.stackoverflowclient.ui.questions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * A presenter with life-cycle aware.
 *
 * @author QuangNguyen (quangctkm9207).
 */
public class QuestionsPresenter implements QuestionsContract.Presenter, LifecycleObserver{

  private QuestionRepository repository;

  private QuestionsContract.View view;

  private List<Question> caches;

  @Inject
  public QuestionsPresenter(QuestionRepository repository, QuestionsContract.View view) {
    this.repository = repository;
    this.view = view;
    // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
    if (view instanceof LifecycleOwner) {
      ((LifecycleOwner) view).getLifecycle().addObserver(this);
    }
  }

  @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  public void attachView() {

  }

  @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void detachView() {
    // Clean up your resources here
  }

  @Override
  public void loadQuestions(boolean onlineRequired) {
    // Clear view
    view.clearQuestions();
    // Save results to cache
    repository.loadQuestions(onlineRequired)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(list -> {
          view.stopLoadingIndicator();
          if (list == null || list.isEmpty()) {
            view.showError("No data returned. Pull to refresh.");
          } else {
            view.showQuestions(list);
            caches = list;
          }
        }, error -> {
          view.stopLoadingIndicator();
          view.showError(error.getLocalizedMessage());
        });
  }

  @Override
  public void getQuestion(long questionId) {
    // Load question detail from cache
    if (caches != null && caches.size() != 0) {
      for (Question question : caches) {
        if (question.getId() == questionId) {
          view.showQuestionDetail(question);
          break;
        }
      }
    }
  }
}
