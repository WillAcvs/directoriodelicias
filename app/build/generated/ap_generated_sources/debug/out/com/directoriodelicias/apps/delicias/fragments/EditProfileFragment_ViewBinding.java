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
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditProfileFragment_ViewBinding implements Unbinder {
  private EditProfileFragment target;

  @UiThread
  public EditProfileFragment_ViewBinding(EditProfileFragment target, View source) {
    this.target = target;

    target.loginTxt = Utils.findRequiredViewAsType(source, R.id.login, "field 'loginTxt'", TextInputEditText.class);
    target.fullNameTxt = Utils.findRequiredViewAsType(source, R.id.full_name, "field 'fullNameTxt'", TextInputEditText.class);
    target.emailTxt = Utils.findRequiredViewAsType(source, R.id.email, "field 'emailTxt'", TextInputEditText.class);
    target.saveBtn = Utils.findRequiredViewAsType(source, R.id.saveBtn, "field 'saveBtn'", MaterialRippleLayout.class);
    target.takePicture = Utils.findRequiredViewAsType(source, R.id.takePicture, "field 'takePicture'", ImageView.class);
    target.userimage = Utils.findRequiredViewAsType(source, R.id.userimage, "field 'userimage'", CircularImageView.class);
    target.authBgImg = Utils.findRequiredViewAsType(source, R.id.auth_bg, "field 'authBgImg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EditProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginTxt = null;
    target.fullNameTxt = null;
    target.emailTxt = null;
    target.saveBtn = null;
    target.takePicture = null;
    target.userimage = null;
    target.authBgImg = null;
  }
}
