package com.github.stannismod.reactive.entity;

import org.bson.Document;

public class Product {
    private final String name;
    private final double price;

    public Product(Document document) {
        this(document.getString("name"), document.getDouble("price"));
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, long price, Currency currency) {
        this(name, currency.toUniversal(price));
    }

    public Document toDocument() {
        return new Document()
                .append("name", name)
                .append("price", price);
    }

    public String toString(Currency currency) {
        return String.format("%s %.1f %s%n", name, currency.fromUniversal(price), currency);
    }
}
