package likelion.side_project_blog.service;

import jakarta.persistence.EntityNotFoundException;
import likelion.side_project_blog.domain.Article;
import likelion.side_project_blog.domain.Comment;
import likelion.side_project_blog.dto.request.AddCommentRequest;
import likelion.side_project_blog.exception.ArticleNotFoundException;
import likelion.side_project_blog.exception.CommentNotFoundException;
import likelion.side_project_blog.repository.ArticleRepository;
import likelion.side_project_blog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    /*댓글 작성*/
    public void addComment(long articleId, AddCommentRequest request) {
        Optional<Article> article=articleRepository.findById(articleId);
        if(article.isEmpty()){
            throw new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다.");
        }else{
            commentRepository.save(Comment.builder()
                    .article(article.get())
                    .content(request.getContent())
                    .createdAt(LocalDateTime.now())
                    .build()
            );
        }
    }


    /*댓글 삭제*/
    public void deleteComment(long commentId) {
        boolean isCommentExist = commentRepository.existsById(commentId);
        if(!isCommentExist){
            throw new CommentNotFoundException("해당 ID의 댓글을 찾을 수 없습니다.");
        }else{
            commentRepository.deleteById(commentId);
        }
    }




}
