package com.lcwd.store.services.impl;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.lcwd.store.dtos.PageableResponse;
import com.lcwd.store.dtos.ProductDto;
import com.lcwd.store.entities.Category;
import com.lcwd.store.entities.Product;
import com.lcwd.store.excetions.ResourceNotFountException;
import com.lcwd.store.helper.Helper;
import com.lcwd.store.respository.CategoryRepository;
import com.lcwd.store.respository.ProductRepository;
import com.lcwd.store.services.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper map;

    @Override
    public ProductDto create(ProductDto dto) {
        Product prod = map.map(dto, Product.class);
        prod.setProductId(UUID.randomUUID().toString());
        Product saveprod = productRepository.save(prod);
        return map.map(saveprod, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto dto, String productId) {
        Product prod = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product is not found"));
        prod.setDescription(dto.getDescription());
        prod.setPrice(dto.getPrice());
        prod.setTitle(dto.getTitle());
        prod.setStock(dto.isStock());
        prod.setLive(dto.isLive());
        Product prod1 = productRepository.save(prod);
        return map.map(prod1, ProductDto.class);
    }

    @Override
    public ProductDto get(String productId) {
        Product prod = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFountException("Product not found"));
        return map.map(prod, ProductDto.class);
    }

    // implemating pageable
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
    	
//    	logger.info("sort By {}",sortBy);
    	
    	Sort sort = (sortDir.toUpperCase().equals("DESC"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
    	
    	// PageRequest is a child of Pageable 
    	Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
    	Page<Product> page = productRepository.findAll(pageable);
    	return Helper.getPageableResponse(page, ProductDto.class);
    	
    	// shifted to helper
//        List<Product> prod = page.getContent(); 
//        List<ProductDto> productDtos = prod.stream().map(p -> map.map(p, ProductDto.class)).collect(Collectors.toList());
//        
//        PageableResponse<ProductDto> response = PageableResponse.builder().content(productDtos).build();
//        
//        return response;
    }

    @Override
    public void delete(String productId) {
        Product prod = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFountException("Product with given id not found..."));
        productRepository.delete(prod);

    }

    @Override
    public List<ProductDto> searchProduct(String keywords) {
        List<Product> lp = productRepository.findByTitleContains(keywords);
        return lp.stream().map(p -> map.map(p, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto create(ProductDto dto, String categoryId) {
        // GET THE CATEGORY OF GIVEN ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFountException("Category not found !!"));
        Product product = map.map(dto, Product.class);
        product.setCategory(category);
        product.setProductId(UUID.randomUUID().toString());
        Product savedProduct = productRepository.save(product);
        return map.map(savedProduct, ProductDto.class);
    }

}
