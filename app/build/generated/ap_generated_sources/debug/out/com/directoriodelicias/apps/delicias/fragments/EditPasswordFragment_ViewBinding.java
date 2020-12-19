// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.fragments;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.balysv.materialripple.MaterialRippleLayout;
import com.directoriodelicias.apps.delicias.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditPasswordFragment_ViewBinding implements Unbinder {
  private EditPasswordFragment target;

  @UiThread
  public EditPasswordFragment_ViewBinding(EditPasswordFragment target, View source) {
    this.target = target;

    target.oldPasswordTxt = Utils.findRequiredViewAsType(source, R.id.old_password, "field 'oldPasswordTxt'", TextInputEditText.class);
    target.newPasswordTxt = Utils.findRequiredViewAsType(source, R.id.new_password, "field 'newPasswordTxt'", TextInputEditText.class);
    target.confirmPasswordTxt = Utils.findRequiredViewAsType(source, R.id.confirm_password, "field 'confirmPasswordTxt'", TextInputEditText.class);
    target.saveBtn = Utils.findRequiredViewAsType(source, R.id.saveBtn, "field 'saveBtn'", MaterialRippleLayout.class);
    target.authBgImg = Utils.findRequiredViewAsType(source, R.id.auth_bg, "field 'authBgImg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EditPasswordFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.oldPasswordTxt = null;
    target.newPasswordTxt = null;
    target.confirmPasswordTxt = null;
    target.saveBtn = null;
    target.authBgImg = null;
  }
}
