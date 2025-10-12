package com.E_Commerce.E_Commerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IdGeneratorType;

@Getter
@Setter
public class AddressDto {
    private Long id;
    private String street;
    private String city ;
    private String  state;
    private String postalCode;
    private String country;
}
