package com.merunkocasey.anycomp.marketplace.model.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByBuyerId(Long buyerId);

    List<Purchase> findByItemId(Long itemId);

    List<Purchase> findByStatus(PurchaseStatus status);

    List<Purchase> findByBuyerIdAndStatus(Long buyerId, PurchaseStatus status);

}
