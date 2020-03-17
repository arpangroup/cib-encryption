package com.cib.icici.icicicib.controller;

import com.cib.icici.icicicib.dto.ImpsP2aRequestDto;
import com.cib.icici.icicicib.service.CibService;
import com.cib.icici.icicicib.service.ImpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/imps")
public class ImpsApiController {

    @Autowired
    ImpsService impsService;


    //localhost:8082/api/v1/imps/p2a
    @PostMapping("/p2a")
    public ResponseEntity<String>registerApi(@RequestBody ImpsP2aRequestDto impsP2aRequestDto) throws Exception{
        return new ResponseEntity<String>(impsService.processImpsP2aRequest(impsP2aRequestDto), HttpStatus.OK);
    }
}
