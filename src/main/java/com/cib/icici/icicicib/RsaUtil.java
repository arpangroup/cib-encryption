package com.cib.icici.icicicib;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RsaUtil {

    private static final String PUBLIC_KEY_FILE  = "public.key";
    private static final String PRIVATE_KEY_FILE = "private.key";


    private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        FileOutputStream fos   = null;
        ObjectOutputStream oos = null;

        try{
            System.out.println("Generating " + fileName + " ...");
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(mod);
            oos.writeObject(exp);
            System.out.println(fileName + " generated successfully");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(oos != null){
                oos.close();
                if(fos != null){
                    fos.close();
                }
            }
        }
    }
    private static PublicKey readPublicKeyFromFile(String fileName) throws IOException {
        FileInputStream fis    = null;
        ObjectInputStream ois  = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus  = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            //Get Private Key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(rsaPublicKeySpec);
            return publicKey;
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
            return null;
        }finally {
            if (ois != null) {
                ois.close();
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }
    private static PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {
        FileInputStream fis    = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus  = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            //Get Private Key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);
            return privateKey;
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
            return null;
        }finally {
            if(ois != null){
                ois.close();
                if(fis != null){
                    fis.close();
                }
            }
        }
    }

    // Get RSA keys. Uses key size of 2048.
    public static void generateRSAKeys() throws Exception {
        System.out.println("=========#########=========GENERATE PUBLIC AND PRIVATE KEY=========#########=========");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);// (1024, 2048, 4096)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("\n-------------------------PULLING OUT PARAMETERS WHICH MAKES KEYPAIR-------------------------\n");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec      = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateCrtKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

        System.out.println("\n-------------------------SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-------------------------\n");
        RsaUtil rsaUtil = new RsaUtil();
        rsaUtil.saveKeys(PUBLIC_KEY_FILE,  rsaPublicKeySpec.getModulus(),  rsaPublicKeySpec.getPublicExponent());
        rsaUtil.saveKeys(PRIVATE_KEY_FILE, rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());

    }

    // Get RSA keys. Uses key size of 4096.
    public static void generateRSAKeysOf4096Bits() throws Exception {
        System.out.println("=========#########=========GENERATE PUBLIC AND PRIVATE KEY=========#########=========");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);// (1024, 2048, 4096)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("\n-------------------------PULLING OUT PARAMETERS WHICH MAKES KEYPAIR-------------------------\n");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec      = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateCrtKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

        System.out.println("\n-------------------------SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-------------------------\n");
        RsaUtil rsaUtil = new RsaUtil();
        rsaUtil.saveKeys(PUBLIC_KEY_FILE,  rsaPublicKeySpec.getModulus(),  rsaPublicKeySpec.getPublicExponent());
        rsaUtil.saveKeys(PRIVATE_KEY_FILE, rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());

    }



    // Encrypt using RSA public key
    public static byte[] encryptData(String data) throws Exception {
        System.out.println("\n------------------ENCRYPTION STARTED------------------");
        System.out.println("Data Before Encryption : " + data);

        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try{
//            PublicKey publicKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
            PublicKey publicKey = Encryption.getRSAPublicKey();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
            System.out.println("Encrypted Data: " + new String(encryptedData));
        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        System.out.println("------------------ENCRYPTION COMPLETED------------------");
        return encryptedData;
    }

    // Decrypt using RSA private key
    public static String decryptData(byte[] data) throws IOException {
        System.out.println("\n------------------DECRYPTION STARTED------------------");
        byte[] decryptedData = null;
        try{
            PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);
            System.out.println("Decrypted Data: " + new String(decryptedData));
        }catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        System.out.println("------------------DECRYPTION COMPLETED------------------");
        return new String(decryptedData);
    }

}
