package com.cib.icici.icicicib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {
    private static String CERTIFICATE_PATH = "ICICI_PUBLIC_CERTIFICATE_UAT.txt";



    public static String getICICIPublicCertifificate() throws IOException {
        String result = "";
        Path path = Paths.get(CERTIFICATE_PATH);
        //System.out.println("\n=======########=======ICICI_PUBLIC_CERTIFICATE=========########========");
        String fileContent = new String(Files.readAllBytes(path));
        result = fileContent;
        //System.out.println(result);
        //System.out.println("=========########========================================########========");
        return result;
    }
}
