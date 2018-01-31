package com.xust.wtc.Controller.user;

import com.qiniu.util.Auth;
import com.xust.wtc.Entity.DisplayPerson;
import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.UserService;
import com.xust.wtc.jcaptcha.JCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * 用户信息控制层
 * Created by Spirit on 2017/12/4.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

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
        return userService.sendEmail(loginName, email);
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
        return new DisplayPerson(person.getId(), person.getName(), person.getGender(), person.getAutograph(), person.getPortrait());
    }

    /**
     * 修改用户信息
     * @param person
     * @return
     */
    @PutMapping(value = "/updateUserInfo/{id}", consumes = "application/json", produces = "application/json")
    public Result updateUserInfo(@PathVariable("id") int id,@RequestBody Person person) {
        person.setId(id);
        return userService.updateUserInfo(person);
    }

    /**
     * 注册接口
     * @param httpServletRequest
     * @param code
     * @param person
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public Result register(HttpServletRequest httpServletRequest,
                           @RequestParam(value = "code") String code,
                           @Validated({Person.Register.class}) @RequestBody() Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            Result result = new Result();
            result.setStatus(0);
            result.setContent("注册参数不正确");
            return result;
        }
        Result result = isTrueCode(httpServletRequest, code);
        if (result != null) {
            return result;
        }
        return userService.register(person);
    }

    /**
     * 获取七牛云上传图片授权码
     */
    @GetMapping(value = "/updateCode", consumes = "application/json", produces = "application/json")
    public String updateCode() {
        String accessKey = "RRyC_e6AmR_7u7MKtQNJNkm4QfTvZs7n--suNnB5";
        String secretKey = "-L3t_xoFd7JvuNGJXQ0G6yU3IaCzXwj6cLmqA7mh";
        String bucket = "spirit";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return upToken;
    }

    /**
     * 更新用户头像
     * @param person
     * @return
     */
    @PutMapping(value = "/updatePortrait", consumes = "application/json", produces = "application/json")
    public Result updateUserPortrait(@RequestBody Person person) {
        return userService.modifyPortrait(person.getPortrait(), person.getId());
    }

    /**
     * 登录
     * @param httpServletRequest
     * @param //code
     * @param person
     * @return
     */
    @PostMapping(value = "/myLogin", consumes = "application/json", produces = "application/json")
    public Result login(HttpServletRequest httpServletRequest,
                        @RequestParam(value = "code") String code,
                        @RequestBody() Person person) {
        Result result = isTrueCode(httpServletRequest, code);
        if (result != null) {
            return result;
        } else {
            result = new Result();
        }
        System.out.println(person);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(person.getLoginName(), person.getLoginPasswd());

        try {
            subject.login(token);
            System.out.println(subject.getPrincipal());
            Person p = userService.findUserByLoginName(person.getLoginName());
            //把当前用户缓存进redis
            redisTemplate.opsForValue().set(subject.getSession().getId(), p.getId(), 30, TimeUnit.MINUTES);
            //redisTemplate.boundHashOps("userId").put(subject.getSession().getId(), person.getId());
            result.setStatus(1);
            result.setContent("登录成功");
        } catch (Exception e) {
            result.setStatus(0);
            result.setContent("登录失败");
        }
        return result;
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
            result.setStatus(0);
            result.setContent("验证码不正确");
        }
        return result;
    }
}
