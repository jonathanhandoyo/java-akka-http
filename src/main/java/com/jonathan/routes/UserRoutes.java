package com.jonathan.routes;

import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import lombok.extern.slf4j.Slf4j;

import static akka.http.javadsl.server.PathMatchers.*;

@Slf4j
public class UserRoutes extends AllDirectives {

    public Route routes() {
        return route(
                pathPrefix("users", () ->
                        route(
                                pathEnd(this::users),
                                pathPrefix(longSegment(), this::usersWithId)
                        )
                )
        );
    }

    private Route users() {
        return get(() -> route(
                parameterList((list) -> {
                    return complete(StatusCodes.OK);
                })
//                complete(StatusCodes.OK)
        ));
    }

    private Route usersWithId(Long userId) {
        return route(
                pathEnd(() -> route(
                        get(() -> complete(StatusCodes.OK)),
                        post(() -> complete(StatusCodes.OK)),
                        delete(() -> complete(StatusCodes.OK)),
                        put(() -> complete(StatusCodes.OK))
                )),
                pathPrefix("portfolios", () -> portfolios(userId)),
                pathPrefix("tokens", () -> tokens(userId))
        );
    }

    private Route portfolios(Long userId) {
        return complete(StatusCodes.OK);
    }

    private Route tokens(Long userId) {
        return complete(StatusCodes.OK);
    }
}
