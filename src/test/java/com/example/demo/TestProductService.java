package com.example.demo;

import com.example.demo.exception.ProductNotFoundIdException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.serviceImpl.ProductServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TestProductService {
    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findByIdTest() throws ProductNotFoundIdException {
        Product productExpected = new Product("phone", BigDecimal.valueOf(100));
        productExpected.setId(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(productExpected));
        Product product = productService.findById(1L);
        assertEquals(productExpected, product);

    }

    @Test
    void getAllTest() {
        Product product = new Product("Iphone", BigDecimal.valueOf(100));
        Product product1 = new Product("Samsung", BigDecimal.valueOf(110));
        Product product2 = new Product("TV sony", BigDecimal.valueOf(2400));
        List<Product> expectedList = List.of(product, product1, product2);
        when(productsRepository.findAll()).thenReturn(expectedList);
        List<Product> currentList = (List<Product>) productService.findAll();
        assertEquals(expectedList, currentList);
    }

    @Test
    void saveTest() {
        Product expectedProduct = new Product("TV", BigDecimal.valueOf(5));
        when(productsRepository.save(any(Product.class))).thenReturn(expectedProduct);
        Product productCurrent = productService.save(expectedProduct);
        assertEquals(expectedProduct, productCurrent);
    }


    @Test
    void deleteTest() throws ProductNotFoundIdException {
        Product product = new Product("TV", BigDecimal.valueOf(100));
        product.setId(1L);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        String currentMessage = productService.delete(1L);
        assertEquals("has been deleted", currentMessage);
    }

}
