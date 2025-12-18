package com.powerofwear.dto.category;

import java.util.List;

public record CategoryWithBreadcrumbDto(
        CategoryResponseDto category,
        List<CategoryResponseDto> breadcrumb
) {}