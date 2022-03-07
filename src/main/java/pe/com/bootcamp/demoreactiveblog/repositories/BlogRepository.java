package pe.com.bootcamp.demoreactiveblog.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.demoreactiveblog.entities.Blog;
import reactor.core.publisher.Flux;


@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
	
	Flux<Blog> findByAuthorId(String authorId);
	
}