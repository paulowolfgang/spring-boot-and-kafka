package br.dev.paulovictorsilva.shop_validator.repository;

import br.dev.paulovictorsilva.shop_validator.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product findByIdentifier(String identifier);
}
