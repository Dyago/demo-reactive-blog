package pe.com.bootcamp.demoreactiveblog.services.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.demoreactiveblog.entities.Comment;
import pe.com.bootcamp.demoreactiveblog.entities.Post;
import pe.com.bootcamp.demoreactiveblog.entities.Reaction;
import pe.com.bootcamp.demoreactiveblog.exceptions.BusinessException;
import pe.com.bootcamp.demoreactiveblog.repositories.BlogRepository;
import pe.com.bootcamp.demoreactiveblog.repositories.PostRepository;
import pe.com.bootcamp.demoreactiveblog.services.PostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Mono<Post> save(Post post) {
		return blogRepository.findById(post.getBlogId())
				.switchIfEmpty(Mono.error(new BusinessException(HttpStatus.BAD_REQUEST,
						"El codigo de blog ingresado no se encuentra registrado")))
				.flatMap(b -> {
					return "ACTIVO".equals(b.getStatus()) ? postRepository.save(post)
							: Mono.error(new BusinessException(HttpStatus.CONFLICT,
									"El blog no se encuentra en estado ACTIVO"));
				});
	}

	@Override
	public Flux<Post> findAll() {
		return this.postRepository.findAll();
	}

	@Override
	public Mono<Post> publish(String id) {
		return postRepository.findById(id)
				.switchIfEmpty(Mono.error(new BusinessException(HttpStatus.BAD_REQUEST, "El id ingresado no existe")))
				.flatMap(p -> {
					return postRepository.existsByPublishDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
							.flatMap(b -> {
								if (!b) {
									p.setStatus("PUBLICADO");
									p.setPublishDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
									return postRepository.save(p);
								} else {
									return Mono.error(new BusinessException(HttpStatus.CONFLICT,
											"No se puede publicar un post en el mismo dia"));
								}
							});
				});
	}

	@Override
	public Mono<Post> comment(String id, Comment comment) {
		return postRepository.findById(id)
				.switchIfEmpty(Mono
						.error(new BusinessException(HttpStatus.BAD_REQUEST, "El codigo de post ingresado no se encuentra registrado")))
				.flatMap(p -> {
					if ("BORRADOR".equals(p.getStatus())) {
						return Mono.error(new BusinessException(HttpStatus.CONFLICT,
					"Solo se puede registrar comentarios en un post publicado"));
					} else {
						comment.setCreateDate(new Date());
						p.getComments().add(comment);
						return postRepository.save(p);
					}
				});
	}
	
	@Override
	public Mono<Post> reaction(String id, Reaction reaction) {
		return postRepository.findById(id)
				.switchIfEmpty(Mono
						.error(new BusinessException(HttpStatus.BAD_REQUEST, "El codigo de post ingresado no se encuentra registrado")))
				.flatMap(p -> {
					if (p.getReactions().stream().anyMatch(str -> str.getUserId().equals(reaction.getUserId()))) {
						return Mono.error(new BusinessException(HttpStatus.CONFLICT, "Usuario ya registro una reacion"));
					};
					reaction.setCreateDate(new Date());
					p.getReactions().add(reaction);
					return postRepository.save(p);
				});
	}
}
