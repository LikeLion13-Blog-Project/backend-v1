package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id=comment.getId();
        this.content=comment.getContent();
        this.author=comment.getAuthor();
        this.createdAt=comment.getCreatedAt();
    }
}
