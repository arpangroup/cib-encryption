package com.cib.icici.icicicib;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Encryption {
    private static Cipher encCipher = null;
    private static Cipher decCipher = null;

    public static String encrypt(String input) throws Exception {
        Cipher cipher = getEncCipher();
        byte[] body = cipher.doFinal(input.toString().getBytes());
        return Base64.getEncoder().encodeToString(body);
    }

    public static String decrypt(String input) throws Exception {
        Cipher cipher = getDecCipher();
        input = input.replaceAll("\\r\\n", "");
        byte[] bytes = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
        String output = new String(cipher.doFinal(bytes));
        return output;
    }

    private static Cipher getEncCipher() throws CertificateException, IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {

        if (Encryption.encCipher != null) {
            return Encryption.encCipher;
        }

        RSAPublicKey pubkey = getRSAPublicKey();

        // Obtain a RSA Cipher Object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // The source of randomness
        SecureRandom secureRandom = new SecureRandom();

        // Initialize the cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

        Encryption.encCipher = cipher;

        return Encryption.encCipher;
    }

    private static Cipher getDecCipher() throws Exception {

        if (Encryption.decCipher != null) {
            return Encryption.decCipher;
        }

        // The source of randomness
        SecureRandom secureRandom = new SecureRandom();

        // Obtain a RSA Cipher Object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        String pvtKey = "Here You have to pass private Key content";

        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priv = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        // Initialize the cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
        Encryption.decCipher = cipher;

        return Encryption.decCipher;
    }

    private static RSAPublicKey getRSAPublicKey() throws CertificateException, IOException {
        String certData = Util.getICICIPublicCertifificate();

        InputStream inStream = new ByteArrayInputStream(certData.getBytes());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
        inStream.close();
        RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
        return pubkey;
    }

    public static String decryptByAES(String secretKey, String cipherText) throws Exception {
        int INIT_VECTOR_LENGTH = 16;

        // Get raw encoded data
        byte[] encrypted = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText);

        // Slice initialization vector
        IvParameterSpec ivParameterSpec = new IvParameterSpec(encrypted, 0, INIT_VECTOR_LENGTH);
        // Set secret password
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Trying to get decrypted text
        String decryptedData = new String(
                cipher.doFinal(encrypted, INIT_VECTOR_LENGTH, encrypted.length - INIT_VECTOR_LENGTH));

        return decryptedData;
    }
}
