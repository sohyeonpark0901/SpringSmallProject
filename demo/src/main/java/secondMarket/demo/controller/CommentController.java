package secondMarket.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import secondMarket.demo.domain.Comment;
import secondMarket.demo.domain.Member;
import secondMarket.demo.model.comment.CommentSaveRequest;
import secondMarket.demo.service.CommentService;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("products/{productId}/comment")
    public String commentForm(){
        return "products/comment/put";
    }

    @PostMapping("products/{productId}/comment")
    public String saveComment(@PathVariable Long productId, @ModelAttribute CommentSaveRequest commentSaveRequest, HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");

        long memberId = loginMember.getMemberId();

        if(loginMember == null){
            throw new IllegalStateException("로그인을 해주세요");
        }

        Comment comment = Comment.createComment(commentSaveRequest,memberId,productId);
        commentService.saveComment(comment);
        return "redirect:/products/"+productId;
    }


    @DeleteMapping("/products/{productId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long productId,@PathVariable Long commentId, HttpSession session){
        Member loginMember = (Member) session.getAttribute("memberEmail");
        logger.info(loginMember.toString());
        long memberId = loginMember.getMemberId();
        String memberRole = loginMember.getRole();

        if(loginMember == null){
            throw new IllegalStateException("로그인을 해주세요");
        }

        if(memberRole.equals("admin")){
            boolean result = commentService.adminDeleteComment(commentId,productId);
            if(result == true){
                return "redirect:/products/"+productId+"/"+productId;
            }
            else {
                throw new IllegalStateException("댓글이 삭제되지 않습니다.");
            }
        }

       boolean result = commentService.UserDeleteComment(memberId,commentId,productId);
        if(result == true){
            return "redirect:/products/"+productId+"/"+productId;
        }
        else {
            throw new IllegalStateException("댓글이 삭제되지 않습니다.");
        }

    }
}
