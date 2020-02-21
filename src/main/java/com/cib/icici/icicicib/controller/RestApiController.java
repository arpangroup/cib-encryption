package com.cib.icici.icicicib.controller;

import com.cib.icici.icicicib.Encryption;
import com.cib.icici.icicicib.RegistrationRequest;
import com.cib.icici.icicicib.Util;
import com.cib.icici.icicicib.properties.CibProperties;
import com.cib.icici.icicicib.service.CibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    CibService cibService;


    //localhost:8082/register
    @GetMapping("/register")
    public ResponseEntity<Object>registerApi() throws Exception{
        return new ResponseEntity<>(cibService.registration(), HttpStatus.OK);
    }


    //localhost:8082/transaction
    @GetMapping("/transaction")
    public ResponseEntity<Object>transactionApi() throws Exception{
        return new ResponseEntity<>(cibService.transaction(), HttpStatus.OK);
    }


    //localhost:8082/transaction_inquiry
    @GetMapping("/transaction_inquiry")
    public ResponseEntity<Object>transactionInquiryApi() throws Exception{
        return new ResponseEntity<>(cibService.transactionInquiry(), HttpStatus.OK);
    }

    //localhost:8082/balance
    @GetMapping("/balance")
    public ResponseEntity<Object>balanceApi() throws Exception{
        return new ResponseEntity<>(cibService.balance(), HttpStatus.OK);
    }

    //localhost:8082/account_statement
    @GetMapping("/account_statement")
    public ResponseEntity<Object>accountStatementApi() throws Exception{
        return new ResponseEntity<>(cibService.accountStatement(), HttpStatus.OK);
    }




}
