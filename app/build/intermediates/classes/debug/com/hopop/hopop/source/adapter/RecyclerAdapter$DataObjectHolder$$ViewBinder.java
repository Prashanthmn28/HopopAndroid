// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.source.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecyclerAdapter$DataObjectHolder$$ViewBinder<T extends com.hopop.hopop.source.adapter.RecyclerAdapter.DataObjectHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493112, "field 'tv_entity_name'");
    target.tv_entity_name = finder.castView(view, 2131493112, "field 'tv_entity_name'");
  }

  @Override public void unbind(T target) {
    target.tv_entity_name = null;
  }
}
