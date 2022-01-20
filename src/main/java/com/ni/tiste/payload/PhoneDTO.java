package com.ni.tiste.payload;

import lombok.Data;

@Data
public class PhoneDTO {

    private String number;

    private String cityCode;

    private String countryCode;
}
