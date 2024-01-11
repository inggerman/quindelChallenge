package com.quindel.quindelgenesis.infrastructure.controller;

import com.quindel.quindelgenesis.domain.exception.NotFoundException;
import com.quindel.quindelgenesis.domain.service.PriceService;
import com.quindel.quindelgenesis.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;


    @Test
    void shouldProvidePriceDetailsWhenRequested() throws Exception {
        String errorCode = "DATA_NOT_FOUND";
        String errorMessage = "No data found for the given criteria";
        when(priceService.retrievePrices(any()))
                .thenThrow(new NotFoundException(errorCode, errorMessage));

        // Realizar la solicitud y verificar la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/price")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("productId", "99999")
                        .param("brandId", "data.sql")
                        .param("applyDate", "2024-01-10T10:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorId").isNotEmpty())
                .andExpect(jsonPath("$.errorCode").value(errorCode))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    void shouldReturnNotFoundResponseWhenPriceDataIsUnavailable() throws Exception {
        var requestService = TestUtils.buildPriceRequestDTO("2020-06-14T00:00:00");
        var responseService = TestUtils.buildPriceResponseDTOList();

        when(priceService.retrievePrices(any()))
                .thenReturn(responseService);

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/price")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("productId", String.valueOf(requestService.getProductId()))
                .param("brandId", String.valueOf(requestService.getBrandId()))
                .param("applyDate", requestService.getApplyDate().toString()));

        perform.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].price").value(responseService.get(0).getPrice()))
                .andExpect(jsonPath("$[0].priceList").value(responseService.get(0).getPriceList()))
                .andExpect(jsonPath("$[0].brandId").value(responseService.get(0).getBrandId()))
                .andExpect(jsonPath("$[0].startDate").exists());
    }

}