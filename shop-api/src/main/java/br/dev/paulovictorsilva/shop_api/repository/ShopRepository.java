package br.dev.paulovictorsilva.shop_api.repository;

import br.dev.paulovictorsilva.shop_api.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>{}
