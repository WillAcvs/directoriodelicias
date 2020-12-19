// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CustomSearchFragment_ViewBinding implements Unbinder {
  private CustomSearchFragment target;

  @UiThread
  public CustomSearchFragment_ViewBinding(CustomSearchFragment target, View source) {
    this.target = target;

    target.rangeSeekBar = Utils.findRequiredViewAsType(source, R.id.range_seek_bar, "field 'rangeSeekBar'", SeekBar.class);
    target.range_seek_bar_text = Utils.findRequiredViewAsType(source, R.id.range_seek_bar_text, "field 'range_seek_bar_text'", TextView.class);
    target.searchField = Utils.findRequiredViewAsType(source, R.id.searchField, "field 'searchField'", TextView.class);
    target.dateBeginTxt = Utils.findRequiredViewAsType(source, R.id.date_begin_txt, "field 'dateBeginTxt'", TextInputEditText.class);
    target.filterStoresBtn = Utils.findRequiredViewAsType(source, R.id.filterStoresBtn, "field 'filterStoresBtn'", Button.class);
    target.filterEventsBtn = Utils.findRequiredViewAsType(source, R.id.filterEventsBtn, "field 'filterEventsBtn'", Button.class);
    target.filterOffersBtn = Utils.findRequiredViewAsType(source, R.id.filterOffersBtn, "field 'filterOffersBtn'", Button.class);
    target.searchStores = Utils.findRequiredViewAsType(source, R.id.searchStores, "field 'searchStores'", LinearLayout.class);
    target.searchOffers = Utils.findRequiredViewAsType(source, R.id.searchOffers, "field 'searchOffers'", LinearLayout.class);
    target.searchEvents = Utils.findRequiredViewAsType(source, R.id.searchEvents, "field 'searchEvents'", LinearLayout.class);
    target.searchFilterCategory = Utils.findRequiredViewAsType(source, R.id.searchFilterCategory, "field 'searchFilterCategory'", LinearLayout.class);
    target.btnSearchLayout = Utils.findRequiredViewAsType(source, R.id.btnSearchLayout, "field 'btnSearchLayout'", Button.class);
    target.btnsOffersPrice = Utils.findRequiredViewAsType(source, R.id.btnsOffersPrice, "field 'btnsOffersPrice'", LinearLayout.class);
    target.price_offer_btn = Utils.findRequiredViewAsType(source, R.id.price_offer_btn, "field 'price_offer_btn'", Button.class);
    target.discount_offer_btn = Utils.findRequiredViewAsType(source, R.id.discount_offer_btn, "field 'discount_offer_btn'", Button.class);
    target.btnsOffersDiscountProps = Utils.findRequiredViewAsType(source, R.id.btnsOffersDiscountProps, "field 'btnsOffersDiscountProps'", LinearLayout.class);
    target.btnsOffersPriceFormat = Utils.findRequiredViewAsType(source, R.id.btnsOffersPriceFormat, "field 'btnsOffersPriceFormat'", LinearLayout.class);
    target.openStatusCB = Utils.findRequiredViewAsType(source, R.id.openStatusCB, "field 'openStatusCB'", AppCompatCheckBox.class);
    target.orderByDate = Utils.findRequiredViewAsType(source, R.id.orderByDate, "field 'orderByDate'", Button.class);
    target.orderByGeo = Utils.findRequiredViewAsType(source, R.id.orderByGeo, "field 'orderByGeo'", Button.class);
    target.layoutLocationChanger = Utils.findRequiredViewAsType(source, R.id.layoutLocationChanger, "field 'layoutLocationChanger'", LinearLayout.class);
    target.locationLbl = Utils.findRequiredViewAsType(source, R.id.locationLbl, "field 'locationLbl'", TextView.class);
    target.btnsModules = Utils.findRequiredViewAsType(source, R.id.btnsModules, "field 'btnsModules'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CustomSearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rangeSeekBar = null;
    target.range_seek_bar_text = null;
    target.searchField = null;
    target.dateBeginTxt = null;
    target.filterStoresBtn = null;
    target.filterEventsBtn = null;
    target.filterOffersBtn = null;
    target.searchStores = null;
    target.searchOffers = null;
    target.searchEvents = null;
    target.searchFilterCategory = null;
    target.btnSearchLayout = null;
    target.btnsOffersPrice = null;
    target.price_offer_btn = null;
    target.discount_offer_btn = null;
    target.btnsOffersDiscountProps = null;
    target.btnsOffersPriceFormat = null;
    target.openStatusCB = null;
    target.orderByDate = null;
    target.orderByGeo = null;
    target.layoutLocationChanger = null;
    target.locationLbl = null;
    target.btnsModules = null;
  }
}
