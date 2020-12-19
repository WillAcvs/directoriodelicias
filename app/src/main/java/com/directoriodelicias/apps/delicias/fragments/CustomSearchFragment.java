package com.directoriodelicias.apps.delicias.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.activities.CustomSearchActivity;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.customView.CategoryCustomView;
import com.directoriodelicias.apps.delicias.databinding.V2FragmentCustomSearchBindingImpl;
import com.directoriodelicias.apps.delicias.utils.Tools;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.directoriodelicias.apps.delicias.AppController.getInstance;


public class CustomSearchFragment extends Fragment implements View.OnClickListener {

    public static Class<?> previousPageClass = null;
    private static int mOldDistance = -1;
    private static String mOldValue = "";
    @BindView(R.id.range_seek_bar)
    SeekBar rangeSeekBar;
    @BindView(R.id.range_seek_bar_text)
    TextView range_seek_bar_text;
    @BindView(R.id.searchField)
    TextView searchField;
    @BindView(R.id.date_begin_txt)
    TextInputEditText dateBeginTxt;
    @BindView(R.id.filterStoresBtn)
    Button filterStoresBtn;
    @BindView(R.id.filterEventsBtn)
    Button filterEventsBtn;
    @BindView(R.id.filterOffersBtn)
    Button filterOffersBtn;
    @BindView(R.id.searchStores)
    LinearLayout searchStores;
    @BindView(R.id.searchOffers)
    LinearLayout searchOffers;
    @BindView(R.id.searchEvents)
    LinearLayout searchEvents;
    @BindView(R.id.searchFilterCategory)
    LinearLayout searchFilterCategory;
    @BindView(R.id.btnSearchLayout)
    Button btnSearchLayout;
    @BindView(R.id.btnsOffersPrice)
    LinearLayout btnsOffersPrice;
    @BindView(R.id.price_offer_btn)
    Button price_offer_btn;
    @BindView(R.id.discount_offer_btn)
    Button discount_offer_btn;
    @BindView(R.id.btnsOffersDiscountProps)
    LinearLayout btnsOffersDiscountProps;
    @BindView(R.id.btnsOffersPriceFormat)
    LinearLayout btnsOffersPriceFormat;
    @BindView(R.id.openStatusCB)
    AppCompatCheckBox openStatusCB;
    @BindView(R.id.orderByDate)
    Button orderByDate;
    @BindView(R.id.orderByGeo)
    Button orderByGeo;
    @BindView(R.id.layoutLocationChanger)
    LinearLayout layoutLocationChanger;
    @BindView(R.id.locationLbl)
    TextView locationLbl;

    @BindView(R.id.btnsModules)
    LinearLayout btnsModules;

    private CategoryCustomView rectCategoryList;
    private Context mContext;
    private HashMap<String, Object> searchParams;
    private int AUTOCOMPLETE_REQUEST_CODE = 1001;
    private int REQUEST_RANGE_RADIUS = -1;
    private Button[] btnOffersDiscount = new Button[4], btnOffersPrice = new Button[4];
    private Button btn_discount_unfocus, btn_price_unfocus;
    private int[] btn_id_discount = {R.id.discount_less_than_25, R.id.discount_less_than_50, R.id.discount_less_than_75, R.id.discount_less_than_100};
    private int[] btn_id_price = {R.id.price_one_number, R.id.price_two_numbers, R.id.price_three_numbers, R.id.price_four_numbers};


    public CustomSearchFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        V2FragmentCustomSearchBindingImpl binding = DataBindingUtil.inflate(
                inflater, R.layout.v2_fragment_custom_search, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);


        initParams(view);

        //imageEnlargeAfterClick(view.findViewById(R.id.im));

        //INIT TOOLBAR
        //initToolbar(view, mContext);

        initCategoryRV(view);

        initRangeSeekBar();


        handleClickEventListener();

        initPlacesAPi(view.getContext());


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //set store as a default view
        setDefaultModuleSelected(getView(), (getArguments() != null && getArguments().containsKey("selected_module")) ? getArguments().getString("selected_module") : "store");


