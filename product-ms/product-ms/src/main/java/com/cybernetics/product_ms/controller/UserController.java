package com.cybernetics.product_ms.controller;

import com.cybernetics.product_ms.dto.SuccessDto;
import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;
import com.cybernetics.product_ms.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cybernetics.product_ms.utils.SuccessStatus.SUCCESS;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserServiceImpl userService;

    @GetMapping("/all-products")
    public ResponseEntity<SuccessDto<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> productResponseDtoList = userService.getAllProducts();
        SuccessDto<List<ProductResponseDto>> productResponseSuccessDto = new SuccessDto<>(SUCCESS, productResponseDtoList);
        return new ResponseEntity<>(productResponseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<SuccessDto<ProductResponseDto>> getProductById(@PathVariable String productId) {
        ProductResponseDto productResponseDto = userService.getProductById(productId);
        SuccessDto<ProductResponseDto> productResponseSuccessDto = new SuccessDto<>(SUCCESS, productResponseDto);
        return new ResponseEntity<>(productResponseSuccessDto, HttpStatus.OK);
    }

    @PutMapping("/product/count/decrease")
    public void decreaseProductCount(@RequestBody @Valid DecreaseCountRequestDto decreaseCountRequestDto) {
        userService.decreaseProductCount(decreaseCountRequestDto);
    }

    @GetMapping("/product/{productId}/stock-price")
    public GetStockPriceResponseDto getStockAndPrice(@PathVariable String productId) {
        return userService.getStockAndPrice(productId);
    }
}
