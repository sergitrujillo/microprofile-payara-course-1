package com.example.acme.store.service;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;

@Path("products")
@RequestScoped
public class ProductController {

    @Inject
    ProductRepository repository;

    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "List of all products",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Products.class)))})
    @Operation(
            summary = "Get list of all products",
            description = "get list of all products that are in the catalog")
    @GET
    public Products getAll() {
        return Products.of(repository.getAll());
    }

    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Get one products",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Missing product",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WebApplicationExceptionMapper.Error.class)))})
    @Operation(
            summary = "Get one product",
            description = "Get one specific product")
    @GET
    @Path("{name}")
    public Product retrive(
            @Parameter(
                    description = "The name/id of product",
                    required = true,
                    example = "foo",
                    schema = @Schema(type = SchemaType.STRING))
            @PathParam("name") String name) {
        return repository.get(name).orElseThrow(NotFoundException::new);
    }

    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Add one product",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "product has errors",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Collection.class))),
                    @APIResponse(
                            responseCode = "409",
                            description = "product already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WebApplicationExceptionMapper.Error.class)))})
    @Operation(
            summary = "Get one product",
            description = "Get one specific product")
    @POST
    public Response insert(
            @Parameter(
                    description = "Object product",
                    required = true,
                    example = "foo",
                    schema = @Schema(implementation = Product.class)) @Valid Product product) {
        repository.get(product.getName()).ifPresent(p -> {
            throw new WebApplicationException(Response.Status.CONFLICT);
        });
        repository.save(product);
        final URI productUri = UriBuilder.fromResource(ProductController.class).path("{name}").build(product.getName());
        return Response.created(productUri).build(); // Link/Redirect to new resource of product
    }

    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Add one product",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "product has errors",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Collection.class))),
                    @APIResponse(
                            responseCode = "409",
                            description = "product doesn't consistent with resource path",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WebApplicationExceptionMapper.Error.class)))})
    @Operation(
            summary = "Replace/add one product",
            description = "Replace/add one specific product")
    @PUT
    @Path("{name}")
    public Product update(
            @Parameter(
                    description = "Object product",
                    required = true,
                    example = "foo",
                    schema = @Schema(implementation = Product.class))
            @Valid Product product, @PathParam("name") String name) {
        if (!Objects.equals(name, product.getName())) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        repository.save(product);
        return product;
    }

    @DELETE
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Add one product",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                   })
    @Operation(
            summary = "Remove one product",
            description = "Renove one specific product")
    @Path("{name}")
    public Product delete(
            @Parameter(
                    description = "Object product",
                    required = true,
                    example = "foo",
                    schema = @Schema(implementation = Product.class))
            @PathParam("name") String name) {
        Product product = repository.get(name).orElseThrow(NotFoundException::new);
        repository.delete(product);
        return product; // return for the last time the product
    }

}
