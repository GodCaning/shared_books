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
     * 根据ID号查询某一个用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findUser/{id}", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    public DisplayPerson findUser(@PathVariable int id) {
        Person person = userService.findUser(id);
        return new DisplayPerson(person.getId(), person.getName(), person.getBirthdate(), person.getGender(), person.getAutograph());
    }

    /**
     * 修改用户信息
     * @param person
     * @return
     */
    @RequestMapping(value = "/updateUserInfo/{id}", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
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
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
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
        if (!JCaptcha.validateResponse(httpServletRequest, code)) {
            Result result = new Result();
            result.setStatus(0);
            result.setContent("验证码不正确");
            return result;
        }
        return userService.register(person);
    }

    /**
     * 七牛云上传图片
     */
    @GetMapping("/asdf")
    public void a() {
        String accessKey = "RRyC_e6AmR_7u7MKtQNJNkm4QfTvZs7n--suNnB5";
        String secretKey = "-L3t_xoFd7JvuNGJXQ0G6yU3IaCzXwj6cLmqA7mh";
        String bucket = "spirit";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
    }

    /**
     * 登录
     * @param httpServletRequest
     * @param code
     * @param person
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public Result login(HttpServletRequest httpServletRequest,
                        @RequestParam(value = "code") String code,
                        @Validated({Person.Login.class})@RequestBody() Person person) {
        Result result = new Result();
        if (!JCaptcha.validateResponse(httpServletRequest, code)) {
            result.setStatus(0);
            result.setContent("验证码不正确");
            return result;
        }

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(person.getLoginName(), person.getLoginPasswd());

        try {
            subject.login(token);
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
}
