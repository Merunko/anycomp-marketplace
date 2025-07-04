package com.merunkocasey.anycomp.marketplace.dto;

import java.util.List;

public record BuyerResponse(Long id, String name, String email, List<PurchaseResponse> purchasedItems) {}