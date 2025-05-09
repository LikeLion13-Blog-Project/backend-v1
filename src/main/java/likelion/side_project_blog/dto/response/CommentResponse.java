package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder @AllArgsConstructor
public class CommentResponse {
    private final Long id;
    private final Long articleId;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id=comment.getId();
        this.content=comment.getContent();
        this.author=comment.getAuthor();
        this.createdAt=comment.getCreatedAt();
        this.articleId=comment.getArticle().getId();
    }

    //유용
    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .articleId(comment.getArticle().getId())
                .content(comment.getAuthor())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();

    }
}
