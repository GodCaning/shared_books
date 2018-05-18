package com.xust.wtc.configurer;

import com.xust.wtc.redis.RedisCacheManager;
import com.xust.wtc.shiro.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Spirit on 2017/11/26.
 */
@Configuration
public class ShiroConfig {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher =
                new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1023);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        shiroRealm.setAuthenticationCachingEnabled(true);
//        shiroRealm.setAuthenticationCacheName("authenticationCache");
//        shiroRealm.setAuthorizationCachingEnabled(true);
//        shiroRealm.setAuthorizationCacheName("authorizationCache");
        return shiroRealm;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setMaxAge(-1);
        simpleCookie.setHttpOnly(false);
        return simpleCookie;
    }

    @Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        return enterpriseCacheSessionDAO;
    }

    @Bean
    public QuartzSessionValidationScheduler quartzSessionValidationScheduler() {
        QuartzSessionValidationScheduler quartzSessionValidationScheduler =
                new QuartzSessionValidationScheduler();
        quartzSessionValidationScheduler.setSessionManager(getSessionManager());
        quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
        return quartzSessionValidationScheduler;
    }

    /**
     * DefaultWebSessionManager摒弃Servlet容器的Cookie会话，使用自己的会话机制
     * @return
     */
    @Bean
    public DefaultWebSessionManager getSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(enterpriseCacheSessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionValidationScheduler(quartzSessionValidationScheduler());
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(shiroRealm());
        dwsm.setCacheManager(redisCacheManager());
        dwsm.setSessionManager(getSessionManager());
        return dwsm;
    }

    /**
     * 方法层面的权限过滤, 用于支持shiro注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 网络请求的权限过滤, 拦截外部请求
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/adminLogin.html", "anon");
        filterChainDefinitionMap.put("/forget1.html", "anon");
        filterChainDefinitionMap.put("/forget2.html", "anon");
        filterChainDefinitionMap.put("/forget3.html", "anon");
        filterChainDefinitionMap.put("/forget4.html", "anon");
        filterChainDefinitionMap.put("/code", "anon");
        filterChainDefinitionMap.put("/myLogin", "anon");
        filterChainDefinitionMap.put("/adminLo", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("login.html");
        return shiroFilterFactoryBean;
    }

    /**
     * 协助shiro初始化, 负责调用shiro的init与destory
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
