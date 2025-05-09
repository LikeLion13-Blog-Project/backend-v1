package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder @AllArgsConstructor
public class SimpleArticleResponse {

    private final Long id;
    private final String title;
    private final String author;




    public static SimpleArticleResponse of(Article article) {
        return SimpleArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .build();
    }
}
