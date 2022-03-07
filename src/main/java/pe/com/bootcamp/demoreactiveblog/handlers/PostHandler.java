package pe.com.bootcamp.demoreactiveblog.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.com.bootcamp.demoreactiveblog.entities.Comment;
import pe.com.bootcamp.demoreactiveblog.entities.Post;
import pe.com.bootcamp.demoreactiveblog.entities.Reaction;
import pe.com.bootcamp.demoreactiveblog.services.PostService;
import reactor.core.publisher.Mono;

@Component
public class PostHandler {

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
    
    public Mono<ServerResponse> comment(ServerRequest serverRequest) {
    	var id = serverRequest.pathVariable("id");
    	var comment = serverRequest.bodyToMono(Comment.class);
    	return comment
                .flatMap(c-> this.postService.comment(id, c))
                .flatMap(a-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(a), Post.class));
    }
    
    public Mono<ServerResponse> reaction(ServerRequest serverRequest) {
    	var id = serverRequest.pathVariable("id");
    	var reaction = serverRequest.bodyToMono(Reaction.class);
    	return reaction
                .flatMap(r-> this.postService.reaction(id, r))
                .flatMap(a-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(a), Post.class));
    }
}
