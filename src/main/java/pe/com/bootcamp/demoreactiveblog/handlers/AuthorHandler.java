package pe.com.bootcamp.demoreactiveblog.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.com.bootcamp.demoreactiveblog.entities.Author;
import pe.com.bootcamp.demoreactiveblog.services.AuthorService;
import reactor.core.publisher.Mono;

@Component
public class AuthorHandler {

	@Autowired
	private AuthorService authorService;

	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(authorService.findAll(), Author.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		var id = request.pathVariable("id");
		return this.authorService.findById(id).flatMap(a -> ServerResponse.ok().body(Mono.just(a), Author.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findByQuery(ServerRequest request) {
		var email = request.queryParam("email").get();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(authorService.findByEmail(email), Author.class);
	}

	public Mono<ServerResponse> save(ServerRequest request) {
		var authorInput = request.bodyToMono(Author.class);
		return authorInput.flatMap(author -> this.authorService.save(author))
				.flatMap(a -> ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(a), Author.class));
	}

	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		return this.authorService.delete(serverRequest.pathVariable("id")).then(ServerResponse.ok().build());

	}
}
