package com.quindel.quindelgenesis.application.service;


import com.quindel.quindelgenesis.application.dto.PriceRequestDTO;
import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;
import com.quindel.quindelgenesis.application.mapper.PriceMapper;
import com.quindel.quindelgenesis.domain.repository.PriceRepository;
import com.quindel.quindelgenesis.domain.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Service class that implement the functionality the query prices by filters.
 */
@Service
@RequiredArgsConstructor
class PriceServiceImpl implements PriceService {

    /**
     * Injection price repository bean by constructor.
     */
    private final PriceRepository priceRepository;

    /**
     * Method that query to database with some parameters and return the result in an Object TDO.
     *
     */

    private static final int DEFAULT_LIMIT = 100;
    @Override
    public List<PriceResponseDTO> retrievePrices(PriceRequestDTO priceRequestDTO) {


        return priceRepository.findPricesByCriteria(
                        priceRequestDTO.getApplyDate(),
                        priceRequestDTO.getApplyDate(),
                        priceRequestDTO.getProductId(),
                        priceRequestDTO.getBrandId(),
                        Optional.of(priceRequestDTO.getLimit()).filter(limit -> limit > 0).orElse(DEFAULT_LIMIT)
                ).orElse(emptyList())
                .stream()
                .map(PriceMapper.INSTANCE::mapToPriceResponseDto)
                .toList();
    }

}