package com.afollestad.aesthetic.views;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.afollestad.aesthetic.Aesthetic;

import rx.Subscription;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static com.afollestad.aesthetic.Rx.distinctToMainThread;
import static com.afollestad.aesthetic.Rx.onErrorLogAndRethrow;

/** @author Aidan Follestad (afollestad) */
@RestrictTo(LIBRARY_GROUP)
public class AestheticNestedScrollView extends NestedScrollView {

  private Subscription subscription;

  public AestheticNestedScrollView(Context context) {
    super(context);
  }

  public AestheticNestedScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AestheticNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void invalidateColors(int color) {
    EdgeGlowUtil.setEdgeGlowColor(this, color);
  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    subscription =
        Aesthetic.get()
            .colorAccent()
            .compose(distinctToMainThread())
            .subscribe(this::invalidateColors, onErrorLogAndRethrow());
  }

  @Override
  protected void onDetachedFromWindow() {
    subscription.unsubscribe();
    super.onDetachedFromWindow();
  }
}
