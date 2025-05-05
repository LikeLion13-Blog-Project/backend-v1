package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id=comment.getId();
        this.content=comment.getContent();
        this.createdAt=comment.getCreatedAt();
    }
}
