package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.OrderProduct;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.web.dto.AddressCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderUpdateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductOrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.EXISTING_PRODUCT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderUtils {

    public static OrderUpdateDto createUpdateDto(Order order) {
        OrderUpdateDto dto = new OrderUpdateDto();
        dto.setStatus(order.getStatus().name());
        return dto;
    }

    public static OrderCreateDto createOrderDto(Order order) {
        OrderCreateDto dto = new OrderCreateDto();
        dto.setProducts(createProductOrderDto(order.getProducts()));
        dto.setAddress(createAddressCreateDto(order.getAddress()));
        dto.setPaymentMethod(order.getPaymentMethod().name());
        return dto;
    }

    private static List<ProductOrderDto> createProductOrderDto(List<OrderProduct> products) {
        List<ProductOrderDto> listDto = new ArrayList<>();
        products.forEach(item -> {
            final ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setProductId(item.getProduct().getId());
            productOrderDto.setQuantity(item.getQuantity());
            listDto.add(productOrderDto);
        });
        return listDto;
    }

    private static AddressCreateDto createAddressCreateDto(Address address) {
        AddressCreateDto addressCreateDto = new AddressCreateDto();
        addressCreateDto.setPostalCode(address.getPostalCode());
        addressCreateDto.setComplement(address.getComplement());
        addressCreateDto.setNumber(address.getNumber());
        return addressCreateDto;
    }

    public static Order generateValidOrder(PaymentMethod paymentMethod) {
        return generateValidOrder(paymentMethod, EXISTING_PRODUCT);
    }

    public static Order generateValidOrder(PaymentMethod paymentMethod, Product product) {
        final List<OrderProduct> products = List.of(OrderProduct.builder()
                .product(product).quantity(1)
                .build());

        final BigDecimal subtotal = product.getValue();
        BigDecimal discount = BigDecimal.ZERO;
        if (paymentMethod.equals(PaymentMethod.PIX)) {
            discount = (subtotal.multiply(BigDecimal.valueOf(0.05)));
        }

        final BigDecimal totalValue = subtotal.subtract(discount);
        return Order.builder()
                .products(products)
                .address(Address.builder()
                        .postalCode("01001000").complement("complement").number(123).city("São Paulo").state("SP").street("Rua Teste")
                        .build())
                .paymentMethod(paymentMethod)
                .subtotalValue(subtotal)
                .discount(discount)
                .totalValue(totalValue)
                .status(OrderStatus.CONFIRMED)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static Order generateInvalidOrder() {
        return Order.builder()
                .products(Collections.emptyList())
                .address(Address.builder().postalCode(null).build())
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .subtotalValue(EXISTING_PRODUCT.getValue())
                .discount(BigDecimal.ZERO)
                .totalValue(EXISTING_PRODUCT.getValue())
                .status(OrderStatus.CONFIRMED)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
