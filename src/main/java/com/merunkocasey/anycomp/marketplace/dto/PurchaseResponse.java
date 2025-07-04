package com.merunkocasey.anycomp.marketplace.dto;

public record PurchaseResponse(Long id, Integer quantity, double totalCost, ItemResponse item) {}
