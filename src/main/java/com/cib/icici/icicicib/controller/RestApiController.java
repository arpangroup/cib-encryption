package com.cib.icici.icicicib.controller;

import com.cib.icici.icicicib.Encryption;
import com.cib.icici.icicicib.RegistrationRequest;
import com.cib.icici.icicicib.Util;
import com.cib.icici.icicicib.properties.CibProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

@RestController
public class RestApiController {

    @Autowired
    CibProperties cibProperties;

    public String registerNew() throws Exception {

        //String URL = "https://apigwuat.icicibank.com:8443/api/Corporate/CIB/v1/Registration";
        //String API_KEY = "685d26c8-a367-4733-9aae-cfa9dbc6252b";
        //String IP_ADDR = "159.89.170.197"; //68.183.92.75

        /*================HEADERS==============================*/
        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-forwarded-for", IP_ADDR);
        headers.set("host", "apigwuat.icicibank.com:8443");
        headers.set("content-length", "684");
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "text/plain");
        headers.add("apikey", cibProperties.getApikey());

        /*================EncryptedBody==============================*/
        String requestData = new Util().getRequestData();

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
        String result = restTemplate.postForObject(cibProperties.getUrlregistration(), entity, String.class);
        System.out.println(result);

        return result;
    }



    @GetMapping("/register")
    public String registerApi() throws Exception{
        String encryptedResponse = registerNew();
        System.out.println("==================================================================================");

        String decryptText = Encryption.decrypt(encryptedResponse);
//        String decryptText = RsaUtil.decryptData(encryptedResponse.getBytes());
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");
        return decryptText;
    }
}
