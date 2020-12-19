// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.customView.StoreCardCustomView;
import com.google.android.gms.ads.AdView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EventDetailActivity_ViewBinding implements Unbinder {
  private EventDetailActivity target;

  @UiThread
  public EventDetailActivity_ViewBinding(EventDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EventDetailActivity_ViewBinding(EventDetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.app_bar, "field 'toolbar'", Toolbar.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", ImageView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scroll_view, "field 'scrollView'", NestedScrollView.class);
    target.customStoreCV = Utils.findRequiredViewAsType(source, R.id.customStoreCV, "field 'customStoreCV'", StoreCardCustomView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description_content, "field 'description'", TextView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.address, "field 'address'", TextView.class);
    target.event_date_begin = Utils.findRequiredViewAsType(source, R.id.event_date_begin, "field 'event_date_begin'", TextView.class);
    target.event_begin_date_end = Utils.findRequiredViewAsType(source, R.id.event_begin_date_end, "field 'event_begin_date_end'", TextView.class);
    target.event_day = Utils.findRequiredViewAsType(source, R.id.day_calendar, "field 'event_day'", TextView.class);
    target.event_month = Utils.findRequiredViewAsType(source, R.id.month_calendar, "field 'event_month'", TextView.class);
    target.header_title = Utils.findRequiredViewAsType(source, R.id.header_title, "field 'header_title'", TextView.class);
    target.progressMapLL = Utils.findRequiredViewAsType(source, R.id.progressMapLL, "field 'progressMapLL'", LinearLayout.class);
    target.joinBtn = Utils.findRequiredViewAsType(source, R.id.joinBtn, "field 'joinBtn'", Button.class);
    target.unjoinBtn = Utils.findRequiredViewAsType(source, R.id.unjoinBtn, "field 'unjoinBtn'", Button.class);
    target.phoneBtn = Utils.findRequiredViewAsType(source, R.id.phoneBtn, "field 'phoneBtn'", Button.class);
    target.webBtn = Utils.findRequiredViewAsType(source, R.id.webBtn, "field 'webBtn'", Button.class);
    target.mapcontainer = Utils.findRequiredViewAsType(source, R.id.maps_container, "field 'mapcontainer'", LinearLayout.class);
    target.mapping = Utils.findRequiredView(source, R.id.mapping, "field 'mapping'");
    target.adsLayout = Utils.findRequiredViewAsType(source, R.id.adsLayout, "field 'adsLayout'", LinearLayout.class);
    target.mAdView = Utils.findRequiredViewAsType(source, R.id.adView, "field 'mAdView'", AdView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EventDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.image = null;
    target.scrollView = null;
    target.customStoreCV = null;
    target.description = null;
    target.address = null;
    target.event_date_begin = null;
    target.event_begin_date_end = null;
    target.event_day = null;
    target.event_month = null;
    target.header_title = null;
    target.progressMapLL = null;
    target.joinBtn = null;
    target.unjoinBtn = null;
    target.phoneBtn = null;
    target.webBtn = null;
    target.mapcontainer = null;
    target.mapping = null;
    target.adsLayout = null;
    target.mAdView = null;
  }
}
