package pe.com.bootcamp.demoreactiveblog.services;

import pe.com.bootcamp.demoreactiveblog.entities.Comment;
import pe.com.bootcamp.demoreactiveblog.entities.Post;
import pe.com.bootcamp.demoreactiveblog.entities.Reaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<Post> save(Post post);
    Flux<Post> findAll();
    Mono<Post> publish(String id);
    Mono<Post> comment(String id, Comment comment);
	Mono<Post> reaction(String id, Reaction reaction);
}
