package com.snapthetitle.backend.service;

import com.snapthetitle.backend.dto.ProductDto;
import com.snapthetitle.backend.dto.ProductOptionDto;
import com.snapthetitle.backend.entity.Product;
import com.snapthetitle.backend.entity.ProductOption;
import com.snapthetitle.backend.repository.ProductOptionRepository;
import com.snapthetitle.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final ProductOptionRepository optionRepo;

    /** 연도별 상품 조회 */
    public List<ProductDto> getByYear(Integer year) {
        return productRepo
                .findByYearAndDeletedYnOrderByDisplayOrderAsc(year, "N")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** 상품 생성 */
    public ProductDto create(ProductDto dto) {
        Product e = new Product();
        e.setYear(dto.getYear());
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setImageUrl(dto.getImageUrl());
        e.setDeletedYn("N");
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());
        Product saved = productRepo.save(e);

        // 옵션 생성
        if (dto.getOptions() != null) {
            dto.getOptions().forEach(optDto -> {
                ProductOption opt = new ProductOption();
                opt.setProduct(saved);
                opt.setLabel(optDto.getLabel());
                opt.setValue(optDto.getValue());
                opt.setDisplayOrder(optDto.getDisplayOrder());
                opt.setDeletedYn("N");
                opt.setCreatedAt(LocalDateTime.now());
                opt.setUpdatedAt(LocalDateTime.now());
                optionRepo.save(opt);
            });
        }
        return toDto(saved);
    }

    /** 상품 수정 */
    public ProductDto update(Long id, ProductDto dto) {
        Product e = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: id=" + id));
        e.setYear(dto.getYear());
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setImageUrl(dto.getImageUrl());
        e.setUpdatedAt(LocalDateTime.now());
        Product updated = productRepo.save(e);

        // 기존 옵션 소프트 삭제
        optionRepo.findByProductIdAndDeletedYnOrderByDisplayOrderAsc(updated.getId(), "N")
                .forEach(opt -> {
                    opt.setDeletedYn("Y");
                    opt.setUpdatedAt(LocalDateTime.now());
                    optionRepo.save(opt);
                });

        // 새로운 옵션 생성
        if (dto.getOptions() != null) {
            dto.getOptions().forEach(optDto -> {
                ProductOption opt = new ProductOption();
                opt.setProduct(updated);
                opt.setLabel(optDto.getLabel());
                opt.setValue(optDto.getValue());
                opt.setDisplayOrder(optDto.getDisplayOrder());
                opt.setDeletedYn("N");
                opt.setCreatedAt(LocalDateTime.now());
                opt.setUpdatedAt(LocalDateTime.now());
                optionRepo.save(opt);
            });
        }
        return toDto(updated);
    }

    /** 상품 소프트 삭제 */
    public void delete(Long id) {
        Product e = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: id=" + id));
        e.setDeletedYn("Y");
        e.setUpdatedAt(LocalDateTime.now());
        productRepo.save(e);
    }

    /** Entity → DTO 매핑 */
    private ProductDto toDto(Product e) {
        ProductDto dto = new ProductDto();
        dto.setId(e.getId());
        dto.setYear(e.getYear());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setPrice(e.getPrice());
        dto.setImageUrl(e.getImageUrl());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setDeletedYn(e.getDeletedYn());

        List<ProductOptionDto> opts = optionRepo
                .findByProductIdAndDeletedYnOrderByDisplayOrderAsc(e.getId(), "N")
                .stream()
                .map(this::optionToDto)
                .collect(Collectors.toList());
        dto.setOptions(opts);

        return dto;
    }

    /** 옵션 Entity → DTO 매핑 */
    private ProductOptionDto optionToDto(ProductOption e) {
        ProductOptionDto dto = new ProductOptionDto();
        dto.setId(e.getId());
        dto.setProductId(e.getProduct().getId());
        dto.setLabel(e.getLabel());
        dto.setValue(e.getValue());
        dto.setDisplayOrder(e.getDisplayOrder());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setDeletedYn(e.getDeletedYn());
        return dto;
    }
}
