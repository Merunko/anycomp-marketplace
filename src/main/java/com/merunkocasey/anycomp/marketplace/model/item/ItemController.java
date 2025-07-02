package com.merunkocasey.anycomp.marketplace.model.item;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping() // List all items
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> itemList = itemService.getAllItems();
        return ResponseEntity.ok().body(itemList);
    }

    @GetMapping(path = "/{id}") // Get item by ID
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok().body(item);
    }

    @PutMapping(path = "/{id}") // Update item
    public ResponseEntity<Item> updateItemDetails(@PathVariable Long itemId, @RequestBody ItemRequest request) {
        Item newItemDetails = new Item(
                itemId,
                request.name(),
                request.description(),
                request.price(),
                request.quantity());

        Item updatedItem = itemService.updateItemDetails(itemId, newItemDetails);
        return ResponseEntity.ok().body(updatedItem);
    }

    @DeleteMapping(path = "/{id}") // Delete item
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
