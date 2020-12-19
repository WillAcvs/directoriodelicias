// Generated code from Butter Knife. Do not modify!
package com.directoriodelicias.apps.delicias.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.directoriodelicias.apps.delicias.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutActivity_ViewBinding implements Unbinder {
  private AboutActivity target;

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutActivity_ViewBinding(AboutActivity target, View source) {
    this.target = target;

    target.version = Utils.findRequiredViewAsType(source, R.id.about_app_version, "field 'version'", TextView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.about_description, "field 'description'", TextView.class);
    target.mail = Utils.findRequiredViewAsType(source, R.id.about_mail, "field 'mail'", TextView.class);
    target.verion_content = Utils.findRequiredViewAsType(source, R.id.about_version, "field 'verion_content'", TextView.class);
    target.mail_content = Utils.findRequiredViewAsType(source, R.id.about_mail_content, "field 'mail_content'", TextView.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.about_phone, "field 'phone'", TextView.class);
    target.phone_content = Utils.findRequiredViewAsType(source, R.id.about_phone_content, "field 'phone_content'", TextView.class);
    target.description_content = Utils.findRequiredViewAsType(source, R.id.about_description_content, "field 'description_content'", TextView.class);
    target.APP_TITLE_VIEW = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'APP_TITLE_VIEW'", TextView.class);
    target.APP_DESC_VIEW = Utils.findRequiredViewAsType(source, R.id.toolbar_subtitle, "field 'APP_DESC_VIEW'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.app_bar, "field 'toolbar'", Toolbar.class);
    target.youtube = Utils.findRequiredViewAsType(source, R.id.youtube, "field 'youtube'", ImageView.class);
    target.facebook = Utils.findRequiredViewAsType(source, R.id.facebook, "field 'facebook'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.version = null;
    target.description = null;
    target.mail = null;
    target.verion_content = null;
    target.mail_content = null;
    target.phone = null;
    target.phone_content = null;
    target.description_content = null;
    target.APP_TITLE_VIEW = null;
    target.APP_DESC_VIEW = null;
    target.toolbar = null;
    target.youtube = null;
    target.facebook = null;
  }
}
