package pe.com.bootcamp.demoreactiveblog.entities;

import java.util.ArrayList;
import java.util.List;

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
@Document(value="posts")
public class Post {
	@Id
    private String postId;
    private String blogId;
    private String title;
    private String publishDate;
    private String status;
    private String content;
    private List<Comment> comments = new ArrayList<>();
    private List<Reaction> reactions = new ArrayList<>();
    
    
//    public void addComment(Comment comment) {
//    	if (this.comments == null) {
//			this.comments = new ArrayList<>();
//		}
//    	this.comments.add(comment);
//    }
//    
//    public void addReaction(Reaction reaction) {
//    	if (this.reactions == null) {
//			this.reactions = new ArrayList<>();
//		}
//    	this.reactions.add(reaction);
//    }
}
