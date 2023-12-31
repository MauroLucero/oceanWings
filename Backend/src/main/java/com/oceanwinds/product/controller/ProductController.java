package com.oceanwinds.product.controller;

import Global.dto.MessageDto;
import Global.exceptions.AttributeException;
import Global.util.PaginatedResponse;
import com.oceanwinds.feature.repository.FeatureRepository;
import com.oceanwinds.product.entity.Product;
import com.oceanwinds.product.entity.dto.ProductDto;
import com.oceanwinds.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    private final FeatureRepository featureRepository;



    @GetMapping("/all")
    public Set<Product> getAllProduct() {
        return productService.getAllActiveProduct();
    }


    @GetMapping("/allFiltered/")
    public ResponseEntity<List<Product>> getAllProductFilter(
            @RequestParam(defaultValue = "", required = false) String city,
            @RequestParam(defaultValue = "", required = false) Set<Long> categoriesId,
            @RequestParam(defaultValue = "", required = false) Set<Long> featuresId,
            @RequestParam(defaultValue = "", required = false) Double minPrice,
            @RequestParam(defaultValue = "", required = false) Double maxPrice,
            @RequestParam(defaultValue = "", required = false) LocalDate dateInit,
            @RequestParam(defaultValue = "", required = false) LocalDate dateEnd) {

        List<Product> products = productService.getAllProductFilter(city, categoriesId, featuresId, minPrice, maxPrice, dateInit, dateEnd);

        return ResponseEntity.ok(products);
    }


    @GetMapping("/productByCategoryId/")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@RequestParam List<Long> categoriesId) {
        List<Product> products = productService.getProductByCategoryId(categoriesId);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/productByCategoryName/")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@RequestParam List<String> categoryName) {
        List<Product> products = productService.getProductsByCategoryName(categoryName);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(products);
        }
    }


    @GetMapping("/productByFeaturesId/")
    public ResponseEntity<Set<Product>> getProductsByFeaturesId(@RequestParam Set<Long> featuresId) {
        Set<Product> products = (Set<Product>) productService.getProductByFeaturesId(featuresId);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/productByFeaturesName/")
    public ResponseEntity<Set<Product>> getProductsByFeaturesName(@RequestParam Set<String> featuresName) {
        Set<Product> products = productService.getProductByFeaturesName(featuresName);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<PaginatedResponse<Product>> getProductByPage(
            @PathVariable int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<Product> response = productService.getproductsByPage(page, perPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<Set<Product>> getAvailableProduct() throws AttributeException {
        Set<Product> product = productService.getAvailableProduct();
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
        return ResponseEntity.ok(product);

        }

    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDto> createProduct(@RequestBody ProductDto dto) throws AttributeException {
        productService.createProduct(dto);
        String message = "Product created successfully";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<MessageDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) throws AttributeException {
        productService.updateProduct(id, dto);

        String message = "Product updated successfully";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<MessageDto> deleteProduct(@PathVariable Long id) {

        if(!productService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(HttpStatus.NOT_FOUND, "Product not found"));
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, "Product deleted successfully"));
    }

    @GetMapping("/urlImage/{id}")
    public Set<Map<String, Object>> getProducttModifiedImages(@PathVariable Long id) {
        return productService.findProductWithModifiedImages(id);
    }




}
