package com.github.stannismod.reactive.dao;

import com.github.stannismod.reactive.entity.Currency;
import com.github.stannismod.reactive.entity.Product;
import com.github.stannismod.reactive.entity.User;
import rx.Observable;

public class StoreService {

    private final StoreReactiveDao dao;

    public StoreService(StoreReactiveDao dao) {
        this.dao = dao;
    }

    public Observable<String> getLocalProductsForUser(long id) {
        Observable<Currency> currency = dao.getUser(id).map(User::getCurrency);
        Observable<Product> products = dao.getProducts();
        return Observable.combineLatest(currency, products, (cur, product) -> product.toString(cur));
    }

    public Observable<String> addUser(long id, Currency currency) {
        return dao.addUser(new User(id, currency));
    }

    public Observable<String> addProduct(long id, long price, String name) {
        return dao.getUser(id)
                  .map(User::getCurrency)
                  .switchMap(currency -> dao.addProduct(new Product(name, price, currency)));
    }
}
