package com.example.acme.store.service;

import java.util.Collection;

public class Products {
    private Collection<Product> products;

    private Products(Collection<Product> products){
        this.products = products;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public static Products of (Collection<Product> products){
        return new Products(products);
    }
}
