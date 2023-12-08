package com.example.salesrecord.services;

import com.example.salesrecord.DTO.PurchaseItemDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void startNewPurchase(String name) throws AlreadyExistsException {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with such username is not found"));
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

    @Transactional
    public void stopPurchase(String name) throws NotStartedException {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with such name is not found"));
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

    @Transactional
    public void addItem(String name, PurchaseItemDto item) throws NotStartedException {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with such name is not found"));
        Purchase purchase = purchaseRepository.findCurrentPurchase(user.getId())
                .orElseThrow(() -> new NotStartedException("Purchase is not started"));
        Product product = productRepository.findProductByProductCode(item.getProductCode())
                .orElseThrow(() -> new NotFoundException("Product doesnt exists"));
        Optional<PurchaseItem> purchaseItem = purchaseItemRepository.findByPurchaseIdAndProductId(purchase.getId(), product.getId());
        if (purchaseItem.isPresent()) {
            purchaseItemRepository.updateItemQty(
                    purchaseItem.get().getQty() + item.getQty(),
                    purchase.getId(),
                    product.getId()
            );
        } else {
            purchaseItemRepository.save(
                    purchase.getId(),
                    product.getId(),
                    item.getQty()
            );
        }
    }

    @Transactional
    public void cancelCurrentPurchase(String name) throws NotStartedException {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with such name is not found"));
        Purchase purchase = purchaseRepository.findCurrentPurchase(user.getId())
                .orElseThrow(() -> new NotStartedException("Purchase is not started"));
        purchaseItemRepository.deleteByPurchaseId(purchase.getId());
        purchaseRepository.delete(purchase);
    }

    private double countTotal(List<PurchaseItem> items) {
        return items.stream()
                .map(item -> item.getQty() * productRepository.findById(item.getProduct().getId()).getPrice())
                .reduce(0.0, Double::sum);
    }

    private void updateProducts(List<PurchaseItem> items) {
        items.forEach(item -> {
                    if (item.getProduct().getQty() >= item.getQty()) {
                        productRepository.updateQty(
                                item.getProduct().getQty() - item.getQty(),
                                item.getProduct().getId()
                        );
                    } else {
                        item.setQty(item.getProduct().getQty());
                        productRepository.updateQty(
                                0,
                                item.getProduct().getId()
                        );
                    }
                }
        );
    }

    @Transactional
    public void refundItems(UUID id, PurchaseItemDto purchaseItemDto) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Purchase with such id is not found"));
        Product product = productRepository.findProductByProductCode(purchaseItemDto.getProductCode())
                .orElseThrow(() -> new NotFoundException("Product is not found"));
        PurchaseItem item = purchaseItemRepository.findByPurchaseIdAndProductId(purchase.getId(), product.getId())
                .orElseThrow(() -> new NotFoundException("Product item is not found"));

        if (item.getQty() <= purchaseItemDto.getQty()) {
            purchaseItemDto.setQty(item.getQty());
            purchaseItemRepository.deleteByPurchaseIdAndProductId(purchase.getId(), product.getId());
        } else {
            purchaseItemRepository.updateItemQty(item.getQty() - purchaseItemDto.getQty(), purchase.getId(), product.getId());
        }
        purchase.setTotal(purchase.getTotal() - purchaseItemDto.getQty() * product.getPrice());
        purchaseRepository.updateTotal(purchase);
    }

    public Integer countTotalForToday(LocalDate now) {
        return purchaseRepository.countTodayTotal(now);
    }
}
