package com.cib.icici.icicicib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IciciCibApplication {
    /*========================================================
    {
        Base64Encode(RSA_Encrypt( { "CORPID" : "CIBNEXT", "USERID" : "ERPUSER", "REQUESTTYPE" : "AGREG", …,… }))
    }
    ========================================================
    */


    public static void register() throws Exception{
        String URL       = "https://apigwuat.icicibank.com:8443/api/Corporate/CIB/v1/Registration";
        String API_KEY   = "685d26c8-a367-4733-9aae-cfa9dbc6252b";
        String IP_ADDR   = "159.89.170.197"; //68.183.92.75

        /*================HEADERS==============================*/
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-forwarded-for", IP_ADDR);
        headers.set("host", "apigwuat.icicibank.com:8443");
        headers.set("apikey", API_KEY);
        headers.set("content-length", "684");
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "text/plain");

        /*================EncryptedBody==============================*/
        RegistrationRequest registrationRequest = new RegistrationRequest();
        String requestData = registrationRequest.toString();
        System.out.println("\n---------------PlainRequest(UnEncrypted)-----------------");
        System.out.println(requestData);
        System.out.println("---------------------------------------------------------");
        String encryptedRequest = Encryption.encrypt(requestData);
        System.out.println("\n---------------EncryptedText)-----------------");
        System.out.println(encryptedRequest);
        System.out.println("---------------------------------------------------------");


        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.postForObject( URL, encryptedRequest, Object.class);
        System.out.println(result);
    }


    public static void main(String[] args) throws Exception{
        SpringApplication.run(IciciCibApplication.class, args);
        register();
    }

}
