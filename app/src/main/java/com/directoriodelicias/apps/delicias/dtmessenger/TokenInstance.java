package com.directoriodelicias.apps.delicias.dtmessenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Directorio on 7/21/2016.
 */

public class TokenInstance {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String tokenID = "";
    public static String senderID = "";

    public static String getTokenID(Context context) {

        String mac = ServiceHandler.getMacAddr();
        String macHashed = doHash(doHash(mac) + DCMessengerConfig.APP_ID);

        return macHashed;
    }

    public static String getSenderID() {

        return (getTokenID(AppController.getInstance()) + "@" + DCMessengerConfig.APP_ID).toLowerCase();
    }

    public static String getToken(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = null;

        try {
            token = sharedPreferences.getString("token00", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (token == null) {
            token = getTokenID(context);
        }
        return token;
    }

    static String doHash(String toHash) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toHash.getBytes(StandardCharsets.UTF_8);
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            // This is ~55x faster than looping and String.formating()
            hash = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
