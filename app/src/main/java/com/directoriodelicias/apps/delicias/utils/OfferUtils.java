package com.directoriodelicias.apps.delicias.utils;

import com.directoriodelicias.apps.delicias.classes.Currency;

import java.text.DecimalFormat;

/**
 * Created by Droideve on 1/26/2018.
 */

public class OfferUtils {


    public static String parseCurrencyFormat(float price, Currency cData) {

        DecimalFormat decim = new DecimalFormat("0.00");

        String ps = decim.format(price);

        if (cData != null) {
            switch (cData.getFormat()) {
                case 1:
                    return cData.getSymbol() + ps;
                case 2:
                    return ps + cData.getSymbol();
                case 3:
                    return cData.getSymbol() + " " + ps;
                case 4:
                    return ps + " " + cData.getSymbol();
                case 5:
                    return String.valueOf(ps);
                case 6:
                    return cData.getSymbol() + ps + " " + cData.getCode();
                case 7:
                    return cData.getSymbol() + ps;
                case 8:
                    return ps + cData.getCode();
            }

        }

        return String.valueOf(price);


    }

    /*
        public static String parseCurrencyFormat(float price,String cData){
        //$formats = array("X0,000.00","0,000.00X","X 0,000.00","0,000.00 X","0,000.00","X0,000.00 XX","XX0,000.00","0,000.00XX");
        //emigrate to 1.1.6

        String defCurrency = "";
        Currency mCurrency=null;
        try {
            JSONObject currencyJson  = new JSONObject(cData);
            mCurrency = new Currency();
            mCurrency.code = currencyJson.getString("code");
            mCurrency.symbol = currencyJson.getString("symbol");
            mCurrency.name = currencyJson.getString("name");
            mCurrency.format = currencyJson.getInt("format");

        }catch (JSONException e) {
            defCurrency = cData;
            e.printStackTrace();
        }

        if(!defCurrency.equals(""))
            return cData+" "+price;

        DecimalFormat decim = new DecimalFormat("0.00");

        String ps = decim.format(price);

        if(mCurrency!=null)
            switch (mCurrency.format){
                case 1:
                    return mCurrency.symbol+ps;
                case 2:
                    return ps+mCurrency.symbol;
                case 3:
                    return mCurrency.symbol+" "+ps;
                case 4:
                    return ps+" "+mCurrency.symbol;
                case 5:
                    return String.valueOf(ps);
                case 6:
                    return mCurrency.symbol+ps+" "+mCurrency.code;
                case 7:
                    return mCurrency.code+ps;
                case 8:
                    return ps+mCurrency.code;
            }



        return String.valueOf(price);
    }
*/

}
