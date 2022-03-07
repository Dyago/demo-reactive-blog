package pe.com.bootcamp.demoreactiveblog.services.impl;


import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.demoreactiveblog.entities.Blog;
import pe.com.bootcamp.demoreactiveblog.exceptions.BusinessException;
import pe.com.bootcamp.demoreactiveblog.repositories.AuthorRepository;
import pe.com.bootcamp.demoreactiveblog.repositories.BlogRepository;
import pe.com.bootcamp.demoreactiveblog.services.BlogService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Mono<Blog> findById(String id) {
        return  this.blogRepository.findById(id);
    }

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Mono<Blog> save(Blog blog) {
    	
    	return authorRepository.findById(blog.getAuthorId())
    			.switchIfEmpty(Mono.error(new BusinessException(HttpStatus.BAD_REQUEST,
						"El codigo de autor ingresado no se encuentra registrado")))
    	.flatMap(a -> {
    		return Period.between(
    				Instant.ofEpochMilli(a.getBirthDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
    				LocalDate.now()).getYears() < 18 ? Mono.empty() : Mono.just(a);
    	})
    	.switchIfEmpty(Mono.error(new BusinessException(HttpStatus.CONFLICT, "El autor es menor de edad")))
    	.flatMap(a -> {
    		return blogRepository.findByAuthorId(blog.getAuthorId())
			.count()
			.flatMap(c -> {
				return c < 3 ? blogRepository.save(blog) : Mono.error(new BusinessException(HttpStatus.CONFLICT,
						"El autor ya cuenta con 3 blogs registrados"));
			});
    	});
    	
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.blogRepository.findById(id)
                .doOnNext(b->{
                    System.out.println("doOnNext b = " + b);
                })
                .flatMap(blog-> this.blogRepository.delete(blog));

    }
}
