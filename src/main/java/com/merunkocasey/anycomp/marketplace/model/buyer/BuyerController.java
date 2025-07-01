package com.merunkocasey.anycomp.marketplace.model.buyer;

import com.merunkocasey.anycomp.marketplace.model.purchase.Purchase;
import com.merunkocasey.anycomp.marketplace.dto.BuyerRequest;
import com.merunkocasey.anycomp.marketplace.dto.PurchaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        List<Buyer> buyers = buyerService.getAllBuyers();
        return ResponseEntity.ok(buyers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long id) {
        Buyer buyer = buyerService.getBuyerById(id);
        return ResponseEntity.ok(buyer);
    }

    @PostMapping
    public ResponseEntity<Buyer> registerBuyer(@RequestBody BuyerRequest request) {
        Buyer newBuyer =new Buyer(request.name(), request.email());
        Buyer registeredBuyer = buyerService.registerBuyer(newBuyer);
        return ResponseEntity.status(201).body(registeredBuyer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyerDetails(@PathVariable Long id, @RequestBody BuyerRequest request) {
        Buyer buyerDetails = new Buyer(request.name(), request.email());
        Buyer buyer = buyerService.updateBuyerDetails(id, buyerDetails);
        return ResponseEntity.ok(buyer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Purchase> purchaseItem(@RequestBody PurchaseRequest request) {
        Purchase purchase = buyerService.purchaseItem(
                request.buyerId(),
                request.itemId(),
                request.quantity()
        );
        return ResponseEntity.status(201).body(purchase);
    }
}
