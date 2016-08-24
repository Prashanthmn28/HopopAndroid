// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.registration.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.hopop.hopop.registration.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493012, "field 'mobile'");
    target.mobile = finder.castView(view, 2131493012, "field 'mobile'");
    view = finder.findRequiredView(source, 2131493013, "field 'pass'");
    target.pass = finder.castView(view, 2131493013, "field 'pass'");
    view = finder.findRequiredView(source, 2131493052, "field 'fName'");
    target.fName = finder.castView(view, 2131493052, "field 'fName'");
    view = finder.findRequiredView(source, 2131493053, "field 'lName'");
    target.lName = finder.castView(view, 2131493053, "field 'lName'");
    view = finder.findRequiredView(source, 2131493054, "field 'email'");
    target.email = finder.castView(view, 2131493054, "field 'email'");
    view = finder.findRequiredView(source, 2131493060, "field 'dob' and method 'dobUser'");
    target.dob = finder.castView(view, 2131493060, "field 'dob'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.dobUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493061, "field 'gender'");
    target.gender = finder.castView(view, 2131493061, "field 'gender'");
    view = finder.findRequiredView(source, 2131493062, "field 'male' and method 'onRadioButtonClicked'");
    target.male = finder.castView(view, 2131493062, "field 'male'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onRadioButtonClicked(finder.<android.widget.RadioButton>castParam(p0, "doClick", 0, "onRadioButtonClicked", 0));
        }
      });
    view = finder.findRequiredView(source, 2131493063, "field 'female' and method 'onRadioButtonClicked'");
    target.female = finder.castView(view, 2131493063, "field 'female'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onRadioButtonClicked(finder.<android.widget.RadioButton>castParam(p0, "doClick", 0, "onRadioButtonClicked", 0));
        }
      });
    view = finder.findRequiredView(source, 2131493064, "field 'other' and method 'onRadioButtonClicked'");
    target.other = finder.castView(view, 2131493064, "field 'other'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onRadioButtonClicked(finder.<android.widget.RadioButton>castParam(p0, "doClick", 0, "onRadioButtonClicked", 0));
        }
      });
    view = finder.findRequiredView(source, 2131493066, "method 'linkedInUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.linkedInUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493065, "method 'signUpUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.signUpUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493059, "method 'onTouch'");
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
    target.dob = null;
    target.gender = null;
    target.male = null;
    target.female = null;
    target.other = null;
  }
}
