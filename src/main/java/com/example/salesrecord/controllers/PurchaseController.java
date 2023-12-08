package com.example.salesrecord.controllers;

import com.example.salesrecord.DTO.PurchaseItemDto;
import com.example.salesrecord.DTO.ResponseDto;
import com.example.salesrecord.exception.AlreadyExistsException;
import com.example.salesrecord.exception.NotStartedException;
import com.example.salesrecord.models.Purchase;
import com.example.salesrecord.models.PurchaseItem;
import com.example.salesrecord.services.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Управление покупками", description = "Операции над покупками")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/purchase/start")
    @Operation(summary = "Начать покупку")
    public ResponseEntity<ResponseDto> startPurchase(Principal user) throws AlreadyExistsException {
        purchaseService.startNewPurchase(user.getName());

        return ResponseEntity.ok(new ResponseDto("Purchase successfully started"));
    }

    @PostMapping("/purchase/stop")
    @Operation(summary = "Закончить покупку")
    public ResponseEntity<ResponseDto> stopPurchase(Principal user) throws NotStartedException {
        purchaseService.stopPurchase(user.getName());

        return ResponseEntity.ok(new ResponseDto("Current purchase is stopped"));
    }

    @PostMapping("/purchase/add")
    @Operation(summary = "Добавить товар")
    public ResponseEntity<ResponseDto> addPurchaseItem(Principal user, @RequestBody PurchaseItemDto item) throws NotStartedException {
        purchaseService.addItem(user.getName(), item);

        return ResponseEntity.ok(new ResponseDto(item.getQty() + " of " + item.getProductCode() + " was added successfully"));
    }

    @PostMapping("/purchase/cancel")
    @Operation(summary = "Отменить")
    public ResponseEntity<ResponseDto> cancelPurchase(Principal user) throws NotStartedException {
        purchaseService.cancelCurrentPurchase(user.getName());

        return ResponseEntity.ok(new ResponseDto("Current purchase successfully canceled"));
    }

    @GetMapping("/purchases")
    @Operation(summary = "Просмотреть все покупки")
    public ResponseEntity<List<Purchase>> getPurchases() {
        return ResponseEntity.ok(purchaseService.findAllPurchases());
    }

    @GetMapping("/purchases/{id}")
    @Operation(summary = "Получить товары по покупке", description = "Возвращает товары, которые были приобретены во время покупки")
    public ResponseEntity<List<PurchaseItem>> getPurchasesById(@PathVariable String id) {
        return ResponseEntity.ok(purchaseService.findItemById(UUID.fromString(id)));
    }

    @PostMapping("/purchase/{id}/refund")
    @Operation(summary = "Вернуть товары")
    public ResponseEntity<ResponseDto> refundItems(@PathVariable String id, @RequestBody @Valid PurchaseItemDto purchaseItemDto) {
        purchaseService.refundItems(UUID.fromString(id), purchaseItemDto);

        return ResponseEntity.ok(new ResponseDto("Products refunded successfully"));
    }
}
