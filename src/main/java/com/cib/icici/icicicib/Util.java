package com.cib.icici.icicicib;

import com.cib.icici.icicicib.properties.CibProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

//    @Autowired
//    static CibProperties cibProperties;

//    private static String CERTIFICATE_PATH = "ICICI_PUBLIC_CERTIFICATE_UAT.txt";
//    private static String PRIVATE_KEY_PATH = "PRIVATE_KEY.txt";
//    //private static String PRIVATE_KEY_PATH = "apache-selfsigned.key";
//    private static String REGISTRATION_REQUEST_PATH = "REGISTRATION_REQUEST.txt";


//    private static String CERTIFICATE_PATH = fileStorageService.getPublicKeyPath().toString();
//    private static String PRIVATE_KEY_PATH = fileStorageService.getPrivateKeyPath().toString();
//    private static String REGISTRATION_REQUEST_PATH = fileStorageService.getRegistrationRequestPath().toString();


    public static String getICICIPublicCertifificate() throws IOException {
        String fileName = "./icici/ICICI_PUBLIC_CERTIFICATE_UAT.txt";
//        String fileName = new CibProperties().getPublickey();
        Path filePath = Paths.get(fileName);

        String result = "";
        Path path = filePath;
        //System.out.println("\n=======########=======ICICI_PUBLIC_CERTIFICATE=========########========");
        String fileContent = new String(Files.readAllBytes(path));
        result = fileContent;
        //System.out.println(result);
        //System.out.println("=========########========================================########========");
        return result;
    }


    public static String getICICIPrivateKey() throws IOException {
        String fileName = "./icici/PRIVATE_KEY.txt";
//        String fileName = new CibProperties().getPrivatekey();
        Path filePath = Paths.get(fileName);

        String result = "";
        Path path = filePath;
        //System.out.println("\n=======########=======ICICI_PUBLIC_CERTIFICATE=========########========");
        String fileContent = new String(Files.readAllBytes(path));
        result = fileContent;
        //System.out.println(result);
        //System.out.println("=========########========================================########========");
        return result;
    }


    public static String getRequestData() throws IOException {
        String fileName = "./icici/REGISTRATION_REQUEST.txt";
//        String fileName = new CibProperties().getRequest();
        Path filePath = Paths.get(fileName);
//
//
//
        String result = "";
        Path path = filePath;
        String fileContent = new String(Files.readAllBytes(path));
        result = fileContent;
        System.out.println(result);
        System.out.println("=========########========================================########========");
        return result;
    }
}
