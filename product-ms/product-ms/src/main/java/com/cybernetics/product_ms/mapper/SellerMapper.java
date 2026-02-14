package com.cybernetics.product_ms.mapper;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SellerMapper {

    @Mapping(target = "username", ignore = true)
    @Mapping(source = "categoryId", target = "category.categoryId")
    ProductEntity mapProductDtoToEntity(AddProductRequestDto addProductRequestDto);

    ProductEntity mapUpdateProductDtoToEntity(UpdateProductRequestDto updateProductRequestDto, @MappingTarget ProductEntity productEntity);

}
