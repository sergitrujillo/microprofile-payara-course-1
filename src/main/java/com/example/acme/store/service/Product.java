package com.example.acme.store.service;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbVisibility;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
@Schema(name="Product", description="POJO that represents the product information.")
public class Product {
    @Schema(required = true, description = "the id/name of product", example = "foo")
    @NotBlank(message = "Name mandatory")
    @Size(max = 100, message = "name should be at max 100 chars")
    private String name;

    @Schema(required = true, description = "explain of product", example = "description of foo")
    @NotNull(message = "Description mandatory")
    @Size(min = 5, message = "description should be at least 5 chars")
    private String description;

    @Schema(required = true, description = "Existences", example = "1")
    @NotNull(message = "Quantity mandatory")
    @Min(value = 0, message = "Quantity should be higher than 0")
    private Long quantity;

    public String getName() {
        return name;
    }
}
