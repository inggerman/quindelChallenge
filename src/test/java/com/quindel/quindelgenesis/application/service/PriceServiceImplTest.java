package com.quindel.quindelgenesis.application.service;

import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;
import com.quindel.quindelgenesis.domain.repository.PriceRepository;
import com.quindel.quindelgenesis.domain.service.PriceService;
import com.quindel.quindelgenesis.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.quindel.quindelgenesis.utils.TestUtils.formatter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceImplTest {

    private PriceService priceService;

    private PriceRepository priceRepository;

    @BeforeEach
    void setUp_BeforeEach() {
        priceRepository = mock(PriceRepository.class);

        priceService = new PriceServiceImpl(priceRepository);
    }

    @Test
    void shouldReturnEmptyWhenPriceNotAvailableForDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        when(priceRepository.findPricesByCriteria(dateTime,dateTime, 35455, 1L, 100))
                .thenReturn(Optional.empty());

        List<PriceResponseDTO> response = priceService.
                retrievePrices(TestUtils.buildPriceRequestDTO(dateTime
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        assertTrue(response.isEmpty(),
                "Expected an empty list when no price data is available");
    }

    @Test
    void shouldReturnPriceListForGivenProductAndDate() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 15, 0, 0, 0);

        when(priceRepository.findPricesByCriteria(
                dateTime,dateTime, 35455, 2L, 100))
                .thenReturn(Optional.of(TestUtils.buildPriceList()));

        final List<PriceResponseDTO> productPrice = priceService.retrievePrices(TestUtils.buildPriceRequestDTO(dateTime.format(formatter)));

        assertNotNull(productPrice);
        assertFalse(productPrice.isEmpty());
    }

}