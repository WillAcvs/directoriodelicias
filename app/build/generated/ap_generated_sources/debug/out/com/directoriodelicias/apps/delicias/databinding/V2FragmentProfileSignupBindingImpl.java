package com.directoriodelicias.apps.delicias.databinding;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class V2FragmentProfileSignupBindingImpl extends V2FragmentProfileSignupBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.signup_layout, 1);
        sViewsWithIds.put(R.id.toolbar, 2);
        sViewsWithIds.put(R.id.btnBack, 3);
        sViewsWithIds.put(R.id.toolbar_title, 4);
        sViewsWithIds.put(R.id.toolbar_subtitle, 5);
        sViewsWithIds.put(R.id.auth_bg, 6);
        sViewsWithIds.put(R.id.loginLayout, 7);
        sViewsWithIds.put(R.id.login, 8);
        sViewsWithIds.put(R.id.password, 9);
        sViewsWithIds.put(R.id.connect, 10);
        sViewsWithIds.put(R.id.forget_password, 11);
        sViewsWithIds.put(R.id.createAccountBtn, 12);
        sViewsWithIds.put(R.id.signupLayout, 13);
        sViewsWithIds.put(R.id.framePhoto, 14);
        sViewsWithIds.put(R.id.userimage, 15);
        sViewsWithIds.put(R.id.takePicture, 16);
        sViewsWithIds.put(R.id.login_signup, 17);
        sViewsWithIds.put(R.id.email_signup, 18);
        sViewsWithIds.put(R.id.full_name_signup, 19);
        sViewsWithIds.put(R.id.password_signup, 20);
        sViewsWithIds.put(R.id.btn_signup, 21);
        sViewsWithIds.put(R.id.haveAccountBtn, 22);
        sViewsWithIds.put(R.id.changePasswordLayout, 23);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public V2FragmentProfileSignupBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private V2FragmentProfileSignupBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[3]
            , (com.balysv.materialripple.MaterialRippleLayout) bindings[21]
            , (android.widget.LinearLayout) bindings[23]
            , (com.balysv.materialripple.MaterialRippleLayout) bindings[10]
            , (android.widget.TextView) bindings[12]
            , (com.google.android.material.textfield.TextInputEditText) bindings[18]
            , (android.widget.TextView) bindings[11]
            , (android.widget.FrameLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputEditText) bindings[19]
            , (android.widget.TextView) bindings[22]
            , (com.google.android.material.textfield.TextInputEditText) bindings[8]
            , (android.widget.LinearLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputEditText) bindings[17]
            , (com.google.android.material.textfield.TextInputEditText) bindings[9]
            , (com.google.android.material.textfield.TextInputEditText) bindings[20]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (com.github.siyamed.shapeimageview.CircularImageView) bindings[15]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}