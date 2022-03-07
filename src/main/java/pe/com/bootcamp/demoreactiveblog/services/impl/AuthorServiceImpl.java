package pe.com.bootcamp.demoreactiveblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.demoreactiveblog.entities.Author;
import pe.com.bootcamp.demoreactiveblog.exceptions.BusinessException;
import pe.com.bootcamp.demoreactiveblog.repositories.AuthorRepository;
import pe.com.bootcamp.demoreactiveblog.services.AuthorService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Mono<Author> findById(String id) {
		return this.authorRepository.findById(id);
	}

	@Override
	public Mono<Boolean> existsByEmail(String email) {
		return authorRepository.existsByEmail(email);
	}

	@Override
	public Flux<Author> findByEmail(String email) {
		return this.authorRepository.findByEmail(email);
	}

	@Override
	public Flux<Author> findByName(String name) {
		return this.authorRepository.findByName(name);
	}

	@Override
	public Flux<Author> findAll() {
		return this.authorRepository.findAll();
	}

	@Override
	public Mono<Author> save(Author author) {
		return this.authorRepository.existsByEmail(author.getEmail()).flatMap(exists -> {
			return !exists ? this.authorRepository.save(author)
					: Mono.error(new BusinessException(HttpStatus.BAD_REQUEST, "Author exists"));
		});
	}

	@Override
	public Mono<Void> delete(String id) {
		return this.authorRepository.findById(id)
				.switchIfEmpty(Mono.error(new BusinessException(HttpStatus.NOT_FOUND, "Author no encontrado")))
				.flatMap(author -> {
					return this.authorRepository.delete(author);
				});
	}

}
