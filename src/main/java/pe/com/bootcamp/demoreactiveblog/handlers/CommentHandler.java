package pe.com.bootcamp.demoreactiveblog.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.com.bootcamp.demoreactiveblog.entities.Post;
import pe.com.bootcamp.demoreactiveblog.services.PostService;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {

    @Autowired
    private PostService postService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Post.class)
                .flatMap(post -> this.postService.save(post))
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(postService.findAll(), Post.class);
    }
    
    public Mono<ServerResponse> publish(ServerRequest serverRequest) {
    	var id = serverRequest.pathVariable("id");
        return ok().contentType(APPLICATION_JSON)
                .body(postService.publish(id), Post.class);
    }
}
