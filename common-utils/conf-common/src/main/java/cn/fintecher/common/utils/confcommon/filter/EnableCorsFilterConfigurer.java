package cn.fintecher.common.utils.confcommon.filter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CorsFilterConfigurer.class)
public @interface EnableCorsFilterConfigurer {
}