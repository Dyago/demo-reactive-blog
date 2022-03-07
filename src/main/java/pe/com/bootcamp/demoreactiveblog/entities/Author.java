package pe.com.bootcamp.demoreactiveblog.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="authors")
public class Author {
    @Id
    private String authorId;
    private String name;
    private String email;
    private String phone;
    private Date birthDate;

}
