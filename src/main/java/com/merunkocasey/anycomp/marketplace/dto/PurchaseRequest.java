package com.merunkocasey.anycomp.marketplace.dto;

public record PurchaseRequest(Long buyerId, Long itemId, Integer quantity) {
}