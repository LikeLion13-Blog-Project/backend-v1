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
//        Article article=Article.builder()
//                        .title(request.getTitle())
//                        .content(request.getContent())
//                        .author(request.getAuthor())
//                        .password(request.getPassword())
//                        .build();
        //Option3. toEntity
        Article article=request.toEntity();

        /*2. 레포지토리에 저장*/
        articleRepository.save(article);

        /*3. ArticleResponse DTO 생성하여 반환*/
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
        /*1. 데이터베이스에 저장된 전체 게시글 목록 가져오기*/
        List<Article> articleList = articleRepository.findAll();

        /*2. Article -> ArticleResponse : 엔티티를 DTO로 변환*/
        List<ArticleResponse> articleResponseList=articleList.stream()
                .map(article -> new ArticleResponse(article,getCommentList(article)))
                .toList();

        /*3. articleResponseList (DTO 리스트) 반환 */
        return articleResponseList;

    }


    //단일 글 조회
    public ArticleResponse getArticle(Long id){
        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /*2. 해당 게시글에 달려있는 댓글들 가져오기*/
        List<CommentResponse> comments=getCommentList(article);

        /*3. ArticleResponse DTO 생성하여 반환 */
        return new ArticleResponse(article,comments);
    }


    //글 삭제
    public void deleteArticle(Long id, DeleteRequest request){
        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /*2. 비밀번호 일치하는지 확인 : 요청을 보낸 사람이 이 게시글의 삭제권한을 가지고 있는지
            request.getPassword() : 게시글 수정 요청을 보낸 사람이 입력한 비밀번호
            article.getPassword() : 데이터베이스에 저장된 비밀번호 (작성자가 글 쓸때 등록한)
         */
        if(!request.getPassword().equals(article.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 삭제 권한이 없습니다.");
        }

        /*3. 게시글 삭제 */
        articleRepository.deleteById(id);

    }

    //글 수정
    public void updateArticle(Long id, UpdateArticleRequest request){

        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /*2. 비밀번호 일치하는지 확인 : 요청을 보낸 사람이 이 게시글의 수정 권한을 가지고 있는지
            request.getPassword() : 게시글 수정 요청을 보낸 사람이 입력한 비밀번호
            article.getPassword() : 데이터베이스에 저장된 비밀번호 (작성자가 글 쓸때 등록한)
         */
        if(!article.getPassword().equals(request.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 수정 권한이 없습니다.");
        }

        /*3. 게시글 수정 후 저장 */
        article.update(request.getTitle(),request.getContent());
        articleRepository.save(article);

    }


    //특정 게시글에 달려있는 댓글목록 가져오기
    private List<CommentResponse> getCommentList(Article article){
        return commentRepository.findByArticle(article).stream()
                .map(comment->new CommentResponse(comment))
                .toList();
    }


}
