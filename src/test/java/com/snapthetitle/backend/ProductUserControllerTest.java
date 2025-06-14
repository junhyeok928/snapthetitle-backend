// src/test/java/com/snapthetitle/backend/controller/user/ProductUserControllerTest.java
package com.snapthetitle.backend.controller.user;

import com.snapthetitle.backend.dto.ProductDto;
import com.snapthetitle.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductUserController.class)
class ProductUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void whenGetByYear_thenReturnProducts() throws Exception {
        // given
        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setYear(2025);
        dto.setName("WEDDING SNAP SEMI");
        dto.setDescription("기본 패키지");
        dto.setPrice("400,000원");
        dto.setImageUrl("https://example.com/img.jpg");
        dto.setDisplayOrder(0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setDeletedYn("N");

        BDDMockito.given(productService.getByYear(2025))
                .willReturn(Collections.singletonList(dto));

        // when & then
        mockMvc.perform(get("/api/products")
                        .param("year", "2025")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].year").value(2025))
                .andExpect(jsonPath("$[0].name").value("WEDDING SNAP SEMI"));
    }
}
