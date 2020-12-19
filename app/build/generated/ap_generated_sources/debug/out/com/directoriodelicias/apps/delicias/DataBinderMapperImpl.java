package com.directoriodelicias.apps.delicias;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentCustomSearchBindingImpl;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentProfileEditBindingImpl;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentProfilePasswordBindingImpl;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentProfileSignupBindingImpl;
import com.directoriodelicias.apps.delicias.databinding.V2StoreGalleryHorizontalBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_V2FRAGMENTCUSTOMSEARCH = 1;

  private static final int LAYOUT_V2FRAGMENTPROFILEEDIT = 2;

  private static final int LAYOUT_V2FRAGMENTPROFILEPASSWORD = 3;

  private static final int LAYOUT_V2FRAGMENTPROFILESIGNUP = 4;

  private static final int LAYOUT_V2STOREGALLERYHORIZONTAL = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.directoriodelicias.apps.delicias.R.layout.v2_fragment_custom_search, LAYOUT_V2FRAGMENTCUSTOMSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_edit, LAYOUT_V2FRAGMENTPROFILEEDIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_password, LAYOUT_V2FRAGMENTPROFILEPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_signup, LAYOUT_V2FRAGMENTPROFILESIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.directoriodelicias.apps.delicias.R.layout.v2_store_gallery_horizontal, LAYOUT_V2STOREGALLERYHORIZONTAL);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_V2FRAGMENTCUSTOMSEARCH: {
          if ("layout/v2_fragment_custom_search_0".equals(tag)) {
            return new V2FragmentCustomSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for v2_fragment_custom_search is invalid. Received: " + tag);
        }
        case  LAYOUT_V2FRAGMENTPROFILEEDIT: {
          if ("layout/v2_fragment_profile_edit_0".equals(tag)) {
            return new V2FragmentProfileEditBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for v2_fragment_profile_edit is invalid. Received: " + tag);
        }
        case  LAYOUT_V2FRAGMENTPROFILEPASSWORD: {
          if ("layout/v2_fragment_profile_password_0".equals(tag)) {
            return new V2FragmentProfilePasswordBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for v2_fragment_profile_password is invalid. Received: " + tag);
        }
        case  LAYOUT_V2FRAGMENTPROFILESIGNUP: {
          if ("layout/v2_fragment_profile_signup_0".equals(tag)) {
            return new V2FragmentProfileSignupBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for v2_fragment_profile_signup is invalid. Received: " + tag);
        }
        case  LAYOUT_V2STOREGALLERYHORIZONTAL: {
          if ("layout/v2_store_gallery_horizontal_0".equals(tag)) {
            return new V2StoreGalleryHorizontalBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for v2_store_gallery_horizontal is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/v2_fragment_custom_search_0", com.directoriodelicias.apps.delicias.R.layout.v2_fragment_custom_search);
      sKeys.put("layout/v2_fragment_profile_edit_0", com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_edit);
      sKeys.put("layout/v2_fragment_profile_password_0", com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_password);
      sKeys.put("layout/v2_fragment_profile_signup_0", com.directoriodelicias.apps.delicias.R.layout.v2_fragment_profile_signup);
      sKeys.put("layout/v2_store_gallery_horizontal_0", com.directoriodelicias.apps.delicias.R.layout.v2_store_gallery_horizontal);
    }
  }
}
