package com.directoriodelicias.apps.delicias.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.directoriodelicias.apps.delicias.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Directorio on 1/24/2017.
 */

public class MapsUtils {


    private static Map<String, Marker> markers = new HashMap<>();
    private static Map<String, MarkerOptions> markersOption = new HashMap<>();

    public static void addMarker(String id, Marker marker) {
        if (markers.containsKey(id)) {
            return;
        }
        markers.put(id, marker);
    }

    public static MarkerOptions generateMarker(FragmentActivity context, String id, LatLng pos, Bitmap bitmap, String promo) {

        IconGenerator iconGenerator = new IconGenerator(context);
        iconGenerator.setColor(ResourcesCompat.getColor(context.getResources(), R.color.colorPrimary, null));

        MarkerOptions markerOptions;
        if (markersOption.containsKey(id) && id != null) {

            markerOptions = markersOption.get(id);

        } else {

            View multiProfile = context.getLayoutInflater().inflate(R.layout.view_image_marker_map, null);

            iconGenerator.setContentView(multiProfile);

            ImageView image = multiProfile.findViewById(R.id.image);
            TextView promoView = multiProfile.findViewById(R.id.promo);
            promoView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));

            if (bitmap != null)
                image.setImageBitmap(bitmap);

            if (promo == null)
                promoView.setVisibility(View.GONE);
            else {
                promoView.setText(context.getString(R.string.promo));
                promoView.setVisibility(View.VISIBLE);
            }


            markerOptions = new MarkerOptions().
                    icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(""))).
                    position(pos).
                    anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());

            markersOption.put(id, markerOptions);

        }

        return markerOptions;
    }


    //    public static void animateMarker(LatLng pos, final Marker marker) {
//
//        final Location destination = new Location(LocationManager.GPS_PROVIDER);
//        destination.setLatitude(pos.latitude);
//        destination.setLongitude(pos.longitude);
//        if (marker != null) {
//            final LatLng startPosition = marker.getPosition();
//            final LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());
//
//            final float startRotation = marker.getRotation();
//
//            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
//            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//            valueAnimator.setDuration(1000); // duration 1 second
//            valueAnimator.setInterpolator(new LinearInterpolator());
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    try {
//                        float v = animation.getAnimatedFraction();
//                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
//                        marker.setPosition(newPosition);
//                        marker.setRotation(computeRotation(v, startRotation, destination.getBearing()));
//                    } catch (Exception ex) {
//                        // I don't care atm..
//                    }
//                }
//            });
//
//            valueAnimator.start();
//        }
//    }

}
