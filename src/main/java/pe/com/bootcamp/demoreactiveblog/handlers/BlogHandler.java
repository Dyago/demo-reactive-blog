package pe.com.bootcamp.demoreactiveblog.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.com.bootcamp.demoreactiveblog.entities.Blog;
import pe.com.bootcamp.demoreactiveblog.services.BlogService;
import reactor.core.publisher.Mono;

@Component
public class BlogHandler {
    @Autowired
    private BlogService blogService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(blogService.findAll(), Blog.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return this.blogService.findById(request.pathVariable("id"))
                .flatMap(blog -> ServerResponse.ok().body(Mono.just(blog), Blog.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Blog.class)
                .flatMap(blog -> this.blogService.save(blog))
                .flatMap(blog -> ServerResponse.ok().body(Mono.just(blog), Blog.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String userId = request.pathVariable("id");
        if (!isValidId(userId)) return badRequest().build();

        return this.blogService.delete(request.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }


    public boolean isValidId(String id) {
        return id != null && id.length() > 5;
    }


}
