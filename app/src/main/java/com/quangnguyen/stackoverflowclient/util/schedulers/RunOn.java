package com.quangnguyen.stackoverflowclient.util.schedulers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Qualifier to define Scheduler type (io, computation, or ui main thread).
 *
 * @author QuangNguyen (quangctkm9207).
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RunOn {
  SchedulerType value() default SchedulerType.IO;
}
