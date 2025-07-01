package com.merunkocasey.anycomp.marketplace.model.seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findSellerByName(String name);

    Optional<Seller> findByEmail(String email);
}
