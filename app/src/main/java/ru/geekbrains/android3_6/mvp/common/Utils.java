package ru.geekbrains.android3_6.mvp.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

public class Utils {

    public static String SHA1(String s) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            Timber.e(e);
        }
        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
