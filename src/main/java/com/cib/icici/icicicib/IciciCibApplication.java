package com.cib.icici.icicicib;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

@SpringBootApplication
public class IciciCibApplication {
    /*========================================================
    {
        Base64Encode(RSA_Encrypt( { "CORPID" : "CIBNEXT", "USERID" : "ERPUSER", "REQUESTTYPE" : "AGREG", …,… }))
    }
    ========================================================
    */


    public static String register() throws Exception{
        String URL       = "https://apigwuat.icicibank.com:8443/api/Corporate/CIB/v1/Registration";
        String API_KEY   = "685d26c8-a367-4733-9aae-cfa9dbc6252b";
        String IP_ADDR   = "159.89.170.197"; //68.183.92.75

        /*================HEADERS==============================*/
        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-forwarded-for", IP_ADDR);
        headers.set("host", "apigwuat.icicibank.com:8443");
        //headers.set("apikey", "685d26c8-a367-4733-9aae-cfa9dbc6252b");
        headers.set("content-length", "684");
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "text/plain");
        headers.add("apikey", "685d26c8-a367-4733-9aae-cfa9dbc6252b" );

        /*================EncryptedBody==============================*/
        RegistrationRequest registrationRequest = new RegistrationRequest();
        String requestData = registrationRequest.toString();
        System.out.println("\n---------------PlainRequest(UnEncrypted)-----------------");
        System.out.println(requestData);
        System.out.println("---------------------------------------------------------");
        String encryptedRequest = Encryption.encrypt(requestData);
//        String encryptedRequest = new String(RsaUtil.encryptData(requestData), StandardCharsets.UTF_8);
        System.out.println("\n---------------EncryptedText-----------------");
        System.out.println(encryptedRequest);
        System.out.println("---------------------------------------------------------");

        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject( URL, entity, String.class);
        System.out.println(result);

        return result;
    }



    public static String registerNew() throws Exception{
        String URL       = "https://apigwuat.icicibank.com:8443/api/Corporate/CIB/v1/Registration";
        String API_KEY   = "685d26c8-a367-4733-9aae-cfa9dbc6252b";
        String IP_ADDR   = "159.89.170.197"; //68.183.92.75

        /*================HEADERS==============================*/
        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-forwarded-for", IP_ADDR);
        headers.set("host", "apigwuat.icicibank.com:8443");
        //headers.set("apikey", "685d26c8-a367-4733-9aae-cfa9dbc6252b");
        headers.set("content-length", "684");
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "text/plain");
        headers.add("apikey", "685d26c8-a367-4733-9aae-cfa9dbc6252b" );

        /*================EncryptedBody==============================*/
        String requestData = Util.getRequestData();

        System.out.println("\n---------------PlainRequest(UnEncrypted)-----------------");
        System.out.println(requestData);
        System.out.println("---------------------------------------------------------");
        String encryptedRequest = Encryption.encrypt(requestData);
//        String encryptedRequest = new String(RsaUtil.encryptData(requestData), StandardCharsets.UTF_8);
        System.out.println("\n---------------EncryptedText-----------------");
        System.out.println(encryptedRequest);
        System.out.println("---------------------------------------------------------");

        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject( URL, entity, String.class);
        System.out.println(result);

        return result;
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

    // Decrypt using RSA private key
    public static String decryptData(byte[] data) throws IOException {
        String PRIVATE_KEY_FILE = "apache-selfsigned.key";
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


    public static void main(String[] args) throws Exception{
        SpringApplication.run(IciciCibApplication.class, args);
        String encryptedResponse = registerNew();
        System.out.println("==================================================================================");

        String decryptText = Encryption.decrypt(encryptedResponse);
//        String decryptText = RsaUtil.decryptData(encryptedResponse.getBytes());
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");
    }

}
