package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.service.serviceImpl.ProductServiceImpl;
import com.example.demo.web.ProductController;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class TestProductController {
    @MockBean
    private ProductServiceImpl service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTest() throws Exception {
        Product product = new Product("Iphone", BigDecimal.valueOf(100));
        Product product1 = new Product("Samsung", BigDecimal.valueOf(110));
        Product product2 = new Product("TV sony", BigDecimal.valueOf(2400));
        List<Product> expectedList = List.of(product, product1, product2);
        when(service.findAll()).thenReturn(expectedList);
        List<Product> currentList = (List<Product>) service.findAll();
        assertEquals(expectedList, currentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        Long id = 1L;
        Product productExpected = new Product("Iphone", BigDecimal.valueOf(100));
        productExpected.setId(id);
        productExpected.setVersion(id);
        when(service.findById(id)).thenReturn(productExpected);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value((productExpected).getName()));
        Product productCurrent = service.findById(id);
        assertEquals(productExpected, productCurrent);
    }

    @Test
    void saveTest() throws Exception {
        Long id = 1L;
        Product productExpected = new Product("Iphone", BigDecimal.valueOf(100));
        productExpected.setId(id);
        when(service.save(any(Product.class))).thenReturn(productExpected);
        when(service.save(any(Product.class))).thenReturn(productExpected);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(new ObjectMapper().writeValueAsString(productExpected))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        Product productCurrent = service.save(productExpected);
        assertEquals(productExpected, productCurrent);
    }

    @Test
    void updateTest() throws Exception {
        String uri = "/products/{id}";
        Long id = 1L;
        Product productExpected = new Product("Iphone", BigDecimal.valueOf(100));
        productExpected.setId(id);
        productExpected.setVersion(id);
        Product newProduct = new Product();
        newProduct.setId(productExpected.getId());
        newProduct.setPrice(BigDecimal.valueOf(110));
        newProduct.setVersion(2L);
        when(service.update(eq(productExpected.getId()), any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(put(uri, id).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andDo(print());
        assertNotEquals(productExpected.getVersion(), service.update(id, newProduct).getVersion());
    }

    @Test
    void deleteTest() throws Exception {
        Product productToDelete = new Product("Iphone", BigDecimal.valueOf(101));
        productToDelete.setId(1L);
        when(service.delete(productToDelete.getId())).thenReturn("has been deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1)
                        .content(new ObjectMapper().writeValueAsString(productToDelete))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals("has been deleted", service.delete(1L));
    }
}
