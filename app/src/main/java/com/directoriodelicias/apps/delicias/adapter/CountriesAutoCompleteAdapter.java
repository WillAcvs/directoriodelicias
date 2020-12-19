package com.directoriodelicias.apps.delicias.adapter;


import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.fragment.app.FragmentActivity;

import com.directoriodelicias.apps.delicias.classes.CountriesModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Directorio on 8/14/2016.
 */

public class CountriesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    //private static final String PLACES_API_BASE = "http://bestplaces.Directorio.com/1.0/webservice/getGooglePlaces";

    public ArrayList<String> resultList;


    private List<String> codeCountries;

    public CountriesAutoCompleteAdapter(FragmentActivity context, int textViewResourceId) {
        super(context, textViewResourceId);
        resultList = new ArrayList<>();
        codeCountries = new ArrayList<>();
    }

    public List<String> getCodeCountries() {
        return codeCountries;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {

        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {


        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.

                    resultList = getList(constraint.toString());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, final FilterResults results) {

                results.values = resultList;
                results.count = resultList.size();

                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    private ArrayList<String> getList(String string) {

        ArrayList<String> list = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();

        RealmResults<CountriesModel> result = realm.where(CountriesModel.class)
                .contains("name", string, Case.INSENSITIVE)
                .or()
                .contains("name", string, Case.SENSITIVE)
                .findAllSorted("name", Sort.ASCENDING);


        codeCountries.clear();
        for (CountriesModel country : result) {
            codeCountries.add(country.getDial_code());
            list.add(country.getName() + " ( " + country.getDial_code() + " )");
        }

        realm.close();

        resultList = list;
        return list;
    }


}