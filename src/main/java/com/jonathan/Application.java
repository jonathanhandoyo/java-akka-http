package com.jonathan;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class Application extends AllDirectives {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Application app = new Application();

        final Flow<HttpRequest, HttpResponse, NotUsed> flow = app.route().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(flow, ConnectHttp.toHost("localhost", 8080), materializer);

        log.info("Server online at http://localhost:8080\nPress RETURN to stop.");
        System.in.read();

        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private Route route() {
        return route(
                path("hello", () ->
                        route(
                                get(() -> complete("You just did a GET")),
                                post(() -> complete("You just did a POST")),
                                put(() -> complete("You just did a PUT"))
                        )
                )
        );
    }

    /*
    TODO:
    1. how to capture path variable
    2. how to respond with json entity (with full header)
    3. how to consume json entity (with full header)
    4. how to capture request parameter
     */
}
