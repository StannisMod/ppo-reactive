package com.github.stannismod.reactive.entity;

import org.bson.Document;

public class User {
    private final long id;
    private final Currency currency;

    public User(Document document) {
        this(document.getLong("id"), Currency.values()[document.getInteger("currency")]);
    }

    public User(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("currency", currency.ordinal());
    }

    public Currency getCurrency() {
        return currency;
    }
}
