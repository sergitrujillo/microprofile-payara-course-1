package com.example.acme.store.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ProductRepositoryMemory implements ProductRepository{

    private Map<String, Product> data;

    public ProductRepositoryMemory() {
        this.data = new HashMap<>();
    }

    @Override
    public Collection<Product> getAll() {
        return data.values();
    }

    @Override
    public Optional<Product> get(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void save(Product product) {
        data.put(product.getName(), product);
    }

    @Override
    public void delete(Product product) {
        data.remove(product.getName());
    }
}
