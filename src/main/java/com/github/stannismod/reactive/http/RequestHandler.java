package com.github.stannismod.reactive.http;

import com.github.stannismod.reactive.dao.StoreService;
import com.github.stannismod.reactive.entity.Currency;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.List;

public class RequestHandler {

    private final StoreService service;

    public RequestHandler(StoreService service) {
        this.service = service;
    }

    public Observable<String> getResponse(HttpServerRequest<ByteBuf> req) {
        long id;

        switch (req.getDecodedPath()) {
            case "/products":
            case "/register":
            case "/add-product":
                 id = Long.parseLong(getQueryParam(req, "id"));
                 break;
            default:
                return Observable.just(req.getDecodedPath());
        }

        switch (req.getDecodedPath()) {
            case "/products":
                return getProducts(id);
            case "/register":
                return register(id, req);
            case "/add-product":
                return addProduct(id, req);
            default:
                return Observable.just(req.getDecodedPath());
        }
    }

    private Observable<String> getProducts(long id) {
        return service.getLocalProductsForUser(id);
    }

    private Observable<String> register(long id, HttpServerRequest<ByteBuf> req) {
        Currency currency = Currency.valueOf(getQueryParam(req, "currency").toUpperCase());
        return service.addUser(id, currency);
    }

    private Observable<String> addProduct(long id, HttpServerRequest<ByteBuf> req) {
        long price = Long.parseLong(getQueryParam(req, "price"));
        String name = getQueryParam(req, "name");
        return service.addProduct(id, price, name);
    }

    private String getQueryParam(HttpServerRequest<ByteBuf> req, String key) {
        List<String> values = req.getQueryParameters().get(key);
        if (values.isEmpty()) {
            throw new UsernameException("No username provided");
        }
        return values.get(0);
    }
}
