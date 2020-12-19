// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.activities;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.customView.SwipeDisabledViewPager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class V2MainActivity_ViewBinding implements Unbinder {
  private V2MainActivity target;

  @UiThread
  public V2MainActivity_ViewBinding(V2MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public V2MainActivity_ViewBinding(V2MainActivity target, View source) {
    this.target = target;

    target.navigation_bottom_view = Utils.findRequiredView(source, R.id.navigation_bottom, "field 'navigation_bottom_view'");
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", SwipeDisabledViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    V2MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.navigation_bottom_view = null;
    target.viewPager = null;
  }
}
