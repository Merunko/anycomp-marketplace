package com.merunkocasey.anycomp.marketplace.model.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemByName(String name);
    Item findItemById(Long id);

    List<Item> findBySeller_Id(Long sellerId);

}
