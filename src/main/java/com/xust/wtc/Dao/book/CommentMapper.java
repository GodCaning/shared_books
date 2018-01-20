package com.xust.wtc.Dao.book;

import com.xust.wtc.Entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2018/1/10.
 */
@Repository
public interface CommentMapper {

    /**
     * 根据书籍id获取评论信息
     * @param id
     * @return
     */
    List<Comment> findCommentByBookId(@Param("id") int id);

    /**
     * 删除ID的评论或回复
     * @param id
     * @return
     */
    int deleteCommentById(@Param("id") int id);

    /**
     * 增加一条评论或者回复
     * @param comment
     * @return
     */
    int addComment(Comment comment);
}
