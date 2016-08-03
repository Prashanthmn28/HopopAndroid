// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.login.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.hopop.hopop.login.activity.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493013, "field 'mobile'");
    target.mobile = finder.castView(view, 2131493013, "field 'mobile'");
    view = finder.findRequiredView(source, 2131493014, "field 'pass'");
    target.pass = finder.castView(view, 2131493014, "field 'pass'");
    view = finder.findRequiredView(source, 2131493015, "method 'loginUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.loginUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493016, "method 'signupUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.signupUser(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mobile = null;
    target.pass = null;
  }
}
