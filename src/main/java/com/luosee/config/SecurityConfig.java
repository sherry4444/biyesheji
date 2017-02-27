//package com.luosee.config;
//
////import com.luosee.account.AccountService;
//
//
//import com.luosee.common.DaedalusePasswordEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authentication.dao.ReflectionSaltSource;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import javax.annotation.Resource;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Resource(name = "userService")
////    private UserService userService;
////
////    @Resource(name = "userDao")
////    private UserDao userDao;
////
////    @Resource(name = "businessDao")
////    private BusinessDao businessDao;
////
////    @Resource(name = "businessModelDao")
////    private BusinessModelDao businessModelDao;
////
////    @Resource(name = "sellerDao")
////    private SellerDao sellerDao;
////
////    @Resource(name = "planDao")
////    private PlanDao planDao;
////
////    @Resource(name = "productDao")
////    private ProductDao productDao;
////    @Bean
////    public TokenBasedRememberMeServices rememberMeServices() {
////        return new TokenBasedRememberMeServices("remember-me-key", ac
//// countService);
////    }
//
//    @Bean
//    public DaedalusePasswordEncoder passwordEncoder() {
//        return new DaedalusePasswordEncoder();
//	}
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
////        userService.setUserDao(userDao);
////        userService.setBusinessDao(businessDao);
////        userService.setBusinessModelDao(businessModelDao);
////        userService.setPlanDao(planDao);
////        userService.setProductDao(productDao);
////        userService.setSellerDao(sellerDao);
//        ReflectionSaltSource saltSource=new ReflectionSaltSource();
//        saltSource.setUserPropertyToUse("salt");
//        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//        authenticationProvider.setSaltSource(saltSource);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
////        authenticationProvider.setUserDetailsService(userService);
//        authenticationProvider.setHideUserNotFoundExceptions(false);
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/", "/favicon.ico", "/resources/**","/checkUserName","/signup", "/toretrievePage","/signup/*","/users/verification","/choiceRole","/get_verify","/saveNewPassword","/retrieveNewPassword").permitAll()
//                .anyRequest().authenticated()
//                .and()
//
//            .formLogin()
//                .loginPage("/users/login")
//                .permitAll()
//                .failureUrl("/users/login?error=1")
//                .loginProcessingUrl("/authenticate")
//                .and()
//            .logout()
//                .logoutUrl("/logout")
//                .permitAll()
//                .logoutSuccessUrl("/users/login?logout")
//                .and();
////            .rememberMe()
////                .rememberMeServices(rememberMeServices())
////                .key("remember-me-key");
//    }
//
//    @Bean(name = "authenticationManager")
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
////    @Bean
////    public AnonymousAuthenticationFilter anonymousAuthFilter()
////    {
////        AnonymousAuthenticationFilter anonymousAuthFilter=new AnonymousAuthenticationFilter();
////
////
////    }
//}