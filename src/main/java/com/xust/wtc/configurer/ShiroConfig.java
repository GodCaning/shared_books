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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        shiroRealm.setAuthenticationCachingEnabled(true);
        shiroRealm.setAuthenticationCacheName("authenticationCache");
        shiroRealm.setAuthorizationCachingEnabled(true);
        shiroRealm.setAuthorizationCacheName("authorizationCache");
        return shiroRealm;
    }

//    @Bean
//    public CacheManager getCacheManager() {
//        return new EhCacheManager();
//    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setMaxAge(-1);
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

//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setSessionManager(sessionManager());
//        securityManager.setCacheManager(redisCacheManager());
//        securityManager.setRealm(shiroRealm());
//        return securityManager;
//    }

    /**
     * 方法层面的权限过滤, 用于支持shiro注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }
//    @Bean
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
//        aasa.setSecurityManager(securityManager());
//        return aasa;
//    }

    /**
     * 网络请求的权限过滤, 拦截外部请求
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        filterChainDefinitionMap.put("/code", "anon");
        filterChainDefinitionMap.put("/a", "authc");
        filterChainDefinitionMap.put("/qaz", "anon");
        filterChainDefinitionMap.put("/asdf", "anon");
        filterChainDefinitionMap.put("/wtc", "anon");
        filterChainDefinitionMap.put("/find", "anon");
        filterChainDefinitionMap.put("/myLogin", "anon");
        filterChainDefinitionMap.put("/login*", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/**/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("wang.html");
        return shiroFilterFactoryBean;
    }

    /**
     * 协助shiro初始化, 负责调用shiro的init与destory
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    @Autowired
//    private RedisSessionDao redisSessionDao;

//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator daapc = new DefaultAdvisorAutoProxyCreator();
//        daapc.setProxyTargetClass(true);
//        return daapc;
//    }
//
//    @Bean
//    public JCaptchaValidateFilter jCaptchaValidateFilter() {
//        JCaptchaValidateFilter jCaptchaValidateFilter = new JCaptchaValidateFilter();
//        jCaptchaValidateFilter.setFailureKeyAttribute("shiroLoginFailure");
//        jCaptchaValidateFilter.setJcaptchaEbabled(true);
//        jCaptchaValidateFilter.setJcaptchaParam("jcaptchaCode");
//        return jCaptchaValidateFilter;
//    }
//
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
//        //配置路径
//        Map<String, String> filterChainDefinitionMap = new HashMap<>();
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager());
//
//        shiroFilterFactoryBean.setLoginUrl("");
//        shiroFilterFactoryBean.setUnauthorizedUrl("");
//
//
//        filterChainDefinitionMap.put("/jujue", "authc");
//        filterChainDefinitionMap.put("**", "anon");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//
//        //配置过滤器
//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("jCaptchaValidate", jCaptchaValidateFilter());
//        shiroFilterFactoryBean.setFilters(filters);
//
//        //配置URL
//        shiroFilterFactoryBean.setLoginUrl("/templates/wang.html");
//        return shiroFilterFactoryBean;
//    }
}
