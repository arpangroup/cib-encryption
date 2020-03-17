package com.cib.icici.icicicib.service;

import com.cib.icici.icicicib.Encryption;
import com.cib.icici.icicicib.Util;
import com.cib.icici.icicicib.dto.AccountStatementEncryptResponse;
import com.cib.icici.icicicib.dto.ImpsP2aRequestDto;
import com.cib.icici.icicicib.properties.CibProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImpsService {

    public String processImpsP2aRequestOld(ImpsP2aRequestDto impsP2aRequestDto){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://203.199.32.92:7474/imps-web-bc/api/transaction/bc/IBCMon00046/p2a";
        String response = null;

        //HttpHeaders headers = new HttpHeaders();
        //headers.set("host", "apigwuat.icicibank.com:8443");
        //headers.set("content-length", "684");
        //headers.set("Accept", "*/*");
        //headers.set("Content-Type", "text/plain");
        //headers.add("apikey", cibProperties.getApikey());

        try {
            ResponseEntity<String> responseString = restTemplate.postForEntity(url, impsP2aRequestDto, String.class);
            response = responseString.getBody();

            System.out.println("=============================IMPS_RESPONSE=========================================");
            System.out.println(response);
            System.out.println("===================================================================================");
        }catch (Exception e){
            e.printStackTrace();
            response = "MicroService Error";
        }

        return response;
    }


    public String processImpsP2aRequest(ImpsP2aRequestDto impsP2aRequestDto){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://203.199.32.92:7474/imps-web-bc/api/transaction/bc/IBCMon00046/p2a";
        String response = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set("host", "apigwuat.icicibank.com:8443");
        //headers.set("content-length", "684");
        //headers.set("Accept", "*/*");
        //headers.set("Content-Type", "text/plain");
        //headers.add("apikey", cibProperties.getApikey());


        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("BeneAccNo"      , impsP2aRequestDto.getBeneAccNo());
        map.add("BeneIFSC"       , impsP2aRequestDto.getBeneIFSC());
        map.add("Amount"         , String.valueOf(impsP2aRequestDto.getAmount()));
        map.add("TranRefNo"      , impsP2aRequestDto.getTranRefNo());
        map.add("PaymentRef"     , impsP2aRequestDto.getPaymentRef());
        map.add("RemName"        , impsP2aRequestDto.getRemName());
        map.add("RemMobile"      , impsP2aRequestDto.getRemMobile());
        map.add("RetailerCode"   , impsP2aRequestDto.getRetailerCode());
        map.add("PassCode"       , impsP2aRequestDto.getPassCode());
        //map.add("TransactionDate", impsP2aRequestDto.getTransactionDate());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        try {
            ResponseEntity<String> responseString = restTemplate.postForEntity(url, request, String.class);
            response = responseString.getBody();

            System.out.println("=============================IMPS_RESPONSE=========================================");
            System.out.println(response);
            System.out.println("===================================================================================");
        }catch (Exception e){
            e.printStackTrace();
            response = "MicroService Error";
        }

        return response;
    }
}
