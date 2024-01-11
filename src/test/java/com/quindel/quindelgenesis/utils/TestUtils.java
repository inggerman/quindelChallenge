package com.quindel.quindelgenesis.utils;


import com.quindel.quindelgenesis.application.dto.PriceRequestDTO;
import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;
import com.quindel.quindelgenesis.domain.model.Brand;
import com.quindel.quindelgenesis.domain.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class TestUtils {

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static List<Price> buildPriceList() {
        return Collections.singletonList(Price.builder()
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 29))
                .price(BigDecimal.valueOf(20.80))
                .priceList(1L)
                .id(1L)
                .curr("EUR")
                .brand(Brand.builder()
                        .id(1)
                        .store("ZARA")
                        .build())
                .build());
    }

    public static PriceRequestDTO buildPriceRequestDTO(String dateTime) {
        return  PriceRequestDTO.builder()
                .applyDate(LocalDateTime.parse(dateTime, formatter))
                .productId(35455)
                .brandId(1L)
                .build();
    }

    public static List<PriceResponseDTO> buildPriceResponseDTOList() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return Collections.singletonList(PriceResponseDTO.builder()
                .price(BigDecimal.valueOf(20.80))
                .priceList(1)
                .brandId(1L)
                .startDate(LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(),
                        localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()))
                .build());
    }
}