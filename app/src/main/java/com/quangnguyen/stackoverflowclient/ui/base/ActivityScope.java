package com.quangnguyen.stackoverflowclient.ui.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * An activity scope for PresenterComponent.
 * @author QuangNguyen (quangctkm9207).
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
