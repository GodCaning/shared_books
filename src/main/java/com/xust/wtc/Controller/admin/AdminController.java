package com.xust.wtc.Controller.admin;

import com.github.pagehelper.PageInfo;
import com.xust.wtc.Dao.logistics.LogisticsMapper;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.admin.BookInfo;
import com.xust.wtc.Entity.admin.LendInfo;
import com.xust.wtc.Entity.chat.Feedback;
import com.xust.wtc.Entity.user.Person;
import com.xust.wtc.Service.admin.AdminService;
import com.xust.wtc.utils.CONSTANT_STATUS;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员页面
 * Created by Spirit on 2018/5/7.
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/adminLo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Result login(@RequestBody() Person person) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token =
                new UsernamePasswordToken(person.getLoginName(), person.getLoginPasswd());

        Result result = new Result();
        try {
            subject.login(token);
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("登录成功");
        } catch (Exception e) {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("帐号密码不正确");
        }
        return result;
    }

    @GetMapping(value = "/bookInfo")
    public String displayBookInfo(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  Model model) {
        PageInfo<BookInfo> bookInfoPageInfo = adminService.displayBookInfo(currentPage, pageSize);
        model.addAttribute("list", bookInfoPageInfo.getList());
        model.addAttribute("currentPage", bookInfoPageInfo.getPageNum());
        model.addAttribute("pages", bookInfoPageInfo.getPages());
        int prePage = bookInfoPageInfo.getPrePage();
        model.addAttribute("prePage", prePage < 1 ? 1 : prePage);
        int nextPage = bookInfoPageInfo.getNextPage();
        model.addAttribute("nextPage", nextPage < 1 ? bookInfoPageInfo.getPages() : nextPage); // 下一页
        return "bookInfo";
    }

    @GetMapping(value = "/stockDelete/{id}")
    public String deleteStock(@PathVariable("id") int stockId,
                              @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              Model model) {
        adminService.deleteStock(stockId);
        PageInfo<BookInfo> bookInfoPageInfo = adminService.displayBookInfo(currentPage, pageSize);
        model.addAttribute("list", bookInfoPageInfo.getList());
        model.addAttribute("currentPage", bookInfoPageInfo.getPageNum()); //当前页
        model.addAttribute("pages", bookInfoPageInfo.getPages()); //总页数
        int prePage = bookInfoPageInfo.getPrePage();
        model.addAttribute("prePage", prePage < 1 ? 1 : prePage); // 上一页
        int nextPage = bookInfoPageInfo.getNextPage();
        model.addAttribute("nextPage", nextPage < 1 ? bookInfoPageInfo.getPages() : nextPage); // 下一页
        return "bookInfo";
    }

    @GetMapping(value = "/feedbackInfo")
    public String displayFeedbackInfo(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  Model model) {
        PageInfo<Feedback> feedbackPageInfo = adminService.displayFeedback(currentPage, pageSize);
        model.addAttribute("list", feedbackPageInfo.getList());
        model.addAttribute("currentPage", feedbackPageInfo.getPageNum());
        model.addAttribute("pages", feedbackPageInfo.getPages());
        int prePage = feedbackPageInfo.getPrePage();
        model.addAttribute("prePage", prePage < 1 ? 1 : prePage);
        int nextPage = feedbackPageInfo.getNextPage();
        model.addAttribute("nextPage", nextPage < 1 ? feedbackPageInfo.getPages() : nextPage); // 下一页
        return "feedbackInfo";
    }

    @GetMapping(value = "/lendInfo")
    public String displayLendInfo(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      Model model) {
        PageInfo<LendInfo> lendInfoPageInfo = adminService.displayLendInfo(currentPage, pageSize);
        model.addAttribute("list", lendInfoPageInfo.getList());
        model.addAttribute("currentPage", lendInfoPageInfo.getPageNum());
        model.addAttribute("pages", lendInfoPageInfo.getPages());
        int prePage = lendInfoPageInfo.getPrePage();
        model.addAttribute("prePage", prePage < 1 ? 1 : prePage);
        int nextPage = lendInfoPageInfo.getNextPage();
        model.addAttribute("nextPage", nextPage < 1 ? lendInfoPageInfo.getPages() : nextPage); // 下一页
        return "lendInfo";
    }

    @GetMapping(value = "/orderDelete/{id}")
    public String deleteOrder(@PathVariable("id") int orderId,
                              @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              Model model) {
        //关单
        adminService.closeOrder(orderId);
        PageInfo<LendInfo> lendInfoPageInfo = adminService.displayLendInfo(currentPage, pageSize);
        model.addAttribute("list", lendInfoPageInfo.getList());
        model.addAttribute("currentPage", lendInfoPageInfo.getPageNum());
        model.addAttribute("pages", lendInfoPageInfo.getPages());
        int prePage = lendInfoPageInfo.getPrePage();
        model.addAttribute("prePage", prePage < 1 ? 1 : prePage);
        int nextPage = lendInfoPageInfo.getNextPage();
        model.addAttribute("nextPage", nextPage < 1 ? lendInfoPageInfo.getPages() : nextPage); // 下一页
        return "lendInfo";
    }

}
