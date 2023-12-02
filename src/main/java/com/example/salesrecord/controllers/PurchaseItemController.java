package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotStartedException;
import com.example.salesrecord.models.PurchaseItem;
import com.example.salesrecord.services.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PurchaseItemController {// Также добавлю сюда ручки для покупки и возврата товаров
    private final PurchaseService purchaseService;

    @PostMapping("/purchase/start")
    public ResponseEntity<ResponseDto> startPurchase(Principal user) throws AlreadyExistsException {
        purchaseService.startNewPurchase("test");

        return ResponseEntity.ok(new ResponseDto("Purchase successfully started"));
    }

    @PostMapping("/purchase/stop")
    public ResponseEntity<ResponseDto> stopPurchase(Principal user) throws NotStartedException {
        purchaseService.stopPurchase("test");

        return ResponseEntity.ok(new ResponseDto("Current purchase is stopped"));
    }

    @GetMapping("/purchase")
    public ResponseEntity<List<PurchaseItem>> getPurchasesById() {
        return ResponseEntity.ok(purchaseService.findItemById(UUID.fromString("6c8ca3a4-9089-11ee-b9d1-0242ac120002")));
    }
}
