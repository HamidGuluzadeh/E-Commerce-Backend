package com.cybernetics.product_ms.mapper;

import com.cybernetics.product_ms.dto.response.ProductResponseDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "category.categoryId", target = "categoryId")
    ProductResponseDto mapProductEntityToResponseDto(ProductEntity productEntity);

}
