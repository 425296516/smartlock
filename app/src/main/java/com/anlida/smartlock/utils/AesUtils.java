package com.anlida.smartlock.utils;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jiang on 17/8/8.
 */

public class AesUtils {
    private static final String TAG = "AesUtils";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
    private static final String CHARSET_UTF_8 = "UTF-8";


    private static SecretKeySpec getSecretKeySpec(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(key.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            return new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (Exception e) {
            Log.e(TAG, "getSecretKeySpec: " + e.getMessage());
        }
        return null;

    }


    public static String encrypt(String content, String key) {
        try {
            byte[] encrypted = encrypt(content.getBytes(StandardCharsets.UTF_8), key);
            return new String(Base64.encode(encrypted, Base64.NO_WRAP), StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e(TAG, "encrypt: " + e.getMessage());
        }
        return null;
    }

    private static byte[] encrypt(byte[] content, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(content);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String key) {
        try {
            byte[] bytes = Base64.decode(content, Base64.DEFAULT);
            byte[] decrypted = decrypt(bytes, key);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e(TAG, "decrypt: " + e.getMessage());
        }
        return null;
    }

    private static byte[] decrypt(byte[] content, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(content);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

