package com.github.stannismod.reactive;

import com.github.stannismod.reactive.dao.StoreReactiveDao;
import com.github.stannismod.reactive.dao.StoreService;
import com.github.stannismod.reactive.http.RequestHandler;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class Main {

    public static final int SERVER_PORT = 8080;
    public static final int CLIENT_PORT = 9000;

    public static void main(String[] args) {
        try (MongoClient client = createMongoClient()) {
            MongoDatabase db = client.getDatabase("reactive");
            RequestHandler requestHandler = new RequestHandler(new StoreService(new StoreReactiveDao(
                    db.getCollection("users"), db.getCollection("products"))));

            HttpServer
                    .newServer(SERVER_PORT)
                    .start((req, resp) -> {
                        Observable<String> response = requestHandler.getResponse(req);
                        return resp.writeString(response);
                    })
                    .awaitShutdown();
        }
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:" + CLIENT_PORT);
    }
}
