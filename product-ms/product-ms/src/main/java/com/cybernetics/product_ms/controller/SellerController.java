package com.cybernetics.product_ms.controller;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;
import com.cybernetics.product_ms.service.impl.SellerServiceImpl;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerController {
    SellerServiceImpl sellerService;

    @PostMapping("/new-product")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid AddProductRequestDto addProductRequestDto) {
        sellerService.addProduct(addProductRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductRequestDto updateProductRequestDto, @PathVariable String productId) {
        sellerService.updateProduct(updateProductRequestDto, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        sellerService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
