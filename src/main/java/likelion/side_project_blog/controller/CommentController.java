package likelion.side_project_blog.controller;

import likelion.side_project_blog.dto.request.AddCommentRequest;
import likelion.side_project_blog.dto.request.DeleteRequest;
import likelion.side_project_blog.dto.response.ApiResponse;
import likelion.side_project_blog.dto.response.CommentResponse;
import likelion.side_project_blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        CommentResponse response=commentService.addComment(articleId,request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new ApiResponse(true,201,"댓글 등록 성공",response)));

    }


    /*댓글 삭제*/
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable long commentId,
                                                     @RequestBody DeleteRequest request) {
        commentService.deleteComment(commentId, request);
        return ResponseEntity.ok(new ApiResponse(true, 204, "댓글 삭제 성공"));
    }






}
