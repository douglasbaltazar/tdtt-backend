package com.tdtt.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdtt.backend.exception.ResourceNotFoundException;
import com.tdtt.backend.model.Product;
import com.tdtt.backend.repository.ProductRepository;

@RestController
@RequestMapping("api/v1/")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	private String erro = "Não foi encontrado um Produto com o id: ";
	// getProducts
	@GetMapping("products")
	public List<Product> getProducts() {
		return this.productRepository.findAll();
	}
	
	// getProduct By Id
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(erro + productId));
		return ResponseEntity.ok().body(product);
	}
	
	// Create a new Product
	@PostMapping("product")
	public Product createProduct(@RequestBody Product product) {
		return this.productRepository.save(product);
	}
	
	// Update Product
	@PutMapping("product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId, 
			@Validated @RequestBody Product productUpdate) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(erro + productId));
		product.setAdditionalInfo(productUpdate.getAdditionalInfo());
		product.setImgCover(productUpdate.getImgCover());
		product.setLink(productUpdate.getLink());
		product.setNewPrice(productUpdate.getNewPrice());
		product.setProductName(productUpdate.getProductName());
		return ResponseEntity.ok(this.productRepository.save(product));
	}
	
	// Delete a Product
	@DeleteMapping("products/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(erro + productId));
		this.productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
