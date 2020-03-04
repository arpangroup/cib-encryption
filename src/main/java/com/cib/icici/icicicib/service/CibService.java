package com.cib.icici.icicicib.service;

import com.cib.icici.icicicib.Base64Util;
import com.cib.icici.icicicib.Encryption;
import com.cib.icici.icicicib.Util;
import com.cib.icici.icicicib.dto.AccountStatementEncryptResponse;
import com.cib.icici.icicicib.properties.CibProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CibService {

    @Autowired
    CibProperties cibProperties;



    protected HttpHeaders getIciciRequestHeader(){
        HttpHeaders headers = new HttpHeaders();
        //headers.set("x-forwarded-for", IP_ADDR);
        headers.set("host", "apigwuat.icicibank.com:8443");
        headers.set("content-length", "684");
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "text/plain");
        headers.add("apikey", cibProperties.getApikey());

        return headers;
    }
    protected String getEncryptedRequest(String requestData) throws Exception{
        String encryptedRequest = Encryption.encrypt(requestData);
        System.out.println("\n---------------EncryptedText-----------------");
        System.out.println(encryptedRequest);
        System.out.println("---------------------------------------------------------");
        return encryptedRequest;
    }


    public String registration() throws Exception {
        String requestData = Util.getRegistrationRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrlregistration(), entity, String.class);


        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }

    public String transaction() throws Exception {
        String requestData = Util.getTransactionRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrltransaction(), entity, String.class);


        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }


    public String transactionInquiry() throws Exception {
        String requestData = Util.getTransactionInquiryRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrltransactioninquery(), entity, String.class);


        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }


    public String balance() throws Exception {
        String requestData = Util.getBalanceRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrlbalanceinquery(), entity, String.class);


        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }


    public String accountStatement() throws Exception {
        String requestData = Util.getAccountStatementRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrlaccountstatement(), entity, String.class);
        System.out.println("\n========================ENCRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(encryptedResponse);
        System.out.println("==================================================================================");


        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }


    public String accountStatementNew() throws Exception {
        String requestData = Util.getAccountStatementRequestData();
        String encryptedRequest =getEncryptedRequest(requestData);
        HttpHeaders headers = getIciciRequestHeader();
        HttpEntity<String> entity = new HttpEntity<String>(encryptedRequest, headers);


        RestTemplate restTemplate = new RestTemplate();
        String encryptedResponse  = restTemplate.postForObject(cibProperties.getUrlaccountstatement(), entity, String.class);
        System.out.println("\n========================ENCRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(encryptedResponse);
        System.out.println("==================================================================================");



        System.out.println("\n========================Step1. DECRYPTING_ENCRYPTED_KEY===============================");
        Gson gson = new Gson();
        AccountStatementEncryptResponse responseDto = gson.fromJson(encryptedResponse, AccountStatementEncryptResponse.class);
        System.out.println("responseDto: "+responseDto);
        System.out.println("-------------------------------------");
        String decryptKey = Encryption.decrypt(responseDto.getEncryptedKey());
        System.out.println("------------DecryptKey-------------------------");
        System.out.println(decryptKey);
        System.out.println("========================================================================================");




        System.out.println("\n========================Step2. Base64_DECRYPTING_ENCRYPTED_DATA===============================");
        System.out.println("------------DecryptData-------------------------");
        byte[] bytes = Base64.getDecoder().decode(responseDto.getEncryptedData().getBytes(StandardCharsets.UTF_8));
        String decodedData = new String(bytes);
        System.out.println(decodedData);
        System.out.println("========================================================================================");



        String decryptText = Encryption.decrypt(encryptedResponse);
        System.out.println("\n========================DECRYPTED_DATA_FROM_ICICI===============================");
        System.out.println(decryptText);
        System.out.println("==================================================================================");

        return decryptText;
    }


}
