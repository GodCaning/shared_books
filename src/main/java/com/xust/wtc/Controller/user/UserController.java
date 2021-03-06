package com.xust.wtc.Controller.user;

import com.qiniu.util.Auth;
import com.xust.wtc.Entity.chat.Feedback;
import com.xust.wtc.Entity.user.DisplayPerson;
import com.xust.wtc.Entity.user.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.UserService;
import com.xust.wtc.jcaptcha.JCaptcha;
import com.xust.wtc.utils.CONSTANT_STATUS;
import com.xust.wtc.utils.Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * 用户信息控制层
 * Created by Spirit on 2017/12/4.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 忘记密码第一步 验证用户名
     * @param request
     * @param loginName
     * @param code
     * @return
     */
    @GetMapping(value = "/validationName", consumes = "application/json", produces = "application/json")
    public Result validationLogin(HttpServletRequest request, @RequestParam String loginName, @RequestParam String code) {
        Result result = isTrueCode(request, code);
        if (result != null) {
            return result;
        }
        return userService.validationLogin(loginName);
    }

    /**
     * 忘记密码第二步 发送邮件
     * @param loginName
     * @param email
     * @return
     */
    @GetMapping(value = "/sendEmail", consumes = "application/json", produces = "application/json")
    public Result sendEmail(@RequestParam String loginName, @RequestParam String email) {
        Result s = userService.sendEmail(loginName, email);
        return s;
    }

    /**
     * 忘记密码第三步 修改密码
     * @param code
     * @param passwd
     * @return
     */
    @PostMapping(value = "/modifyPassWd", consumes = "application/json", produces = "application/json")
    public Result modifyPassWd(@RequestParam String code, @RequestBody() String passwd) {
        return userService.modifyPassWd(code, passwd);
    }

    /**
     * 根据ID号查询某一个用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "/findUser/{id}", consumes = "application/json", produces = "application/json")
    public DisplayPerson findUser(@PathVariable int id) {
        Person person = userService.findUser(id);
        return new DisplayPerson(person.getId(), person.getName(), person.getLoginName(), person.getGender(), person.getAutograph(), person.getPortrait());
    }

    /**
     * 修改用户信息
     * @param person
     * @return
     */
    @PutMapping(value = "/updateUserInfo", consumes = "application/json", produces = "application/json")
    public Result updateUserInfo(@RequestBody Person person, HttpSession session) {
        person.setId(Utils.getUserId(session.getId()));
        return userService.updateUserInfo(person);
    }

    /**
     * 注册接口
     * @return
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public Result register(HttpServletRequest request,
                           @RequestParam(value = "code") String code,
                           @Validated({Person.Register.class}) @RequestBody() Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            Result result = new Result();
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("注册参数不正确");
            return result;
        }
        Result result = isTrueCode(request, code);
        if (result != null) {
            return result;
        }
        return userService.register(person);
    }

    /**
     * 获取七牛云上传图片授权码
     */
    @GetMapping(value = "/updateCode")
    public String updateCode() {
        String accessKey = "RRyC_e6AmR_7u7MKtQNJNkm4QfTvZs7n--suNnB5";
        String secretKey = "-L3t_xoFd7JvuNGJXQ0G6yU3IaCzXwj6cLmqA7mh";
        String bucket = "spirit";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

    /**
     * 更新用户头像
     * @return
     */
    @PutMapping(value = "/updatePortrait", consumes = "application/json", produces = "application/json")
    public Result updateUserPortrait(@RequestBody String portrait,
                                     HttpSession session) {
        int userId = Utils.getUserId(session.getId());
        return userService.modifyPortrait(portrait, userId);
    }

    /**
     * 反馈
     * @return
     */
    @PostMapping(value = "/feedback", consumes = "application/json", produces = "application/json")
    public Result feedback(@RequestBody Feedback feedback, HttpSession session) {
        int userId = Utils.getUserId(session.getId());
        return userService.feedback(feedback, userId);
    }

    /**
     * 登录
     * @return
     */
    @PostMapping(value = "/myLogin", consumes = "application/json", produces = "application/json")
    public Result login(HttpServletRequest httpServletRequest,
                        @RequestParam(value = "code") String code,
                        @Validated({Person.Login.class}) @RequestBody() Person person) {
        Result result = isTrueCode(httpServletRequest, code);
        if (result != null) {
            return result;
        } else {
            result = new Result();
        }
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(person.getLoginName(), person.getLoginPasswd());

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

    /**
     * 退出操作
     */
    @GetMapping(value = "/myLogout", consumes = "application/json", produces = "application/json")
    public void logout(HttpSession session) {
        Subject subject = Utils.getUserSubject(session.getId());
        System.out.println(subject.getSession().getId());
        subject.logout();
    }

    /**
     * 登陆成功获取当前登陆的用户信息
     * @param session
     * @return
     */
    @GetMapping(value = "/loginInfo", consumes = "application/json", produces = "application/json")
    public DisplayPerson loginInfo(HttpSession session) {
        Person person = userService.findUser(Utils.getUserId(session.getId()));
        return new DisplayPerson(person.getId(), person.getName(), person.getLoginName(), person.getGender(), person.getAutograph(), person.getPortrait());
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        HttpSession session = request.getSession();

        String id = session.getId();
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(id);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 验证码判断
     * @param request
     * @param code
     * @return
     */
    private Result isTrueCode(HttpServletRequest request, String code) {
        Result result = null;
        if (!JCaptcha.validateResponse(request, code)) {
            result = new Result();
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("验证码不正确");
        }
        return result;
    }
}
