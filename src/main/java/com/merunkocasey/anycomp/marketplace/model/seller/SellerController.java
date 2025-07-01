package com.merunkocasey.anycomp.marketplace.model.seller;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import com.merunkocasey.anycomp.marketplace.dto.SellerRequest;
import com.merunkocasey.anycomp.marketplace.model.item.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(seller);
    }

    @PostMapping
    public ResponseEntity<Seller> registerSeller(@RequestBody SellerRequest request) {
        Seller newSeller = new Seller(request.name(), request.email());
        Seller registeredSeller = sellerService.registerSeller(newSeller);
        return ResponseEntity.status(201).body(registeredSeller);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSellerDetails(@PathVariable Long id, @RequestBody SellerRequest request) {
        Seller newSellerDetails = new Seller(request.name(), request.email());
        Seller updatedSeller = sellerService.updateSellerDetails(id, newSellerDetails);
        return ResponseEntity.ok().body(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sellerId}/items")
    public ResponseEntity<List<Item>> getItemsBySeller(@PathVariable Long sellerId) {
        List<Item> items = sellerService.getItemsBySeller(sellerId);
        return ResponseEntity.ok().body(items);
    }

    @PostMapping("/{sellerId}/items")
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

    @DeleteMapping("/{sellerId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromMarket(@PathVariable Long sellerId, @PathVariable Long itemId) {
        sellerService.removeItemFromMarket(sellerId, itemId);
        return ResponseEntity.noContent().build();
    }

}
