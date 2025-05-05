package likelion.side_project_blog.dto.response;

import likelion.side_project_blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final int totalComments;
    //댓글목록 추가
    private final List<CommentResponse> comments;

    public ArticleResponse(Article article, List<CommentResponse> comments){
        this.id= article.getId();
        this.title= article.getTitle();;
        this.content= article.getContent();
        this.createdAt=article.getCreatedAt();
        this.comments=comments;
        this.totalComments=comments.size();
    }


}
