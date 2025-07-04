package com.merunkocasey.anycomp.marketplace.model.buyer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.merunkocasey.anycomp.marketplace.model.purchase.Purchase;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "buyers")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchasedItems;

    public Buyer() {

    }

    public Buyer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Purchase> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Purchase> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

}
