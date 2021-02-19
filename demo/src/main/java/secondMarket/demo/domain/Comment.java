package secondMarket.demo.domain;

import lombok.Getter;
import lombok.Setter;
import secondMarket.demo.model.comment.CommentSaveRequest;

@Getter
@Setter
public class Comment {
    private Long commentId;
    private String content;
    private Long memberId;
    private Long productId;

    public static Comment createComment(CommentSaveRequest commentSaveRequest,Long memberId,Long productId){
        Comment comment = new Comment();
        comment.content = commentSaveRequest.getContent();
        comment.memberId = memberId;
        comment.productId = productId;
        return comment;
    }
}
