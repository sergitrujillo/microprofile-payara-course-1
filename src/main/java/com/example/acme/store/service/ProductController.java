package com.example.acme.store.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;

@Path("product")
@RequestScoped
public class ProductController {

    @Inject
    ProductRepository repository;

    @GET
    public Collection<Product> getAll(){
        return repository.getAll();
    }

    @GET
    @Path("{name}")
    public Product get(@PathParam("name") String name){
        return repository.get(name).orElseThrow(NotFoundException::new);
    }

    @POST
    public Response post(@Valid Product product){
        repository.get(product.getName()).ifPresent( p -> {throw new WebApplicationException(Response.Status.CONFLICT);});
        repository.save(product);
        final URI productUri = UriBuilder.fromResource(ProductController.class).path("{name}").build(product.getName());
        return Response.created(productUri).build(); // Link/Redirect to new resource of product
    }

    @PUT
    @Path("{name}")
    public Product put(@Valid Product product, @PathParam("name") String name){
        if (!Objects.equals(name, product.getName())){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        repository.save(product);
        return product;
    }

    @DELETE
    @Path("{name}")
    public Product delete(@PathParam("name") String name){
        Product product = repository.get(name).orElseThrow(NotFoundException::new);
        repository.delete(product);
        return product; // return for the last time the product
    }

}
