package pe.com.bootcamp.demoreactiveblog.services;

import pe.com.bootcamp.demoreactiveblog.entities.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> findById(String id);
    Mono<Boolean> existsByEmail(String email);
    Flux<Author> findByEmail(String email);
    Flux<Author> findByName(String name);
    Flux<Author> findAll();
    Mono<Author> save(Author author);
    Mono<Void> delete(String id);
    
}
