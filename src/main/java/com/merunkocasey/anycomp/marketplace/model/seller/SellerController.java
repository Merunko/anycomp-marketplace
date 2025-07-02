package com.merunkocasey.anycomp.marketplace.model.seller;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import com.merunkocasey.anycomp.marketplace.dto.SellerRequest;
import com.merunkocasey.anycomp.marketplace.model.item.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellers")
@Tag(name = "Seller API", description = "Endpoints for managing seller and its items.")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Operation(
            summary = "Get list of sellers",
            description = "Get list of sellers from the system.")
    @GetMapping // List all sellers
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    @Operation(
            summary = "Get a specific seller",
            description = "Get a specific seller by its ID from the system.")
    @GetMapping("/{id}") // Get a specific seller
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(seller);
    }

    @Operation(
            summary = "Create/Register a seller",
            description = "Create a new seller account in the system.")
    @PostMapping // Create a seller
    public ResponseEntity<Seller> registerSeller(@RequestBody SellerRequest request) {
        Seller newSeller = new Seller(request.name(), request.email());
        Seller registeredSeller = sellerService.registerSeller(newSeller);
        return ResponseEntity.status(201).body(registeredSeller);
    }

    @Operation(
            summary = "Update a seller details",
            description = "Update a specific seller details in the system.")
    @PutMapping("/{id}") // Update a seller
    public ResponseEntity<Seller> updateSellerDetails(@PathVariable Long id, @RequestBody SellerRequest request) {
        Seller newSellerDetails = new Seller(request.name(), request.email());
        Seller updatedSeller = sellerService.updateSellerDetails(id, newSellerDetails);
        return ResponseEntity.ok().body(updatedSeller);
    }

    @Operation(
            summary = "Delete a seller account",
            description = "Delete a specific seller account from the system.")
    @DeleteMapping("/{id}") // Delete a seller
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get list of items from a specific seller",
            description = "Get list of items from a specific seller in the system.")
    @GetMapping("/{sellerId}/items") // Get items by seller
    public ResponseEntity<List<Item>> getItemsBySeller(@PathVariable Long sellerId) {
        List<Item> items = sellerService.getItemsBySeller(sellerId);
        return ResponseEntity.ok().body(items);
    }

    @Operation(
            summary = "Add new item to the market for a specific seller",
            description = "Add new item to the market for a specific seller in the system.")
    @PostMapping("/{sellerId}/items") // Add new item to seller
    public ResponseEntity<Item> addItemToMarket(@PathVariable Long sellerId, @RequestBody ItemRequest request) {
        Item newItem = new Item(
                sellerId,
                request.name(),
                request.description(),
                request.price(),
                request.quantity());

        Item addedItem = sellerService.addItemToMarket(sellerId, newItem);
        return ResponseEntity.status(201).body(addedItem);
    }

    /*
    @PutMapping("/{sellerId}/items/{itemId}")
    public ResponseEntity<Item> updateItemDetails(@PathVariable Long sellerId, @PathVariable Long itemId, @RequestBody ItemRequest request) {
        Item newItemDetails = new Item(
                itemId,
                request.name(),
                request.description(),
                request.price(),
                request.quantity());

        Item updatedItem = sellerService.updateItemDetails(sellerId, itemId, newItemDetails);
        return ResponseEntity.ok().body(updatedItem);
    }
     */

    /*
    @DeleteMapping("/{sellerId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromMarket(@PathVariable Long sellerId, @PathVariable Long itemId) {
        sellerService.removeItemFromMarket(sellerId, itemId);
        return ResponseEntity.noContent().build();
    }
     */

}
