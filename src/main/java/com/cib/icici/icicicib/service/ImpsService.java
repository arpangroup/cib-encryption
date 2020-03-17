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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImpsService {

    public String processImpsP2aRequest(ImpsP2aRequestDto impsP2aRequestDto){
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
}
