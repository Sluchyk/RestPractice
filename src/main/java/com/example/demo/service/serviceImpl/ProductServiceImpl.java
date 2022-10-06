package com.example.demo.service.serviceImpl;

import com.example.demo.exception.ProductNotFoundIdException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;

    @Override
    public Iterable<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundIdException {
        return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundIdException("Product with id: " + id + " not found"));
    }

    @Override
    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public Product update(Long id, Product newProduct) throws ProductNotFoundIdException {
        Product product = findById(id);
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        return productsRepository.save(product);
    }

    @Override
    public String delete(Long id) throws ProductNotFoundIdException {
        productsRepository.delete(findById(id));
        return "has been deleted";
    }
}
