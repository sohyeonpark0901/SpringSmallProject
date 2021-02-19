package secondMarket.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.Comment;
import secondMarket.demo.repository.CommentRepository;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment){
        Comment saveComment = commentRepository.save(comment);
    }

    public Boolean adminDeleteComment(Long commentId,Long productId){
        int result = commentRepository.AdminDelete(commentId,productId);
        if(result == 0){
            return false;
        }
        return true;
    }
    public Boolean UserDeleteComment(Long memberId,Long commentId,Long productId){
        int result = commentRepository.UserDelete(memberId,commentId,productId);
        if(result == 0){
            return false;
        }
        return true;
    }


}
