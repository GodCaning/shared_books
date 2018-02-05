package com.xust.wtc.Controller.book;

import com.xust.wtc.Entity.Comment;
import com.xust.wtc.Entity.DisplayComment;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.CommentService;
import com.xust.wtc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 评论控制类
 * Created by Spirit on 2018/1/10.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据书籍ID获取所有评论和回复
     * @param bookId
     * @return
     */
    @GetMapping(value = "/findComments/{id}", consumes = "application/json", produces = "application/json")
    public List<DisplayComment> findCommentByBookId(@PathVariable("id") int bookId) {
        return commentService.findCommentByBookId(bookId);
    }

    /**
     * 删除一条评论或者回复
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteComment/{id}", consumes = "application/json", produces = "application/json")
    public Result deleteCommentById(@PathVariable int id) {
        return commentService.deleteCommentById(id);
    }

    /**
     * 增加一条评论或者回复
     * @param comment
     * @return
     */
    @PostMapping(value = "/addComment", consumes = "application/json", produces = "application/json")
    public Result addComment(HttpSession session, @RequestBody Comment comment) {
        comment.setParentId(Utils.getUserId(session.getId()));
        return commentService.addComment(comment);
    }
}
