package com.cib.icici.icicicib.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
@ApiModel
public class ImpsP2aRequestDto {
    @ApiModelProperty(required = true, hidden = false, value = "111234567890", example = "111234567890" )
    private String BeneAccNo = "111234567890";

    @ApiModelProperty(required = true, hidden = false, value = "DLXB0000092", example = "DLXB0000092" )
    private String BeneIFSC;

    @ApiModelProperty(required = true, hidden = false, value = "1", example = "1" )
    private String Amount;

    @ApiModelProperty(required = true, hidden = false, value = "123132128834556", example = "123132128834556" )
    private String TranRefNo;

    @ApiModelProperty(required = true, hidden = false, value = "FTTransferP2P", example = "FTTransferP2P" )
    private String PaymentRef;

    @ApiModelProperty(required = true, hidden = false, value = "RenName", example = "RenName" )
    private String RemName;

    @ApiModelProperty(required = true, hidden = false, value = "9860001122", example = "9860001122" )
    private String RemMobile;

    @ApiModelProperty(required = true, hidden = false, value = "rcode", example = "rcode" )
    private String RetailerCode;

    @ApiModelProperty(required = true, hidden = false, value = "a9a42043d31945c0bbb87f6fc5d0b31e", example = "a9a42043d31945c0bbb87f6fc5d0b31e" )
    private String PassCode;

    @ApiModelProperty(required = true, hidden = false, value = "20150707171319", example = "20150707171319" )
    private String TransactionDate;
}
