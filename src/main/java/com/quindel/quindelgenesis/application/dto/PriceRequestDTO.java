package com.quindel.quindelgenesis.application.dto;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO class for setting parameters received from the API for price queries.
 */
@Setter
@Getter
@Builder
public class PriceRequestDTO {

    /**
     * The date and time when the price application is evaluated.
     */
    @NotNull(message = "Apply date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applyDate;

    /**
     * Unique identifier of the product.
     */
    private Integer productId;

    /**
     * Unique identifier of the brand.
     */
    @NotNull(message = "Brand ID is required.")
    private Long brandId;

    /**
     * The limit on the number of price rows to be returned. Must be a positive number.
     */
    @Min(value = 1, message = "Limit must be at least 1.")
    private Integer limit;
}