package com.cib.icici.icicicib;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class RegistrationRequest {
    private String AGGRID = "CUST0161";
    private String AGGRNAME = "HADRU";
    private String CORPID = "CIBNEXT";
    private String USERID = "CIBTESTING6";
    private String DEBITACC = "000405001257";
    private String CREDITACC = "000705001225";
    private String IFSC = "ICIC0000011";
    private String AMOUNT = "1";

//    private String UNIQUEREFERENCENumber = "12345";
//    private String ALIASId               = "";

}
