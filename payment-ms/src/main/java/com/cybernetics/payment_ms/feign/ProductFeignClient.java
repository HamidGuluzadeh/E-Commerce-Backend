package com.cybernetics.payment_ms.feign;

import com.cybernetics.payment_ms.dto.request.DecreaseCountReqDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(
        name = "payment-service",
        url = "${feign.clients.product-service.url}"
)
public interface ProductFeignClient {

    @GetMapping(value = "/product/{productId}/count", produces = MediaType.APPLICATION_JSON_VALUE)
    Integer getProductCount(@PathVariable("productId") String productId);

    @PutMapping(value = "/product/count/decrease", produces = MediaType.APPLICATION_JSON_VALUE)
    void decreaseProductCount(@RequestBody @Valid DecreaseCountReqDto decreaseCountReqDto);

    @GetMapping(value = "/product/{productId}/price", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal getProductPrice(@PathVariable("productId") String productId);

}
