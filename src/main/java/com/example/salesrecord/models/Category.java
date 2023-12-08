package com.example.salesrecord.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private String type;

    public static Category of(String name, String type) {
        return new Category(
                UUID.randomUUID(),
                name,
                type
        );
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
