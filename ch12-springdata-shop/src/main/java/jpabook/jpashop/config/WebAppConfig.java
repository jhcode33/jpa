package jpabook.jpashop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = {"jpabook.jpashop.web", "jpabook.jpashop.config"})
public class WebAppConfig extends WebMvcConfigurationSupport {

//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver =
//                new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        registry.viewResolver(resolver);
//    }

//    @Bean
//    public OpenEntityManagerInViewInterceptor interceptor(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        OpenEntityManagerInViewInterceptor interceptor =
//                new OpenEntityManagerInViewInterceptor();
//        interceptor.setEntityManagerFactory(entityManagerFactory.getNativeEntityManagerFactory());
//        return interceptor;
//    }

//    @Override
//    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        //super.configureDefaultServletHandling(configurer);
//        configurer.enable();
//    }
}
