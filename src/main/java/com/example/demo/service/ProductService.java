package com.example.demo.service;

import com.example.demo.exception.ProductNotFoundIdException;
import com.example.demo.model.Product;
public interface ProductService {
    Iterable<Product> findAll();
    Product findById(Long id) throws ProductNotFoundIdException;
    Product save(Product product);
    Product update(Long id,Product product) throws ProductNotFoundIdException;
      String delete(Long id) throws ProductNotFoundIdException;

}
