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
