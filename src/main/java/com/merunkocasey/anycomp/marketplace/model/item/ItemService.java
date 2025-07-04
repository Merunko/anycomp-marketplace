package com.merunkocasey.anycomp.marketplace.model.item;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import com.merunkocasey.anycomp.marketplace.model.seller.Seller;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));
    }

    @Transactional
    public Item updateItemDetails(Long itemId, Item itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setQuantity(itemDetails.getQuantity());

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItemDetails(Long itemId, ItemRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setQuantity(request.quantity());

        return itemRepository.save(item);

    }

    @Transactional
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new IllegalArgumentException("Item not found with id: " + itemId);
        }

        itemRepository.deleteById(itemId);
    }

}
