// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.source.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SourceActivity$$ViewBinder<T extends com.hopop.hopop.source.activity.SourceActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493062, "field 'source_list'");
    target.source_list = finder.castView(view, 2131493062, "field 'source_list'");
  }

  @Override public void unbind(T target) {
    target.source_list = null;
  }
}
