package com.compassuol.sp.challenge.ecommerce.domain.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false, length = 120)
    private String name;
    @Column(name = "value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @Positive
    private BigDecimal value;
    @Column(name = "description", nullable = false, length = 256)
    @Size(min = 10)
    private String description;
}
