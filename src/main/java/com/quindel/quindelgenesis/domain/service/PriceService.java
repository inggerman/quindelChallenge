package com.quindel.quindelgenesis.domain.service;




import com.quindel.quindelgenesis.application.dto.PriceRequestDTO;
import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;

import java.util.List;

/**
 * Interface that declares the functionality of the PriceService.
 */
public interface PriceService {

    /**
     * Retrieves a list of PriceResponseDTO based on the provided PriceRequestDTO.
     *
     * @param priceRequestDTO the request parameters {@link PriceRequestDTO}
     * @return A list of PriceResponseDTO {@link PriceResponseDTO}
     */
    List<PriceResponseDTO> retrievePrices(PriceRequestDTO priceRequestDTO);

}