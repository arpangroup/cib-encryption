package com.cib.icici.icicicib.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class AccountStatementEncryptResponse {
    private String requestId;
    private String service;
    private String encryptedKey;
    private String oaepHashingAlgorithm;
    private String iv;
    private String encryptedData;
    private String clientInfo;
    private String optionalParam;
}
