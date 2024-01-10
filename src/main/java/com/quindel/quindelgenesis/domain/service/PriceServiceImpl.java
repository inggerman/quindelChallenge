package com.quindel.quindelgenesis.domain.service;


import com.quindel.quindelgenesis.application.dto.PriceRequestDTO;
import com.quindel.quindelgenesis.application.dto.PriceResponseDTO;
import com.quindel.quindelgenesis.application.mapper.PriceMapper;
import com.quindel.quindelgenesis.application.service.PriceService;
import com.quindel.quindelgenesis.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Service class that implement the functionality the query prices by filters.
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    /**
     * Injection price repository bean by constructor.
     */
    private final PriceRepository priceRepository;

    /**
     *
     */
    private static final int DEFAULT_LIMIT = 100;
    /**
     * Method that query to database with some parameters and return the result in an Object TDO.
     *
     * @param priceRequestDTO A price request DTO {@link PriceRequestDTO}.
     * @return A List of type {@link PriceResponseDTO}.
     */
    @Override
    public List<PriceResponseDTO> retrievePrices(PriceRequestDTO priceRequestDTO) {


        return priceRepository.findPricesByCriteria(
                        priceRequestDTO.getApplyDate(), priceRequestDTO.getApplyDate(),
                        priceRequestDTO.getProductId(), priceRequestDTO.getBrandId(),
                        PageRequest.of(0, Optional.ofNullable(priceRequestDTO.getLimit())
                                .filter(l -> l > 0)
                                .orElse(DEFAULT_LIMIT))
                ).orElse(emptyList())
                .stream()
                .map(PriceMapper.INSTANCE::mapToPriceResponseDto)
                .collect(Collectors.toList());
    }

}