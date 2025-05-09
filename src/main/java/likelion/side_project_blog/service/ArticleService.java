package likelion.side_project_blog.service;

import likelion.side_project_blog.domain.Article;
import likelion.side_project_blog.dto.request.AddArticleRequest;
import likelion.side_project_blog.dto.request.DeleteRequest;
import likelion.side_project_blog.dto.request.UpdateArticleRequest;
import likelion.side_project_blog.dto.response.ArticleResponse;
import likelion.side_project_blog.dto.response.CommentResponse;
import likelion.side_project_blog.exception.ArticleNotFoundException;
import likelion.side_project_blog.exception.PermissionDeniedException;
import likelion.side_project_blog.repository.ArticleRepository;
import likelion.side_project_blog.repository.CommentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    //글 추가
    public ArticleResponse addArticle(AddArticleRequest request){

        /*1. Article 객체 생성*/
        //Option1. 생성자 사용
//        Article article=new Article(request.getTitle(), request.getContent(), request.getAuthor(), request.getPassword());
        //Option2. 빌더 사용
        Article article=Article.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .author(request.getAuthor())
                        .password(request.getPassword())
                        .build();

        /*2. 레포지토리에 저장*/
        articleRepository.save(article);

        /*3. ArticleResponse 생성하여 반환*/
        //Option1. 직접 생성
//        return ArticleResponse.builder()
//                .id(article.getId())
//                .title(article.getTitle())
//                .content(article.getContent())
//                .author(article.getAuthor())
//                .createdAt(article.getCreatedAt())
//                .build();

        //Option2. DTO에 static 메서드 정의하여 사용
        return ArticleResponse.of(article);

    }


    //전체 글 조회
    public List<ArticleResponse> getAllArticles(){
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(article -> new ArticleResponse(article,getCommentList(article)))
                .toList();

    }


    //단일 글 조회
    public ArticleResponse getArticle(Long id){
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));
        List<CommentResponse> comments=getCommentList(article);
        return new ArticleResponse(article,comments);
    }


    //글 삭제
    public void deleteArticle(Long id, DeleteRequest request){
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));
        if(!article.getPassword().equals(request.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 삭제 권한이 없습니다.");
        }
        articleRepository.deleteById(id);

    }

    //글 수정
    public void updateArticle(Long id, UpdateArticleRequest request){
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));
        if(!article.getPassword().equals(request.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 수정 권한이 없습니다.");
        }
        article.update(request.getTitle(),request.getContent());
        articleRepository.save(article);

    }


    //댓글 가져오기
    private List<CommentResponse> getCommentList(Article article){
        return commentRepository.findByArticle(article).stream()
                .map(comment->new CommentResponse(comment))
                .toList();
    }


}
