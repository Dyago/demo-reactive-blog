package pe.com.bootcamp.demoreactiveblog.routes;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.com.bootcamp.demoreactiveblog.handlers.AuthorHandler;
import pe.com.bootcamp.demoreactiveblog.handlers.BlogHandler;
import pe.com.bootcamp.demoreactiveblog.handlers.PostHandler;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> blogRoutes(BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(GET(""), blogHandler::findAll)
                        .andRoute(GET("/{id}"), blogHandler::findById)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), blogHandler::save)
                        .andRoute(DELETE("/{id}"), blogHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorHandler authorHandler){
        return RouterFunctions.nest(RequestPredicates.path("/authors"),
                RouterFunctions
                .route(GET(""), authorHandler::findAll)
                .andRoute(GET("/query"), authorHandler::findByQuery)
                .andRoute(GET("/{id}"), authorHandler::findById)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),authorHandler::save)
                .andRoute(DELETE("/{id}"), authorHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes(PostHandler postHandler){
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions.route(GET(""), postHandler::findAll)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),postHandler::save)
                .andRoute(PATCH("{id}/publish"), postHandler::publish)
                .andRoute(POST("{id}/comment").and(accept(APPLICATION_JSON)),postHandler::comment)
                .andRoute(POST("{id}/reaction").and(accept(APPLICATION_JSON)),postHandler::reaction)
                );
    }
    
}
