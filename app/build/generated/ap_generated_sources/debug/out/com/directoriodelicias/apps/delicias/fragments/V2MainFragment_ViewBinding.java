// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.fragments;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.customView.SwipeDisabledViewPager;
import com.google.android.gms.ads.AdView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class V2MainFragment_ViewBinding implements Unbinder {
  private V2MainFragment target;

  @UiThread
  public V2MainFragment_ViewBinding(V2MainFragment target, View source) {
    this.target = target;

    target.navigation_bottom_view = Utils.findRequiredView(source, R.id.navigation_bottom, "field 'navigation_bottom_view'");
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", SwipeDisabledViewPager.class);
    target.adsLayout = Utils.findRequiredViewAsType(source, R.id.adsLayout, "field 'adsLayout'", LinearLayout.class);
    target.mAdView = Utils.findRequiredViewAsType(source, R.id.adView, "field 'mAdView'", AdView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    V2MainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.navigation_bottom_view = null;
    target.viewPager = null;
    target.adsLayout = null;
    target.mAdView = null;
  }
}
