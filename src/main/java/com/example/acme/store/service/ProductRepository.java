package com.example.acme.store.service;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {
    Collection<Product> getAll();

    Optional<Product> get(String id);

    void save(Product product);

    void delete(Product product);
}
