package pe.com.bootcamp.demoreactiveblog.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import pe.com.bootcamp.demoreactiveblog.entities.Post;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
	
//	@Query("select case when count(p)> 0 then true else false end from Post p where CAST(p.publishDate AS date) = CAST(:publishDate AS date)")
//	Mono<Boolean> existsPostPublished(@Param("publishDate") Date publishDate);
	
//	@Query(value ="{publishDate: ?0}", count=true)  
//	Mono<Integer> getPostsCountByPublishDate(Date publishDate);
	
	Mono<Boolean> existsByPublishDate(String publishDate);
}
