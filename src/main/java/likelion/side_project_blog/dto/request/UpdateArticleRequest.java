package likelion.side_project_blog.dto.request;

import lombok.Getter;

@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    private String password;
}