        //get data from cache if exist
        if (getArguments() != null && getArguments().containsKey("useCacheFields") && getArguments().getString("useCacheFields").equals("enabled")) {

            HashMap<String, Object> cacheSearchObj = null;
            if (getArguments().containsKey("searchParams")) {  //this will serve when choosing a category from home fragment
                cacheSearchObj = (HashMap<String, Object>) getArguments().getSerializable("searchParams");
            } else { // if there's no fields then we should get the detail from cache
                cacheSearchObj = getSavedSearchFields();
            }

            if (cacheSearchObj != null) {
                HashMap<String, Object> finalCacheSearchObj = cacheSearchObj;
                (new android.os.Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        importDataFromPrefObj(finalCacheSearchObj);
                    }
                }, 800);
            }
        }


        //disable module filter buttons for maps activity
        if (previousPageClass != null)
            btnsModules.setVisibility(View.GONE);


    }

    private void initPlacesAPi(Context context) {

        String apiKey = getString(R.string.places_api_key);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(context, apiKey);
        }

        // Create a new Places client instance.
        //PlacesClient placesClient = Places.createClient(context);


        putDrawableText(getResources().getString(R.string.current_location), CommunityMaterial.Icon.cmd_crosshairs_gps);

    }

    private void getLocationGP() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_place_autocomplet);
        dialog.setCancelable(true);


        dialog.findViewById(R.id.change_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getActivity());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.keep_current_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchParams.containsKey("latitude") && searchParams.containsKey("longitude")) {
                    searchParams.remove("latitude");
                    searchParams.remove("longitude");
                }

                putDrawableText(getResources().getString(R.string.current_location), CommunityMaterial.Icon.cmd_crosshairs_gps);

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("CustomSearchFrag", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress() + ", " + place.getLatLng());
                if (place != null && place.getLatLng() != null) {
                    searchParams.put("latitude", place.getLatLng().latitude);
                    searchParams.put("longitude", place.getLatLng().longitude);
                    searchParams.put("city", place.getName());
                    putDrawableText(place.getName(), CommunityMaterial.Icon.cmd_crosshairs_gps);
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the showError.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("CustomSearchFrag", status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void putDrawableText(String text, CommunityMaterial.Icon cmd_map_marker) {

        //change text
        locationLbl.setText(text);

        //set the marker when location is changed
        Drawable locationDrawable = new IconicsDrawable(getActivity())
                .icon(cmd_map_marker)
                .color(ResourcesCompat.getColor(getActivity().getResources(), R.color.colorAccent, null))
                .sizeDp(12);

        locationLbl.setCompoundDrawables(locationDrawable, null, null, null);
        locationLbl.setCompoundDrawablePadding(8);
    }

    private void offersDiscountBtnClick() {
        for (int i = 0; i < btnOffersDiscount.length; i++) {
            btnOffersDiscount[i] = btnsOffersDiscountProps.findViewById(btn_id_discount[i]);
            btnOffersDiscount[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchParams.put("discount_selected_value", v.getTag());

                    switch (v.getId()) {

                        case R.id.discount_less_than_25:

                            setDiscountFocus(btn_discount_unfocus, btnOffersDiscount[0]);
                            break;

                        case R.id.discount_less_than_50:
                            setDiscountFocus(btn_discount_unfocus, btnOffersDiscount[1]);
                            break;

                        case R.id.discount_less_than_75:
                            setDiscountFocus(btn_discount_unfocus, btnOffersDiscount[2]);
                            break;

                        case R.id.discount_less_than_100:
                            setDiscountFocus(btn_discount_unfocus, btnOffersDiscount[3]);
                            break;
                    }

                }
            });
        }

        btn_discount_unfocus = btnOffersDiscount[0];
    }

    private void setDiscountFocus(Button btn_discount_unfocus, Button btn_focus) {
        if (btn_discount_unfocus.getId() == btn_focus.getId()) {
            btn_focus.setSelected(!btn_focus.isSelected());
        } else {
            btn_focus.setSelected(true);
            btn_discount_unfocus.setSelected(false);
        }
        this.btn_discount_unfocus = btn_focus;

        searchParams.put("value_type", "percent");
        searchParams.put("offer_value", btn_focus.getText().toString());
    }


    private void offersPriceBtnClick() {
        for (int i = 0; i < btnOffersPrice.length; i++) {
            btnOffersPrice[i] = btnsOffersPriceFormat.findViewById(btn_id_price[i]);
            btnOffersPrice[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //save button state
                    searchParams.put("offer_selected_value", v.getTag());

                    switch (v.getId()) {
                        case R.id.price_one_number:
                            setPriceFocus(btn_price_unfocus, btnOffersPrice[0]);
                            break;
                        case R.id.price_two_numbers:
                            setPriceFocus(btn_price_unfocus, btnOffersPrice[1]);
                            break;
                        case R.id.price_three_numbers:
                            setPriceFocus(btn_price_unfocus, btnOffersPrice[2]);
                            break;

                        case R.id.price_four_numbers:
                            setPriceFocus(btn_price_unfocus, btnOffersPrice[3]);
                            break;
                    }

                }
            });
        }

        btn_price_unfocus = btnOffersPrice[0];

    }

    @SuppressLint("ResourceType")
    private void setPriceFocus(Button btn_discount_unfocus, Button btn_focus) {
        if (btn_discount_unfocus.getId() == btn_focus.getId()) {
            btn_focus.setSelected(!btn_focus.isSelected());
        } else {
            btn_focus.setSelected(true);
            btn_discount_unfocus.setSelected(false);
        }
        this.btn_price_unfocus = btn_focus;


        searchParams.put("value_type", "price");
        searchParams.put("offer_value", btn_focus.getText().toString());

    }


    private void initParams(View view) {

        mContext = view.getContext();

        searchParams = new HashMap<>();

    }

    private void initCategoryRV(View view) {
        rectCategoryList = searchFilterCategory.findViewById(R.id.rectCategoryList);
        rectCategoryList.loadData(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

    }

    private void handleClickEventListener() {
        filterStoresBtn.setOnClickListener(this);
        filterEventsBtn.setOnClickListener(this);
        filterOffersBtn.setOnClickListener(this);
        price_offer_btn.setOnClickListener(this);
        discount_offer_btn.setOnClickListener(this);
        btnSearchLayout.setOnClickListener(this);
        orderByDate.setOnClickListener(this);
        orderByGeo.setOnClickListener(this);
        dateBeginTxt.setOnClickListener(this);
        locationLbl.setOnClickListener(this);


        searchParams.put("order_by", Constances.OrderByFilter.NEARBY);


        //make geo location  section selected as a default
        orderByGeo.setSelected(true);

    }

    private void setDefaultModuleSelected(View view, String module_name) {
        if (module_name.equals("offer")) {
            makeOfferFocusable();
        } else if (module_name.equals("event")) {
            makeEventFocusable();
        } else {
            makeStoreFocusable();
        }
    }

    private void initRangeSeekBar() {


        if (mOldDistance == -1) {
            int radius = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("distance_value", 100);
            mOldDistance = radius;
        }

        String val = String.valueOf(mOldDistance);
        if (mOldDistance == 100) {
            val = "+" + mOldDistance;
        }

        String msg = String.format(getContext().getString(R.string.settings_notification_distance_dis), val);
        range_seek_bar_text.setText(msg);
        rangeSeekBar.setProgress(mOldDistance);
        range_seek_bar_text.setText(mOldValue);

        rangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String val = String.valueOf(progress);
                if (progress == 100) {
                    val = "+" + progress;
                }

                String msg = String.format(getContext().getString(R.string.settings_notification_distance_dis), val);
                range_seek_bar_text.setText(msg);
                mOldDistance = progress;

                REQUEST_RANGE_RADIUS = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void dialogDatePickerLight(final TextInputEditText pickDateTxt) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        pickDateTxt.setText(Tools.getFormattedDateAPI(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.filterStoresBtn) {

            makeStoreFocusable();

        } else if (v.getId() == R.id.filterOffersBtn) {

            makeOfferFocusable();

        } else if (v.getId() == R.id.filterEventsBtn) {

            makeEventFocusable();

        } else if (v.getId() == R.id.price_offer_btn) {
            price_offer_btn.setSelected(true);
            discount_offer_btn.setSelected(false);
            btnsOffersDiscountProps.setVisibility(View.GONE);
            btnsOffersPriceFormat.setVisibility(View.VISIBLE);
            searchParams.put("price_offer_btn", price_offer_btn.isSelected());
            searchParams.remove("discount_offer_btn");

        } else if (v.getId() == R.id.discount_offer_btn) {
            price_offer_btn.setSelected(false);
            discount_offer_btn.setSelected(true);
            btnsOffersDiscountProps.setVisibility(View.VISIBLE);
            btnsOffersPriceFormat.setVisibility(View.GONE);

            searchParams.put("discount_offer_btn", discount_offer_btn.isSelected());
            searchParams.remove("price_offer_btn");

        } else if (v.getId() == R.id.btnSearchLayout) {
            handleSearchLayoutClick();
        } else if (v.getId() == R.id.date_begin_txt) {
            dialogDatePickerLight(dateBeginTxt);
        } else if (v.getId() == R.id.orderByGeo) {
            orderByDate.setSelected(false);
            orderByGeo.setSelected(true);
            layoutLocationChanger.setVisibility(View.VISIBLE);
            searchParams.put("order_by", Constances.OrderByFilter.NEARBY);
        } else if (v.getId() == R.id.orderByDate) {
            orderByGeo.setSelected(false);
            orderByDate.setSelected(true);
            layoutLocationChanger.setVisibility(View.GONE);
            searchParams.put("order_by", Constances.OrderByFilter.RECENT);
        } else if (v.getId() == R.id.locationLbl) {
            getLocationGP();
        }
    }

    private void makeStoreFocusable() {
        filterStoresBtn.setSelected(true);
        filterOffersBtn.setSelected(false);
        filterEventsBtn.setSelected(false);

        searchEvents.setVisibility(View.GONE);
        searchStores.setVisibility(View.VISIBLE);
        searchOffers.setVisibility(View.GONE);

        searchParams.put("module", "store");
    }


    private void makeOfferFocusable() {

        searchParams.put("module", "offer");


        filterStoresBtn.setSelected(false);
        filterOffersBtn.setSelected(true);
        filterEventsBtn.setSelected(false);
        price_offer_btn.setSelected(true);

        searchEvents.setVisibility(View.GONE);
        searchStores.setVisibility(View.GONE);
        searchOffers.setVisibility(View.VISIBLE);

        btnsOffersPrice.setVisibility(View.VISIBLE);
        btnsOffersPriceFormat.setVisibility(View.VISIBLE);


        offersDiscountBtnClick();
        offersPriceBtnClick();
    }

    private void makeEventFocusable() {
        filterStoresBtn.setSelected(false);
        filterOffersBtn.setSelected(false);
        filterEventsBtn.setSelected(true);

        searchEvents.setVisibility(View.VISIBLE);
        searchStores.setVisibility(View.GONE);
        searchOffers.setVisibility(View.GONE);

        searchParams.put("module", "event");
    }


    private void handleSearchLayoutClick() {


        String searchFieldTxt = searchField.getText().toString().trim();
        searchParams.put("search", searchFieldTxt);
        searchParams.put("category", CategoryCustomView.itemCategoryselectedId);
        searchParams.put("category_selected_index", CategoryCustomView.itemCategoryselectedIndex);

        if (REQUEST_RANGE_RADIUS > -1) {
            if (REQUEST_RANGE_RADIUS <= 99)
                searchParams.put("radius", String.valueOf((REQUEST_RANGE_RADIUS * 1000)));
        }

        searchParams.put("openingStatus", openStatusCB.isChecked() ? 1 : 0);
        searchParams.put("date_begin", dateBeginTxt.getText().toString().trim());


        if (previousPageClass != null) {
            Intent intent = new Intent();
            previousPageClass = null;
            intent.putExtra("searchParams", searchParams);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
            return;
        } else {

            //save view state
            saveCurrentSearchFields(searchParams);

            if (getArguments() != null && getArguments().containsKey("useCacheFields") && getArguments().getString("useCacheFields").equals("enabled")) {
                Intent intent = new Intent();
                intent.putExtra("searchParams", searchParams);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();

            } else {

                Intent intent = new Intent(mContext, CustomSearchActivity.ResultFilterActivity.class);
                intent.putExtra("searchParams", searchParams);
                getActivity().startActivity(intent);
                getActivity().finish();
            }


        }


    }


    private void saveCurrentSearchFields(final HashMap<String, Object> searchObj) {
        final SharedPreferences sharedPref = getInstance()
                .getSharedPreferences("searchObjPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("searchParams", gson.toJson(searchObj));
        editor.commit();
    }

    private HashMap<String, Object> getSavedSearchFields() {
        SharedPreferences saveSO = getInstance().getSharedPreferences("searchObjPref", Context.MODE_PRIVATE);

        if (saveSO != null) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(saveSO.getString("searchParams", null), type);
        } else {
            return null;
        }
    }

    private void importDataFromPrefObj(final HashMap<String, Object> cacheSearchObj) {

        if (cacheSearchObj == null) {
            Toast.makeText(getActivity(), "Cache data isn't available right now", Toast.LENGTH_LONG).show();
            return;
        }

        if (AppConfig.APP_DEBUG)
            Log.e("cacheSearchObj", cacheSearchObj.toString());

        if (cacheSearchObj.containsKey("module")) {
            setDefaultModuleSelected(getView(), (String) cacheSearchObj.get("module"));
        }

        if (cacheSearchObj.containsKey("search")) {
            searchField.setText((String) cacheSearchObj.get("search"));
        }

        if (cacheSearchObj.containsKey("category")) {
            CategoryCustomView.itemCategoryselectedId = (int) cacheSearchObj.get("category");
        }

        if (cacheSearchObj.containsKey("category_selected_index")) {
            rectCategoryList.focusOnViewAfterAction((int) cacheSearchObj.get("category_selected_index"));
        }

        if (cacheSearchObj.containsKey("radius")) {

            int radius = Integer.parseInt((String) cacheSearchObj.get("radius")) / 1000;
            rangeSeekBar.setProgress(radius);
            @SuppressLint("StringFormatMatches") String msg = String.format(getContext().getString(R.string.settings_notification_distance_dis), radius);
            range_seek_bar_text.setText(msg);
        }


        if (cacheSearchObj.containsKey("date_begin")) {
            dateBeginTxt.setText((String) cacheSearchObj.get("date_begin"));
        }


        if (cacheSearchObj.containsKey("openingStatus")) {
            openStatusCB.setChecked(((int) cacheSearchObj.get("openingStatus")) == 1);
        }


        if (cacheSearchObj.containsKey("order_by")) {
            if (cacheSearchObj.get("order_by").equals(Constances.OrderByFilter.NEARBY)) {
                orderByDate.setSelected(false);
                orderByGeo.setSelected(true);
                layoutLocationChanger.setVisibility(View.VISIBLE);
            } else if (cacheSearchObj.get("order_by").equals(Constances.OrderByFilter.RECENT)) {
                orderByGeo.setSelected(false);
                orderByDate.setSelected(true);
                layoutLocationChanger.setVisibility(View.GONE);
            }
        }


        if (cacheSearchObj.containsKey("latitude") && cacheSearchObj.containsKey("longitude")) {
            searchParams.put("latitude", cacheSearchObj.get("latitude"));
            searchParams.put("longitude", cacheSearchObj.get("longitude"));
        }

        if (cacheSearchObj.containsKey("city")) {
            searchParams.put("city", cacheSearchObj.get("city"));
            putDrawableText((String) cacheSearchObj.get("city"), CommunityMaterial.Icon.cmd_crosshairs_gps);
        }

        if (cacheSearchObj.containsKey("discount_offer_btn")) {
            discount_offer_btn.performClick();
        }

        if (cacheSearchObj.containsKey("discount_selected_value")) {
            Objects.requireNonNull(getView()).findViewWithTag(cacheSearchObj.get("discount_selected_value")).performClick();
        }

        if (cacheSearchObj.containsKey("offer_selected_value")) {
            Objects.requireNonNull(getView()).findViewWithTag(cacheSearchObj.get("offer_selected_value")).performClick();
        }

        if (cacheSearchObj.containsKey("price_offer_btn")) {
            price_offer_btn.performClick();
        }


    }

}
