package com.ni.tiste.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PhoneDTO {

    private String number;

    private String cityCode;

    private String countryCode;

    public PhoneDTO(String number, String cityCode, String countryCode) {
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }
}
