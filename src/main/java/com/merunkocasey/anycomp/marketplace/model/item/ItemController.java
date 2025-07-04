package com.merunkocasey.anycomp.marketplace.model.item;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Item>> getAllItems(Pageable pageable) {
        Page<Item> itemList = itemService.getAllItems(pageable);
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
    @PutMapping(path = "/{id}")
    public ResponseEntity<Item> updateItemDetails(@PathVariable("id") Long itemId, @RequestBody ItemRequest request) {
        Item updatedItem = itemService.updateItemDetails(itemId, request);
        return ResponseEntity.ok(updatedItem);
    }

    @Operation(
            summary = "Delete item from the market",
            description = "Delete item from the market.")
    @DeleteMapping(path = "/{id}") // Delete item
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
