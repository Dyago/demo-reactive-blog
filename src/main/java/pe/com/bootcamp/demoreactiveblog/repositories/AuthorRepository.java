package pe.com.bootcamp.demoreactiveblog.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.demoreactiveblog.entities.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author,String> {

    Flux<Author> findByEmail(String email);

    Flux<Author> findByName(String name);

    Mono<Boolean>existsByEmail(String email);
}
