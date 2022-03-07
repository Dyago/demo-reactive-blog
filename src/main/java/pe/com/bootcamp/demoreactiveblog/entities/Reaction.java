package pe.com.bootcamp.demoreactiveblog.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
	
	private String type;
	private Date createDate;
	private String userId;

}
