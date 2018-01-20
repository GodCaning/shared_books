package com.xust.wtc.Service.book;

import com.xust.wtc.Entity.Comment;
import com.xust.wtc.Entity.DisplayComment;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2018/1/10.
 */
public interface CommentService {

    List<DisplayComment> findCommentByBookId(int bookId);

    Result deleteCommentById(int id);

    Result addComment(Comment comment);
}
