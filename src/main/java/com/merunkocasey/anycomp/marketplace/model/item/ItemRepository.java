package com.merunkocasey.anycomp.marketplace.model.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemByName(String name);

    List<Item> findBySellerId(Long sellerId);

}
