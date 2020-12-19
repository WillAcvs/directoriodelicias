package com.directoriodelicias.apps.delicias.parser.api_parser;


import com.directoriodelicias.apps.delicias.classes.Offer;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;


public class OfferParser extends Parser {

    public OfferParser(JSONObject json) {
        super(json);
    }

    public RealmList<Offer> getOffers() {

        RealmList<Offer> list = new RealmList<Offer>();

        try {

            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {

                try {
                    JSONObject json_user = json_array.getJSONObject(i + "");
                    Offer offer = new Offer();

                    offer.setId(json_user.getInt("id_offer"));
                    offer.setName(json_user.getString("name"));
                    offer.setDate_end(json_user.getString("date_end"));
                    offer.setDate_start(json_user.getString("date_start"));
                    offer.setStatus(json_user.getInt("status"));
                    offer.setStore_id(json_user.getInt("store_id"));
                    offer.setStore_name(json_user.getString("store_name"));
                    offer.setDistance(json_user.getDouble("distance"));
                    offer.setDescription(json_user.getString("description"));
                    offer.setValue_type(json_user.getString("value_type"));
                    offer.setDescription(json_user.getString("description"));
                    offer.setOffer_value((float) json_user.getDouble("offer_value"));

                    try {
                        offer.setLink(json_user.getString("link"));
                    } catch (Exception e) {
                    }

                    try {
                        offer.setFeatured(json_user.getInt("featured"));
                    } catch (Exception e) {
                    }


                    offer.setLat(json_user.getDouble("latitude"));
                    offer.setLng(json_user.getDouble("longitude"));


                    try {

                        OfferCurrencyParser mOfferCurrencyParser = new OfferCurrencyParser(new JSONObject(
                                json_user.getString("currency")
                        ));

                        offer.setCurrency(mOfferCurrencyParser.getCurrency());

                    } catch (Exception e) {
                    }

                    ImagesParser mImagesParser = new ImagesParser(
                            new JSONObject(json_user.getString("images"))
                    );


                    offer.setImages((mImagesParser.getImagesList() != null && mImagesParser.getImagesList().size() > 0) ? mImagesParser.getImagesList().get(0) : null);

                    list.add(offer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


}
