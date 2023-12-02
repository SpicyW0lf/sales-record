package com.example.salesrecord.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {
    private UUID id;
    private LocalDate date;
    private double total;
    private User user;
}
