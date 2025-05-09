package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder @AllArgsConstructor
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final int totalComments;
    //댓글목록 추가
    private final List<CommentResponse> comments;

    public ArticleResponse(Article article, List<CommentResponse> comments){
        this.id= article.getId();
        this.title= article.getTitle();;
        this.content= article.getContent();
        this.author=article.getAuthor();
        this.createdAt=article.getCreatedAt();
        this.comments=comments;
        this.totalComments=comments.size();
    }

    //유용
    public static ArticleResponse of(Article article){
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .build();
    }


}
