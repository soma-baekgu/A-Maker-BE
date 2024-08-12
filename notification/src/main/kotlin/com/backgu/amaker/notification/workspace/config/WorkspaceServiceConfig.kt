package com.backgu.amaker.notification.workspace.config

import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WorkspaceServiceConfig {
    @Bean
    fun workspaceUserService(workspaceUserRepository: WorkspaceUserRepository): WorkspaceUserService =
        WorkspaceUserService(workspaceUserRepository)
}
