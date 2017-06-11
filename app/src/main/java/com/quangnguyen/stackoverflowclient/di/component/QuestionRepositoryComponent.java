package com.quangnguyen.stackoverflowclient.di.component;

import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.di.module.AppModule;
import com.quangnguyen.stackoverflowclient.di.module.QuestionRepositoryModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Component(modules = { QuestionRepositoryModule.class, AppModule.class })
public interface QuestionRepositoryComponent {
  QuestionRepository getRepository();
}
