package com.jonathan.routes;

import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseRoutes extends AllDirectives {
    public Route routes() {
        return complete(StatusCodes.OK);
    }
}
