// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class AuthenticationFragment_ViewBinding implements Unbinder {
  private AuthenticationFragment target;

  @UiThread
  public AuthenticationFragment_ViewBinding(AuthenticationFragment target, View source) {
    this.target = target;

    target.loginLayout = Utils.findRequiredViewAsType(source, R.id.loginLayout, "field 'loginLayout'", LinearLayout.class);
    target.signupLayout = Utils.findRequiredViewAsType(source, R.id.signupLayout, "field 'signupLayout'", LinearLayout.class);
    target.changePasswordLayout = Utils.findRequiredViewAsType(source, R.id.changePasswordLayout, "field 'changePasswordLayout'", LinearLayout.class);
    target.createAccountBtn = Utils.findRequiredViewAsType(source, R.id.createAccountBtn, "field 'createAccountBtn'", TextView.class);
    target.haveAccountBtn = Utils.findRequiredViewAsType(source, R.id.haveAccountBtn, "field 'haveAccountBtn'", TextView.class);
    target.forget_password = Utils.findRequiredViewAsType(source, R.id.forget_password, "field 'forget_password'", TextView.class);
    target.loginTxt = Utils.findRequiredViewAsType(source, R.id.login, "field 'loginTxt'", TextInputEditText.class);
    target.paswordTxt = Utils.findRequiredViewAsType(source, R.id.password, "field 'paswordTxt'", TextInputEditText.class);
    target.loginBtn = Utils.findRequiredViewAsType(source, R.id.connect, "field 'loginBtn'", MaterialRippleLayout.class);
    target.loginSignupTxt = Utils.findRequiredViewAsType(source, R.id.login_signup, "field 'loginSignupTxt'", TextInputEditText.class);
    target.emailSignupTxt = Utils.findRequiredViewAsType(source, R.id.email_signup, "field 'emailSignupTxt'", TextInputEditText.class);
    target.fullNameSignupTxt = Utils.findRequiredViewAsType(source, R.id.full_name_signup, "field 'fullNameSignupTxt'", TextInputEditText.class);
    target.passwordSignupTxt = Utils.findRequiredViewAsType(source, R.id.password_signup, "field 'passwordSignupTxt'", TextInputEditText.class);
    target.btnSignUp = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field 'btnSignUp'", MaterialRippleLayout.class);
    target.userimage = Utils.findRequiredViewAsType(source, R.id.userimage, "field 'userimage'", CircularImageView.class);
    target.takePicture = Utils.findRequiredViewAsType(source, R.id.takePicture, "field 'takePicture'", ImageView.class);
    target.authBgImg = Utils.findRequiredViewAsType(source, R.id.auth_bg, "field 'authBgImg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthenticationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginLayout = null;
    target.signupLayout = null;
    target.changePasswordLayout = null;
    target.createAccountBtn = null;
    target.haveAccountBtn = null;
    target.forget_password = null;
    target.loginTxt = null;
    target.paswordTxt = null;
    target.loginBtn = null;
    target.loginSignupTxt = null;
    target.emailSignupTxt = null;
    target.fullNameSignupTxt = null;
    target.passwordSignupTxt = null;
    target.btnSignUp = null;
    target.userimage = null;
    target.takePicture = null;
    target.authBgImg = null;
  }
}
