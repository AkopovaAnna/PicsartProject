package com.picsart.bookstorelibrary.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5PasswordConverter {

    private static final String ENCRYPTION = "MD5";

    public static String md5(String input) throws NoSuchAlgorithmException {
        if (null == input)
            return null;

        MessageDigest digest = MessageDigest.getInstance(ENCRYPTION);
        digest.update(input.getBytes(), 0, input.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

}

