package com.quindel.quindelgenesis.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRICES")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Long priceList;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private int priority;

    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "CURR")
    private String curr;

    /**
     * Attribute for mapping to colum BRAND_ID that contain foreign key.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BRAND_ID", nullable = false)
    private Brand brand;
}