package com.example.salesrecord.services;

import com.example.salesrecord.repositories.EarningRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TotalCountService {
    private final PurchaseService purchaseService;
    private final EarningRepository earningRepository;

    @Scheduled(cron = "59 23 * * * ?")
    public void countTotal() {
        LocalDate now = LocalDate.now();
        Integer total = purchaseService.countTotalForToday(now);
        earningRepository.insertTotal(now, total);
    }
}
