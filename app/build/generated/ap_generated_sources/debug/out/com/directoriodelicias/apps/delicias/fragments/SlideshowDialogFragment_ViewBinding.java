// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.fragments;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SlideshowDialogFragment_ViewBinding implements Unbinder {
  private SlideshowDialogFragment target;

  @UiThread
  public SlideshowDialogFragment_ViewBinding(SlideshowDialogFragment target, View source) {
    this.target = target;

    target.menu = Utils.findRequiredViewAsType(source, R.id.menu, "field 'menu'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SlideshowDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.menu = null;
  }
}
