package com.if42.tester.util;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    private final static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static String md5(String input) {

        String md5 = null;

        if(null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            logger.error("Error by hashing password in method generateToOutputStream()");
            e.printStackTrace();
        }
        return md5;
    }

    public static String generatePassword(){
        return RandomStringUtils.randomAlphanumeric(8);
    }

}
