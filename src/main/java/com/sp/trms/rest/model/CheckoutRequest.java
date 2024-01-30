package com.sp.trms.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CheckoutRequest {
    @NotBlank(message = "Customer Id is required.")
    private String customerId;

    @NotBlank(message = "Store Associate Id is required.")
    private String storeAssociateId;

    @NotBlank(message = "Store Id is required.")
    private String storeId;

    @NotBlank(message = "Tool Code is required.")
    private String toolCode;

    @NotNull
    @Min(value = 1, message = "Minimum rental days allowed is 1.")
    private Integer rentalDayCount;

    @Range(min = 0, max = 100, message = "Discount percent is not in the range 0-100.")
    private Integer discountPercent;

    @NotNull(message = "Checkout Date is required.")
    @JsonFormat(pattern = "MM/dd/yy", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkoutDate;
}
