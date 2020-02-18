package com.cib.icici.icicicib;

import java.util.Base64;

public class Base64Util {
    public static String encode(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(str.getBytes());
        return new String(encoded);
    }

    public static byte[] encodeFromByte(byte[] str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(str);
        return encoded;
    }

    public static String decode(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(str);
        return new String(decoded);
    }

    public static byte[] decodeToByte(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(str);
        return decoded;
    }
}
