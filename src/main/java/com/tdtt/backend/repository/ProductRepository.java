package com.tdtt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdtt.backend.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
