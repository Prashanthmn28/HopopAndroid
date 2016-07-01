// Generated code from Butter Knife. Do not modify!
package com.hopop.hopop.payment.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PaymentActivity$$ViewBinder<T extends com.hopop.hopop.payment.activity.PaymentActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493029, "method 'mePlusUser'");
    target.MePlus = finder.castView(view, 2131493029, "field 'MePlus'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.mePlusUser(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493030, "method 'mePlusUser1'");
    target.MePlus1 = finder.castView(view, 2131493030, "field 'MePlus1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.mePlusUser1(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493031, "method 'mePlusUser2'");
    target.MePlus2 = finder.castView(view, 2131493031, "field 'MePlus2'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.mePlusUser2(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493032, "method 'mePlusUser3'");
    target.MePlus3 = finder.castView(view, 2131493032, "field 'MePlus3'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.mePlusUser3(p0);
        }
      });
    view = finder.findOptionalView(source, 2131493037, null);
    target.numofSeats = finder.castView(view, 2131493037, "field 'numofSeats'");
    view = finder.findOptionalView(source, 2131493042, null);
    target.rideshareCalc = finder.castView(view, 2131493042, "field 'rideshareCalc'");
    view = finder.findOptionalView(source, 2131493043, null);
    target.rideshareAmt = finder.castView(view, 2131493043, "field 'rideshareAmt'");
    view = finder.findRequiredView(source, 2131493045, "method 'payUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.payUser(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.MePlus = null;
    target.MePlus1 = null;
    target.MePlus2 = null;
    target.MePlus3 = null;
    target.numofSeats = null;
    target.rideshareCalc = null;
    target.rideshareAmt = null;
  }
}
