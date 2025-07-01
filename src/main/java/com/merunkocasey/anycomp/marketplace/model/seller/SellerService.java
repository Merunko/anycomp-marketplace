package com.merunkocasey.anycomp.marketplace.model.seller;

import com.merunkocasey.anycomp.marketplace.model.item.Item;
import com.merunkocasey.anycomp.marketplace.model.item.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ItemRepository itemRepository;

    public SellerService(SellerRepository sellerRepository, ItemRepository itemRepository) {
        this.sellerRepository = sellerRepository;
        this.itemRepository = itemRepository;
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + sellerId));
    }

    public Seller registerSeller(Seller seller) {
        if (sellerRepository.findByEmail(seller.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email is already registered!");
        }

        if (sellerRepository.findSellerByName(seller.getName()).isPresent()) {
            throw new IllegalArgumentException("This seller name is already registered!");
        }

        return sellerRepository.save(seller);
    }

    @Transactional
    public Seller updateSellerDetails(Long sellerId, Seller sellerDetails) {
        Seller seller = getSellerById(sellerId);
        seller.setName(sellerDetails.getName());
        seller.setEmail(sellerDetails.getEmail());
        return sellerRepository.save(seller);
    }

    @Transactional
    public void deleteSeller(Long sellerId) {
        if (!sellerRepository.existsById(sellerId)) {
            throw new IllegalArgumentException("Seller not found with id: " + sellerId);
        }
        sellerRepository.deleteById(sellerId);
    }

    public List<Item> getItemsBySeller(Long sellerId) {
        if (!sellerRepository.existsById(sellerId)) {
            throw new IllegalArgumentException("Seller not found with id: " + sellerId);
        }
        return itemRepository.findBySellerId(sellerId);
    }

    @Transactional
    public Item addItemToMarket(Long sellerId, Item item) {
        Seller seller = getSellerById(sellerId);
        item.setSeller(seller);
        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItemDetails(Long sellerId, Long itemId, Item itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        if (!Objects.equals(item.getSeller().getId(), sellerId)) {
            throw new IllegalArgumentException("This item does not belong to seller with id: " + sellerId);
        }

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setQuantity(itemDetails.getQuantity());

        return itemRepository.save(item);
    }

    @Transactional
    public void removeItemFromMarket(Long sellerId, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));


        if (!Objects.equals(item.getSeller().getId(), sellerId)) {
            throw new IllegalArgumentException("This item does not belong to seller with id: " + sellerId);
        }

        itemRepository.deleteById(itemId);
    }

}
