package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.DTO.VerificationDTO;
import com.sunpark.ticketflow.Entity.VerificationEntity;
import com.sunpark.ticketflow.Service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping("/request")
    public ResponseEntity<String> requestVerification(@RequestBody VerificationDTO verificationDTO){
        verificationService.createVerification(verificationDTO);
        return new ResponseEntity<>("Verification has been created", HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyVerification(@RequestBody VerificationDTO verificationDTO){
        verificationService.verify(
                verificationDTO.getUserId(),
                verificationDTO.getPhone(),
                verificationDTO.getCode()
        );
        return new ResponseEntity<>("Verification has been verified", HttpStatus.OK);
    }
}
