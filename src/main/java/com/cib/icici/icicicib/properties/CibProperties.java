package com.cib.icici.icicicib.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties("cib")
@Data
@Validated
public class CibProperties {

    @NotBlank
    private String request;

    @NotBlank
    private String publickey;

    @NotBlank
    private String privatekey;

    @NotBlank
    private String apikey;





    @NotBlank
    private String urlregistration;


    @NotBlank
    private String urltransaction;

    @NotBlank
    private String urltransactioninquery;

    @NotBlank
    private String urlaccountstatement;

    @NotBlank
    private String urlbalanceinquery;

    @NotBlank
    private String urlregistrationstatus;

}
