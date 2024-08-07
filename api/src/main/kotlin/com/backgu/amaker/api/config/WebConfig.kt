package com.backgu.amaker.api.config

import com.backgu.amaker.api.common.web.InetAddressRequestResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val inetAddressRequestResolver: InetAddressRequestResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(inetAddressRequestResolver)
    }
}
