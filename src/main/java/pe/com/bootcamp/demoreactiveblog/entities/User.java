package pe.com.bootcamp.demoreactiveblog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String userId;
	private String login;
	private String password;
	private String authorId;
	
}
