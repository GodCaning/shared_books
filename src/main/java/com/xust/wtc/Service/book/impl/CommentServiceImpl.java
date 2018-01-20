package com.xust.wtc.Service.book.impl;

import com.xust.wtc.Dao.book.CommentMapper;
import com.xust.wtc.Entity.Comment;
import com.xust.wtc.Entity.DisplayComment;
import com.xust.wtc.Entity.Reply;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spirit on 2018/1/10.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据书籍ID获取评论信息
     * @param bookId
     */
    @Override
    public List<DisplayComment> findCommentByBookId(int bookId) {
        //首先获取此书籍所有评论
        List<Comment> commentList = commentMapper.findCommentByBookId(bookId);
        //把所有评论分为 ：评论和回复
        List<DisplayComment> displayCommentList = new ArrayList<>();
        List<Reply> replyList = new ArrayList<>();
        //所有显示评论的构建
        for (Comment c : commentList) {
//            Instant i = Instant.ofEpochMilli(c.getTimestamp().getTime());
//            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(i);
            String time = LocalDateTime.ofEpochSecond(c.getTimestamp().getTime()/1000, 0, ZoneOffset.ofHours(8))
                    .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
            if (c.getCommentType() == 1) {
                DisplayComment displayComment =
                        new DisplayComment(c.getId(), c.getBookId(), c.getCommentPersonId(), c.getCommentPersonName(), c.getParentId(),
                                c.getContent(), time, new ArrayList<>());
                displayCommentList.add(displayComment);
            } else if(c.getCommentType() == 2) {
                Reply reply = new Reply(c.getId(), c.getBookId(), c.getCommentPersonId(), c.getCommentPersonName(), c.getParentId(),
                                c.getContent(), time, new ArrayList<>());
                replyList.add(reply);
            }
        }
        //给显示评论添加直接回复
        for (DisplayComment displayComment : displayCommentList) {
            for (Reply reply : replyList) {
                if (reply.getParentId() == displayComment.getId()) {
                    displayComment.getReplies().add(reply);
                }
            }
        }
        //给回复寻找下级回复
        for (Reply reply1 : replyList) {
            for (Reply reply2 : replyList) {
                if (reply1.getId() == reply2.getParentId()) {
                    reply1.getReplies().add(reply2);
                }
            }
        }
        return displayCommentList;
    }

    /**
     * 删除一条评论或者回复
     * @param id
     * @return
     */
    @Override
    public Result deleteCommentById(int id) {
        Result result = new Result();
        if (commentMapper.deleteCommentById(id) > 0) {
            result.setStatus(1);
            result.setContent("删除成功");
        } else {
            result.setStatus(0);
            result.setContent("删除失败");
        }
        return result;
    }

    /**
     * 增加一条评论或者回复
     * @param comment
     * @return
     */
    @Override
    public Result addComment(Comment comment) {
        Result result = new Result();
        if (commentMapper.addComment(comment) > 0) {
            result.setStatus(1);
            result.setContent("增加成功");
        } else {
            result.setStatus(0);
            result.setContent("增加失败");
        }
        return result;
    }

    public static void a(List<Comment> commentList) {
        //把所有评论分为 ：评论和回复
        List<DisplayComment> displayCommentList = new ArrayList<>();
        List<Reply> replyList = new ArrayList<>();
        //所有显示评论的构建
        for (Comment c : commentList) {
//            Instant i = Instant.ofEpochMilli(l);
            String time = LocalDateTime.ofEpochSecond(c.getTimestamp().getTime()/1000, 0, ZoneOffset.ofHours(8))
                    .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
            System.out.println(time);
//            String time1 = DateTimeFormatter.ofPattern("HH:mm:ss").format(Instant.now());
//            System.out.println("->"+time1);
            if (c.getCommentType() == 1) {
                DisplayComment displayComment =
                        new DisplayComment(c.getId(), c.getBookId(), c.getCommentPersonId(), c.getCommentPersonName(), c.getParentId(),
                                c.getContent(), time, new ArrayList<>());
                displayCommentList.add(displayComment);
            } else if(c.getCommentType() == 2) {
                Reply reply = new Reply(c.getId(), c.getBookId(), c.getCommentPersonId(), c.getCommentPersonName(), c.getParentId(),
                        c.getContent(), time, new ArrayList<>());
                replyList.add(reply);
            }
        }
        //给显示评论添加直接回复
        for (DisplayComment displayComment : displayCommentList) {
            for (Reply reply : replyList) {
                if (reply.getParentId() == displayComment.getId()) {
                    displayComment.getReplies().add(reply);
                }
            }
        }
        //给回复寻找下级回复
        for (Reply reply1 : replyList) {
            for (Reply reply2 : replyList) {
                if (reply1.getId() == reply2.getParentId()) {
                    reply1.getReplies().add(reply2);
                }
            }
        }

        for (DisplayComment displayComment : displayCommentList) {
            System.out.println(displayComment);
        }
    }

    public static void main(String[] args) {
        List<Comment> commentList = new ArrayList<>();
        Comment comment1 = new Comment(1, 1, 1, "wtc1",
                0, 1, "wtc1",
                new Timestamp(System.currentTimeMillis()));
        Comment comment2 = new Comment(2, 1, 2, "wtc2",
                0, 1, "wtc2",
                new Timestamp(System.currentTimeMillis()));
        Comment comment3 = new Comment(3, 1, 3, "wtc3",
                0, 1, "wtc3",
                new Timestamp(System.currentTimeMillis()));
        Comment comment4 = new Comment(4, 1, 4, "wtc4",
                1, 2, "wtc4",
                new Timestamp(System.currentTimeMillis()));
        Comment comment5 = new Comment(5, 1, 5, "wtc5",
                1, 2, "wtc5",
                new Timestamp(System.currentTimeMillis()));
        Comment comment6 = new Comment(6, 1, 6, "wtc6",
                4, 2, "wtc6",
                new Timestamp(System.currentTimeMillis()));
        Comment comment7 = new Comment(7, 1, 7, "wtc7",
                3, 2, "wtc7",
                new Timestamp(System.currentTimeMillis()));
        Comment comment8 = new Comment(8, 1, 8, "wtc8",
                2, 2, "wtc8",
                new Timestamp(System.currentTimeMillis()));
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        commentList.add(comment5);
        commentList.add(comment6);
        commentList.add(comment7);
        commentList.add(comment8);
        a(commentList);
    }

}
