package com.example.salesrecord.services;

import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotFoundException;
import com.example.salesrecord.exception.NotStartedException;
import com.example.salesrecord.models.Purchase;
import com.example.salesrecord.models.Product;
import com.example.salesrecord.models.PurchaseItem;
import com.example.salesrecord.models.User;
import com.example.salesrecord.repositories.ProductRepository;
import com.example.salesrecord.repositories.PurchaseItemRepository;
import com.example.salesrecord.repositories.PurchaseRepository;
import com.example.salesrecord.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    public List<PurchaseItem> findItemById(UUID id) {
        return purchaseItemRepository.findByPurchaseId(id);
    }

    public void startNewPurchase(String name) throws AlreadyExistsException {
        User user = userRepository.findByUsername(name);
        Optional<Purchase> currentPurchase = purchaseRepository.findCurrentPurchase(user.getId());
        if (currentPurchase.isPresent()) {
            throw new AlreadyExistsException("You already have started purchase");
        }
        purchaseRepository.save(new Purchase(
                UUID.randomUUID(),
                LocalDate.now(),
                0,
                user
        ));
    }

    public void stopPurchase(String name) throws NotStartedException {
        User user = userRepository.findByUsername(name);
        Purchase purchase = purchaseRepository.findCurrentPurchase(user.getId())
                .orElseThrow(() -> new NotStartedException("Purchase is not started"));
        List<PurchaseItem> purchaseItems = purchaseItemRepository.findByPurchaseId(purchase.getId());
        if (purchaseItems.isEmpty()) {
            purchaseRepository.delete(purchase);
            return;
        }

        double total = countTotal(purchaseItems);
        updateProducts(purchaseItems);
        purchase.setTotal(total);
        purchaseRepository.updateTotal(purchase);
    }

    private double countTotal(List<PurchaseItem> items) {
        return items.stream()
                .map(item -> item.getQty() * productRepository.findById(item.getProduct().getId()).getPrice())
                .reduce(0.0, Double::sum);
    }

    private void updateProducts(List<PurchaseItem> items) {
        items.forEach(item -> productRepository.updateQty(
                        item.getProduct().getQty() - item.getQty(),
                        item.getProduct().getId()
                )
        );
    }
}
