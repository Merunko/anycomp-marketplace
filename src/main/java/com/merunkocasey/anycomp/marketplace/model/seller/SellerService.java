package com.merunkocasey.anycomp.marketplace.model.seller;

import com.merunkocasey.anycomp.marketplace.dto.ItemRequest;
import com.merunkocasey.anycomp.marketplace.model.item.Item;
import com.merunkocasey.anycomp.marketplace.model.item.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ItemRepository itemRepository;

    public SellerService(SellerRepository sellerRepository, ItemRepository itemRepository) {
        this.sellerRepository = sellerRepository;
        this.itemRepository = itemRepository;
    }

    public Page<Seller> getAllSellers(Pageable pageable) {
        return sellerRepository.findAll(pageable);
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
        return itemRepository.findBySeller_Id(sellerId);
    }

    @Transactional
    public Item addItemToMarket(Long sellerId, ItemRequest request) {
        Seller seller = getSellerById(sellerId);

        Item newItem = new Item();
        newItem.setName(request.name());
        newItem.setDescription(request.description());
        newItem.setPrice(request.price());
        newItem.setQuantity(request.quantity());
        newItem.setSeller(seller);

        return itemRepository.save(newItem);
    }

}
