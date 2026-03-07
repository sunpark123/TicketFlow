package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.DTO.VerificationDTO;
import com.sunpark.ticketflow.Entity.VerificationEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;

    @Transactional
    public void createVerification(VerificationDTO verificationDTO){
        if(checkVerificationByUserId(verificationDTO.getUserId(), verificationDTO.getPhone())){
            throw new CustomException(ErrorCode.ALREADY_VERIFICATION);
        }

        VerificationEntity verification = verificationRepository
                .findByUserId(verificationDTO.getUserId())
                .orElseGet(() -> VerificationEntity.builder()
                        .userId(verificationDTO.getUserId())
                        .phone(verificationDTO.getPhone())
                        .build());

        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        verification.setCode(String.valueOf(code));
        verification.setVerify(false);

        verificationRepository.save(verification);
    }

    public boolean checkVerificationByUserId(String userId, String phone){
        return verificationRepository.existsByUserIdAndPhoneAndVerifyTrue(userId, phone);
    }

    public void verify(String userId, String Phone, String code){
        int verifyStatus = verificationRepository.checkVerification(userId, Phone, code);
        switch (verifyStatus){
            case 0:
                VerificationEntity verification = verificationRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.RETRY_VERIFICATION));

                verification.setVerify(true);
                verificationRepository.save(verification);
                break;
            case 1:
                throw new CustomException(ErrorCode.INCORRECT_CODE);
            case 2:
                throw new CustomException(ErrorCode.RETRY_VERIFICATION);
        }
    }
}
