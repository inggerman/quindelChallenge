package com.quindel.quindelgenesis.infrastructure.controller;


import com.quindel.quindelgenesis.application.dto.PriceRequestDTO;
import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;
import com.quindel.quindelgenesis.domain.exception.NotFoundException;
import com.quindel.quindelgenesis.domain.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    /**
     * Handles the HTTP GET request to retrieve prices.
     *
     * <p>This endpoint retrieves a list of prices for a specified product from a particular brand at a given date and time. It utilizes
     * {@link PriceRequestDTO} to encapsulate the request parameters and employs {@link PriceService} to perform the business logic.
     * The result is a list of prices formatted as {@link PriceResponseDTO}.</p>
     *
     * @param applyDate The date and time at which the price application is evaluated. It is expected in ISO date-time format.
     * @param productId The unique identifier of the product for which the prices are being requested.
     * @param brandId The unique identifier of the brand associated with the product.
     * @return A {@link ResponseEntity} object containing the list of {@link PriceResponseDTO} if the query is successful and data is found,
     *         or a 404 Not Found response otherwise.
     */
    @Operation(summary = "Retrieve prices of ZARA products", description = "Retrieves prices for ZARA products based on specified criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation, prices found and returned.",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PriceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No prices found for the given criteria."),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters.")
    })
    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PriceResponseDTO>> getPrices(
            @RequestParam("applyDate") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applyDate,
            @RequestParam("productId") @NotNull  Integer productId,
            @RequestParam("brandId")  Long brandId
    ) {
        return Optional.of(PriceRequestDTO.builder()
                        .applyDate(applyDate)
                        .productId(productId)
                        .brandId(brandId)
                        .build())
                .map(priceService::retrievePrices)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(()->
                        new NotFoundException("123","No prices found for the given criteria."));
    }
}
