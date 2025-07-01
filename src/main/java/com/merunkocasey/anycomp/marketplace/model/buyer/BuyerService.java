package com.merunkocasey.anycomp.marketplace.model.buyer;

import com.merunkocasey.anycomp.marketplace.model.item.Item;
import com.merunkocasey.anycomp.marketplace.model.item.ItemRepository;
import com.merunkocasey.anycomp.marketplace.model.purchase.Purchase;
import com.merunkocasey.anycomp.marketplace.model.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;

    public BuyerService(BuyerRepository buyerRepository, PurchaseRepository purchaseRepository, ItemRepository itemRepository) {
        this.buyerRepository = buyerRepository;
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
    }

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer getBuyerById(Long buyerId) {
        return buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found with id: " + buyerId));
    }

    public Buyer registerBuyer(Buyer buyer) {
        if (buyerRepository.findByEmail(buyer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email is already registered!");
        }
        return buyerRepository.save(buyer);
    }

    @Transactional
    public Buyer updateBuyerDetails(Long buyerId, Buyer buyerDetails) {
        Buyer buyer = getBuyerById(buyerId);
        buyer.setName(buyerDetails.getName());
        buyer.setEmail(buyerDetails.getEmail());
        return buyerRepository.save(buyer);
    }

    @Transactional
    public void deleteBuyer(Long buyerId) {
        if (!buyerRepository.existsById(buyerId)) {
            throw new IllegalArgumentException("Buyer not found with id: " + buyerId);
        }
        buyerRepository.deleteById(buyerId);
    }

//    public void buyItems(Buyer buyer, Item item, Integer quantity) {
//        double totalCost = quantity * item.getPrice();
//        Purchase purchase = new Purchase(buyer, item, quantity, totalCost);
//        buyer.getPurchasedItems().add(purchase);
//        purchaseRepository.save(purchase);
//    }

    @Transactional
    public Purchase purchaseItem(Long buyerId, Long itemId, Integer quantity) {
        Buyer buyer = getBuyerById(buyerId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        if (item.getQuantity() < quantity) {
            throw new IllegalArgumentException("Only " + item.getQuantity() + " stock available!");
        }

        item.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(item);

        double totalCost = quantity * item.getPrice();
        Purchase purchase = new Purchase(buyer, item, quantity, totalCost);
        return purchaseRepository.save(purchase);
    }

}
