package com.backgu.amaker.api.user.config

import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.infra.jpa.user.reposotory.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserServiceConfig {
    @Bean
    fun userService(userRepository: UserRepository): UserService = UserService(userRepository)
}
