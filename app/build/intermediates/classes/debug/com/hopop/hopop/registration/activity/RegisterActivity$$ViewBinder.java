// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.registration.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.hopop.hopop.registration.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493011, "field 'mobile'");
    target.mobile = finder.castView(view, 2131493011, "field 'mobile'");
    view = finder.findRequiredView(source, 2131493012, "field 'pass'");
    target.pass = finder.castView(view, 2131493012, "field 'pass'");
    view = finder.findRequiredView(source, 2131493051, "field 'fName'");
    target.fName = finder.castView(view, 2131493051, "field 'fName'");
    view = finder.findRequiredView(source, 2131493052, "field 'lName'");
    target.lName = finder.castView(view, 2131493052, "field 'lName'");
    view = finder.findRequiredView(source, 2131493053, "field 'email'");
    target.email = finder.castView(view, 2131493053, "field 'email'");
    view = finder.findRequiredView(source, 2131493059, "method 'linkedInUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.linkedInUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493058, "method 'signUpUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.signUpUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493057, "method 'onTouch'");
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouch(p0, p1);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mobile = null;
    target.pass = null;
    target.fName = null;
    target.lName = null;
    target.email = null;
  }
}
