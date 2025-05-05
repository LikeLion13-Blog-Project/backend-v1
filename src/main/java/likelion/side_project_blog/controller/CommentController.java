package likelion.side_project_blog.controller;

import likelion.side_project_blog.dto.request.AddCommentRequest;
import likelion.side_project_blog.dto.response.ApiResponse;
import likelion.side_project_blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /*댓글 생성*/
    @PostMapping("/{articleId}")
    public ResponseEntity<ApiResponse> addComment(@PathVariable long articleId,
                                  @RequestBody AddCommentRequest request){
        commentService.addComment(articleId,request);
        return ResponseEntity.ok(new ApiResponse(true,200,"댓글 등록 성공"));
    }

    /*댓글 삭제*/
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse(true,200,"댓글 삭제 성공"));
    }





}
