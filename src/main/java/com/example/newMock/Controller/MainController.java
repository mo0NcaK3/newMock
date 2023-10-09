package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController


public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE

    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientID = requestDTO.getClientId();
            char firstDigit = clientID.charAt(0);
            String currency;
            BigDecimal maxLimit;
            Random random = new Random();
            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                currency = "RU";
            }
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientID);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(new BigDecimal(random.nextDouble() * maxLimit.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
            responseDTO.setMaxLimit(maxLimit);

            log.error("*********** LimitRequestDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*********** LimitResponseDTO ***********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }
}
