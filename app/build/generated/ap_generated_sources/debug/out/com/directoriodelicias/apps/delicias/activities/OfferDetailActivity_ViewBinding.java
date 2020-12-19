// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.customView.StoreCardCustomView;
import com.google.android.gms.ads.AdView;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OfferDetailActivity_ViewBinding implements Unbinder {
  private OfferDetailActivity target;

  @UiThread
  public OfferDetailActivity_ViewBinding(OfferDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OfferDetailActivity_ViewBinding(OfferDetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.app_bar, "field 'toolbar'", Toolbar.class);
    target.badge_category = Utils.findRequiredViewAsType(source, R.id.badge_category, "field 'badge_category'", TextView.class);
    target.badge_price = Utils.findRequiredViewAsType(source, R.id.badge_price, "field 'badge_price'", TextView.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", ImageView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scroll_view, "field 'scrollView'", ParallaxScrollView.class);
    target.description_content = Utils.findRequiredViewAsType(source, R.id.description_content, "field 'description_content'", TextView.class);
    target.header_title = Utils.findRequiredViewAsType(source, R.id.header_title, "field 'header_title'", TextView.class);
    target.adsLayout = Utils.findRequiredViewAsType(source, R.id.adsLayout, "field 'adsLayout'", LinearLayout.class);
    target.offer_layout = Utils.findRequiredViewAsType(source, R.id.offer_layout, "field 'offer_layout'", LinearLayout.class);
    target.mAdView = Utils.findRequiredViewAsType(source, R.id.adView, "field 'mAdView'", AdView.class);
    target.offer_up_to = Utils.findRequiredViewAsType(source, R.id.offer_up_to, "field 'offer_up_to'", TextView.class);
    target.customStoreCV = Utils.findRequiredViewAsType(source, R.id.customStoreCV, "field 'customStoreCV'", StoreCardCustomView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OfferDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.badge_category = null;
    target.badge_price = null;
    target.image = null;
    target.scrollView = null;
    target.description_content = null;
    target.header_title = null;
    target.adsLayout = null;
    target.offer_layout = null;
    target.mAdView = null;
    target.offer_up_to = null;
    target.customStoreCV = null;
  }
}
