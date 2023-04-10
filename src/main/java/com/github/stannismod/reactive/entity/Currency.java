package com.github.stannismod.reactive.entity;

public enum Currency {
    USD(1),
    EUR(1.09),
    RUB(0.0081);

    public final double conversion;

    Currency(double conversion) {
        this.conversion = conversion;
    }

    public double toUniversal(double value) {
        return value * conversion;
    }

    public double fromUniversal(double value) {
        return value / conversion;
    }
}
