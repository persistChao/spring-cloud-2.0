package com.answer.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author answer
 * @version 1.0.0
 * @date 2021/9/28 4:02 下午
 */
@Configuration
public class GateWayConfig {
    /**
     * 手动配置路由规则
     * 配置了一个id为baidu_service的路由规则，
     * 当访问地址http://localhost:9527/guonei的时候会自动转发到 http://new.baidu.con/guonei
     *
     * @param routeLocatorBuilder
     * @return
     */

    @Bean(name = "routeLocator")
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("baidu_service", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }

}
