package com.quangnguyen.stackoverflowclient.data.repository;

import android.support.annotation.VisibleForTesting;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class QuestionRepository implements QuestionDataSource {

  private QuestionDataSource remoteDataSource;
  private QuestionDataSource localDataSource;

  @VisibleForTesting List<Question> caches;

  @Inject public QuestionRepository(@Local QuestionDataSource localDataSource,
      @Remote QuestionDataSource remoteDataSource) {
    this.localDataSource = localDataSource;
    this.remoteDataSource = remoteDataSource;

    caches = new ArrayList<>();
  }

  @Override public Flowable<List<Question>> loadQuestions(boolean forceRemote) {
    if (forceRemote) {
      return refreshData();
    } else {
      if (caches.size() > 0) {
        // if cache is available, return it immediately
        return Flowable.just(caches);
      } else {
        // else return data from local storage
        return localDataSource.loadQuestions(false)
            .take(1)
            .flatMap(Flowable::fromIterable)
            .doOnNext(question -> caches.add(question))
            .toList()
            .toFlowable()
            .filter(list -> !list.isEmpty())
            .switchIfEmpty(
                refreshData()); // If local data is empty, fetch from remote source instead.
      }
    }
  }

  /**
   * Fetches data from remote source.
   * Save it into both local database and cache.
   *
   * @return the Flowable of newly fetched data.
   */
  Flowable<List<Question>> refreshData() {
    return remoteDataSource.loadQuestions(true).doOnNext(list -> {
      // Clear cache
      caches.clear();
      // Clear data in local storage
      localDataSource.clearData();
    }).flatMap(Flowable::fromIterable).doOnNext(question -> {
      caches.add(question);
      localDataSource.addQuestion(question);
    }).toList().toFlowable();
  }

  /**
   * Loads a question by its question id.
   *
   * @param questionId question's id.
   * @return a corresponding question from cache.
   */
  public Flowable<Question> getQuestion(long questionId) {
    return Flowable.fromIterable(caches).filter(question -> question.getId() == questionId);
  }

  @Override public void addQuestion(Question question) {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override public void clearData() {
    caches.clear();
    localDataSource.clearData();
  }
}
