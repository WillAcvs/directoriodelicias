package com.directoriodelicias.apps.delicias.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.MainActivity;
import com.directoriodelicias.apps.delicias.activities.SplashActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.sessions.GuestController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentProfileSignupBinding;
import com.directoriodelicias.apps.delicias.helper.AppHelper;
import com.directoriodelicias.apps.delicias.helper.CommunFunctions;
import com.directoriodelicias.apps.delicias.navigationdrawer.NavigationDrawerFragment;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.UserParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.ImageUtils;
import com.directoriodelicias.apps.delicias.utils.MessageDialog;
import com.directoriodelicias.apps.delicias.utils.Translator;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.directoriodelicias.apps.delicias.views.CustomDialog;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.wuadam.awesomewebview.AwesomeWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class AuthenticationFragment extends Fragment implements ImageUtils.PrepareImagesData.OnCompressListner {


    @BindView(R.id.loginLayout)
    LinearLayout loginLayout;

    @BindView(R.id.signupLayout)
    LinearLayout signupLayout;

    @BindView(R.id.changePasswordLayout)
    LinearLayout changePasswordLayout;

    @BindView(R.id.createAccountBtn)
    TextView createAccountBtn;

    @BindView(R.id.haveAccountBtn)
    TextView haveAccountBtn;

    @BindView(R.id.forget_password)
    TextView forget_password;


    //Login layout
    @BindView(R.id.login)
    TextInputEditText loginTxt;

    @BindView(R.id.password)
    TextInputEditText paswordTxt;

    @BindView(R.id.connect)
    MaterialRippleLayout loginBtn;


    //Signup Layout
    @BindView(R.id.login_signup)
    TextInputEditText loginSignupTxt;

    @BindView(R.id.email_signup)
    TextInputEditText emailSignupTxt;

    @BindView(R.id.full_name_signup)
    TextInputEditText fullNameSignupTxt;

    @BindView(R.id.password_signup)
    TextInputEditText passwordSignupTxt;

    @BindView(R.id.btn_signup)
    MaterialRippleLayout btnSignUp;

    @BindView(R.id.userimage)
    CircularImageView userimage;

    @BindView(R.id.takePicture)
    ImageView takePicture;

    @BindView(R.id.auth_bg)
    ImageView authBgImg;


    private ProgressDialog mPdialog;

    private GPStracker gps;

    private RequestQueue queue;

    private CustomDialog mDialogError;

    private int GALLERY_REQUEST = 103;

    private Uri imageToUpload = null;

    private String[] header_bg_list = {
            "auth_bg/login_bg_1.jpg",
            "auth_bg/login_bg_2.jpg",
            "auth_bg/login_bg_3.jpg",
            "auth_bg/login_bg_4.jpg",
            "auth_bg/login_bg_5.jpg",
            "auth_bg/login_bg_6.jpg",
    };


    // newInstance constructor for creating fragment with arguments
    public static AuthenticationFragment newInstance(int page, String title) {
        AuthenticationFragment fragmentFirst = new AuthenticationFragment();
        Bundle args = new Bundle();
        args.putInt("id", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gps = new GPStracker(this.getActivity());
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();


    }

    public void requestPermissionForReadExtertalStorage() {

        try {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.GONE);
                signupLayout.setVisibility(View.VISIBLE);
                changePasswordLayout.setVisibility(View.GONE);
            }
        });

        haveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.GONE);
                changePasswordLayout.setVisibility(View.GONE);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignup();
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromGallery();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        V2FragmentProfileSignupBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.v2_fragment_profile_signup, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        initToolbar(view);

        setup_background_img(view);


        return view;
    }

    private void initToolbar(View view) {
        LinearLayout mToolbar = view.findViewById(R.id.toolbar);

        if (AppController.isRTL()) {
            Drawable arrowIcon = getResources().getDrawable(R.drawable.ic_arrow_forward_white_18dp);
            ((ImageView) mToolbar.findViewById(R.id.btnBack)).setImageDrawable(arrowIcon);
        }
        mToolbar.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });
    }


    private void backToHomePage() {

        //enable nav drawer when fragment is closed
        if (NavigationDrawerFragment.getInstance() != null)
            NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            getActivity().onBackPressed();
        }

        getActivity().overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
        V2MainFragment mf = (V2MainFragment) getFragmentManager().findFragmentByTag(V2MainFragment.TAG);
        if (mf != null) {
            mf.setCurrentFragment(0);
        }
    }

    private void handleBackPressedEvent() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    backToHomePage();
                    getView().setFocusableInTouchMode(false);
                    return true;
                }
                return false;
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        handleBackPressedEvent();

        //disable navigation drawer when fragment is opened
        //NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }

    @Override
    public void onPause() {
        super.onPause();

        //enable nav drawer when fragment is closed
        //NavigationDrawerFragment.getInstance().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    private void doLogin() {


        loginBtn.setEnabled(false);

        FragmentManager manager = getChildFragmentManager();

        mPdialog = new ProgressDialog(this.getActivity());
        mPdialog.setMessage(getString(R.string.loading));
        mPdialog.show();

        final double lat = gps.getLatitude();
        final double lng = gps.getLongitude();


        int guest_id = 0;

        if (GuestController.isStored())
            guest_id = GuestController.getGuest().getId();


        final int finalGuest_id = guest_id;
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loginBtn.setEnabled(true);

                mPdialog.dismiss();

                try {

                    if (APP_DEBUG) {
                        Log.i("responseLogin", response);
                    }

                    JSONObject js = new JSONObject(response);
                    UserParser mUserParser = new UserParser(js);

                    int success = Integer.parseInt(mUserParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1) {

                        List<User> list = mUserParser.getUser();


                        if (list.size() > 0) {

                            SessionsController.createSession(list.get(0));

                               /* //hide Keyboard
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);*/

                            //Go back to the Home Fragment
                            ActivityCompat.finishAffinity(getActivity());
                            startActivity(new Intent(getActivity(), SplashActivity.class));
                            //getActivity().finish();

                        }


                    } else {


                        Map<String, String> errors = mUserParser.getErrors();

                        MessageDialog.newDialog(getContext()).onCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MessageDialog.getInstance().hide();
                            }
                        }).onOkClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MessageDialog.getInstance().hide();
                            }
                        }).setContent(Translator.print(getString(R.string.authentification_error_msg), "Message showError")).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    MessageDialog.newDialog(getContext()).onCancelClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MessageDialog.getInstance().hide();
                        }
                    }).onOkClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MessageDialog.getInstance().hide();
                        }
                    }).setContent(Translator.print(getString(R.string.authentification_error_msg), "Message showError (Parser)")).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                loginBtn.setEnabled(true);

                mPdialog.dismiss();

                Map<String, String> errors = new HashMap<String, String>();
                errors.put("NetworkException:", getString(R.string.check_network));
                mDialogError = CommunFunctions.showErrors(errors, getContext());
                mDialogError.setTitle(getString(R.string.network_error));

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("token", Utils.getToken(getContext()));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("password", paswordTxt.getText().toString());
                params.put("login", loginTxt.getText().toString());
                params.put("lat", String.valueOf(lat));
                params.put("lng", String.valueOf(lng));
                params.put("guest_id", String.valueOf(finalGuest_id));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


    private void doSignup() {


        loginBtn.setEnabled(false);

        FragmentManager manager = getChildFragmentManager();

        mPdialog = new ProgressDialog(this.getActivity());
        mPdialog.setMessage("Loading ...");
        mPdialog.show();

        final double lat = gps.getLatitude();
        final double lng = gps.getLongitude();


        int guest_id = 0;

        if (GuestController.isStored())
            guest_id = GuestController.getGuest().getId();


        final int finalGuest_id = guest_id;
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loginBtn.setEnabled(true);

                mPdialog.dismiss();

                try {

                    if (APP_DEBUG) {
                        Log.i("response", response);
                    }

                    JSONObject js = new JSONObject(response);
                    UserParser mUserParser = new UserParser(js);

                    int success = Integer.parseInt(mUserParser.getStringAttr(Tags.SUCCESS));

                    if (success == 1) {

                        List<User> list = mUserParser.getUser();


                        if (list.size() > 0) {

                            if (imageToUpload != null)
                                uploadImage(list.get(0).getId());


                            SessionsController.createSession(list.get(0));

                               /* //hide Keyboard
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);*/

                            //Go back to the Home Fragment
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();

                        }


                    } else {


                        Map<String, String> errors = mUserParser.getErrors();


                        MessageDialog.newDialog(getActivity()).onCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MessageDialog.getInstance().hide();
                            }
                        }).onOkClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MessageDialog.getInstance().hide();
                            }
                        }).setContent(Translator.print(CommunFunctions.convertMessages(errors), "Message showError")).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    MessageDialog.newDialog(getContext()).onCancelClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MessageDialog.getInstance().hide();
                        }
                    }).onOkClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MessageDialog.getInstance().hide();
                        }
                    }).setContent(Translator.print(getString(R.string.authentification_error_msg), "Message showError (Parser)")).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                loginBtn.setEnabled(true);

                mPdialog.dismiss();

                Map<String, String> errors = new HashMap<String, String>();
                errors.put("NetworkException:", getString(R.string.check_network));
                mDialogError = CommunFunctions.showErrors(errors, getContext());
                mDialogError.setTitle(getString(R.string.network_error));

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", loginSignupTxt.getText().toString().trim());
                params.put("name", fullNameSignupTxt.getText().toString().trim());
                params.put("email", emailSignupTxt.getText().toString().trim());
                params.put("token", Utils.getToken(getContext()));
                params.put("mac_adr", ServiceHandler.getMacAddr());
                params.put("password", passwordSignupTxt.getText().toString());
                params.put("lat", String.valueOf(lat));
                params.put("lng", String.valueOf(lng));
                params.put("auth_type", "mobile");
                params.put("guest_id", String.valueOf(finalGuest_id));

                if (APP_DEBUG) {
                    Log.e("Authentication", " params :" + params.toString());
                }

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private void forgetPassword() {
        new AwesomeWebView.Builder(getActivity())
                .statusBarColorRes(R.color.colorPrimary)
                .theme(R.style.FinestWebViewAppTheme)
                .show(Constances.FORGET_PASSWORD);
    }


    private void getFromGallery() {

        if (checkPermissionForReadExtertalStorage()) {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, GALLERY_REQUEST);
        } else {
            requestPermissionForReadExtertalStorage();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST) {

            try {

                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                try {

                    String createNewFileDest = CommunFunctions.createImageFile(getActivity());

                    new ImageUtils.PrepareImagesData(
                            getActivity(),
                            picturePath,
                            bitmap,
                            createNewFileDest,
                            this).execute();

                    userimage.setImageBitmap(bitmap);


                } catch (IOException e) {

                    if (AppContext.DEBUG)
                        e.printStackTrace();

                }

            } catch (Exception e) {
                if (AppContext.DEBUG)
                    e.printStackTrace();
            }


        }
    }


    private void uploadImage(final int uid) {

        Toast.makeText(getContext(), getString(R.string.fileUploading), Toast.LENGTH_LONG).show();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_UPLOAD64, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // pdialog.dismiss();
                try {

                    if (APP_DEBUG)
                        Log.i("SignUpUload", response);
                    JSONObject js = new JSONObject(response);

                    UserParser mUserParser = new UserParser(js);
                    int success = Integer.parseInt(mUserParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {

                        final List<User> list = mUserParser.getUser();
                        if (list.size() > 0) {
                            SessionsController.updateSession(list.get(0));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
                //pdialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Bitmap bm = BitmapFactory.decodeFile(imageToUpload.getPath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                params.put("image", encodedImage);

                params.put("int_id", String.valueOf(uid));
                params.put("type", "user");

                //do else
                params.put("module_id", String.valueOf(uid));
                params.put("module", "user");


                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    @Override
    public void onCompressed(String newPath, String oldPath) {
        if (APP_DEBUG)
            Toast.makeText(getContext(), "PATH:" + newPath, Toast.LENGTH_SHORT).show();

        File mFile = new File(newPath);

        Glide.with(this).load(mFile).centerCrop()
                .placeholder(R.drawable.profile_placeholder).into(userimage);

        imageToUpload = Uri.parse(newPath);
    }


    private void setup_background_img(View rootview) {

        Random Dice = new Random();
        int n = Dice.nextInt(header_bg_list.length);

        Glide.with(this)
                .load(AppHelper.loadDrawable(getActivity(), header_bg_list[n]))
                .centerCrop().into(authBgImg);


    }
}
