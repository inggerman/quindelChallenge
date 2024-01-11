package com.quindel.quindelgenesis;

import com.quindel.quindelgenesis.utils.TestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MainTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String  URL_GET_PRICE = "/price";



    static Stream<Arguments> priceDetailsForVariousDateTimes() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00", List.of(35.50), List.of(1), 1),
                Arguments.of("2020-06-14T16:00:00", List.of(35.50, 25.45), List.of(1, 2), 2),
                Arguments.of("2020-06-14T21:00:00", List.of(35.50), List.of(1), 1),
                Arguments.of("2020-06-15T10:00:00", List.of(35.50, 30.50), List.of(1, 3), 2),
                Arguments.of("2020-06-16T21:00:00", List.of(35.50, 38.95), List.of(1, 4), 2)

        );
    }


    @ParameterizedTest
    @MethodSource("priceDetailsForVariousDateTimes")
    void shouldReturnCorrectPriceDetailsForVariousDateTimes(
            String dateTime,
            List<Double> expectedPrices,
            List<Integer> expectedPriceLists,
            int expectedSize) throws Exception {

        var request = TestUtils.buildPriceRequestDTO(dateTime);

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get(URL_GET_PRICE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("productId", String.valueOf(request.getProductId()))
                .param("brandId", String.valueOf(request.getBrandId()))
                .param("applyDate", request.getApplyDate().toString()));


        perform.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$", hasSize(expectedSize)))
                .andDo(result -> IntStream.range(0, expectedSize).forEach(i ->
                        assertPriceDetails(result, i, expectedPrices.get(i), expectedPriceLists.get(i))));
    }

    private void assertPriceDetails(MvcResult result, int index, double price, int priceList) {
        try {
            MockMvcResultMatchers.jsonPath("$[" + index + "].price").value(price).match(result);
            MockMvcResultMatchers.jsonPath("$[" + index + "].priceList").value(priceList).match(result);
            MockMvcResultMatchers.jsonPath("$[" + index + "].brandId").value(1).match(result);
            MockMvcResultMatchers.jsonPath("$[" + index + "].startDate").exists().match(result);
        } catch (AssertionError | Exception e) {
            throw new AssertionError("Error en la verificación de detalles de precios: " + e.getMessage());
        }
    }

}