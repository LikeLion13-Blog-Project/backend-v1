package likelion.side_project_blog.service;

import likelion.side_project_blog.domain.Article;
import likelion.side_project_blog.dto.request.AddArticleRequest;
import likelion.side_project_blog.dto.request.DeleteRequest;
import likelion.side_project_blog.dto.response.ArticleDetailResponse;
import likelion.side_project_blog.dto.response.ArticleResponse;
import likelion.side_project_blog.exception.ArticleNotFoundException;
import likelion.side_project_blog.repository.ArticleRepository;
import likelion.side_project_blog.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;



    @DisplayName("addArticle() : 게시글 생성 성공")
    @Test
    void addArticle() {
        //given
        AddArticleRequest addArticleRequest = new AddArticleRequest(
                "제목입니다",
                "내용입니다",
                "글쓴이입니다",
                "비번입니다1234"
        );

        Article article = Article.builder()
                .id(1L)
                .title("제목입니다")
                .content("내용입니다")
                .author("글쓴이입니다")
                .password("비번입니다1234")
                .createdAt(LocalDateTime.now())
                .commentCount(0)
                .build();

        given(articleRepository.save(any(Article.class))).willReturn(article);

        //when
        ArticleResponse response=articleService.addArticle(addArticleRequest);

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("제목입니다");
        assertThat(response.getContent()).isEqualTo("내용내용");


    }

    @DisplayName("deleteArticle() 테스트 : 게시글 삭제 실패")
    @Test
    void deleteArticle_notFound(){
        //given
        DeleteRequest deleteRequest=new DeleteRequest("비번입니다");
        Long id=999L;
        given(articleRepository.findById(id)).willReturn(Optional.empty());

        //when-then
        assertThatThrownBy(()->articleService.deleteArticle(id,deleteRequest))
                .isInstanceOf(ArticleNotFoundException.class);

        verify(articleRepository, never()).deleteById(anyLong());
    }
}