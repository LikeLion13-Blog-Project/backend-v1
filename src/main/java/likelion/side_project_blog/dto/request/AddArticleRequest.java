package likelion.side_project_blog.dto.request;

import likelion.side_project_blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
    private String password;

    public Article toEntity(){
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .password(password)
                .build();
    }
}
