package com.merunkocasey.anycomp.marketplace.model.buyer;

import com.merunkocasey.anycomp.marketplace.dto.BuyerResponse;
import com.merunkocasey.anycomp.marketplace.dto.PurchaseResponse;
import com.merunkocasey.anycomp.marketplace.dto.BuyerRequest;
import com.merunkocasey.anycomp.marketplace.dto.PurchaseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/buyers")
@Tag(name = "Buyer API", description = "Endpoints for managing buyer and purchases.")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @Operation(
            summary = "Get list of buyers",
            description = "Get list of buyers from the system.")
    @GetMapping // List all buyers
    public ResponseEntity<Page<BuyerResponse>> getAllBuyers(Pageable pageable) {
        Page<BuyerResponse> buyers = buyerService.getAllBuyers(pageable);
        return ResponseEntity.ok(buyers);
    }

    @Operation(
            summary = "Get a specific buyer",
            description = "Get a specific buyer by its ID from the system.")
    @GetMapping("/{id}") // Get a specific buyer
    public ResponseEntity<BuyerResponse> getBuyerById(@PathVariable Long id) {
        BuyerResponse buyer = buyerService.getBuyerByIdWithMapping(id);
        return ResponseEntity.ok(buyer);
    }

    @Operation(
            summary = "Create/Register a buyer",
            description = "Create a new buyer account in the system.")
    @PostMapping // Create a buyer
    public ResponseEntity<Buyer> registerBuyer(@RequestBody BuyerRequest request) {
        Buyer newBuyer =new Buyer(request.name(), request.email());
        Buyer registeredBuyer = buyerService.registerBuyer(newBuyer);
        return ResponseEntity.status(201).body(registeredBuyer);
    }

    @Operation(
            summary = "Update a buyer details",
            description = "Update a specific buyer details in the system.")
    @PutMapping("/{id}") // Update a buyer
    public ResponseEntity<Buyer> updateBuyerDetails(@PathVariable Long id, @RequestBody BuyerRequest request) {
        Buyer buyerDetails = new Buyer(request.name(), request.email());
        Buyer buyer = buyerService.updateBuyerDetails(id, buyerDetails);
        return ResponseEntity.ok(buyer);
    }

    @Operation(
            summary = "Delete a buyer account",
            description = "Delete a specific buyer account from the system.")
    @DeleteMapping("/{id}") // Delete a buyer
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Purchase item for buyer",
            description = "Purchase item from seller. This will check item availability, deduct bought quantity from the available stock, and create a new purchase entry")
    @PostMapping("/{id}/purchase")
    public ResponseEntity<PurchaseResponse> purchaseItem(@PathVariable Long id, @RequestBody PurchaseRequest request) {
        PurchaseResponse purchase = buyerService.purchaseItem(
                id,
                request.itemId(),
                request.quantity()
        );
        return ResponseEntity.status(201).body(purchase);
    }
}
