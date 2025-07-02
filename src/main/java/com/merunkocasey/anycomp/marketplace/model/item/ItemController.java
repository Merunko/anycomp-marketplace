package com.merunkocasey.anycomp.marketplace.model.item;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@Tag(name = "Item API", description = "Endpoints for managing item.")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(
            summary = "Get list of items in the market",
            description = "Get list of items in the market from the system.")
    @GetMapping() // List all items
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> itemList = itemService.getAllItems();
        return ResponseEntity.ok().body(itemList);
    }

    @Operation(
            summary = "Get a specific item in the market by its ID",
            description = "Get a specific item in the market by its ID in the system.")
    @GetMapping(path = "/{id}") // Get item by ID
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok().body(item);
    }

    @Operation(
            summary = "Update a specific item in the market by its ID",
            description = "Update a specific item in the market by its ID in the system.")
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

    @Operation(
            summary = "Delete item from the market",
            description = "Delete item from the market.")
    @DeleteMapping(path = "/{id}") // Delete item
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
