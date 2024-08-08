package com.backgu.amaker.api.workspace.config

import com.backgu.amaker.application.workspace.WorkspaceService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceRepository
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WorkspaceServiceConfig {
    @Bean
    fun workspaceService(workspaceRepository: WorkspaceRepository): WorkspaceService = WorkspaceService(workspaceRepository)

    @Bean
    fun workspaceUserService(workspaceUserRepository: WorkspaceUserRepository): WorkspaceUserService =
        WorkspaceUserService(workspaceUserRepository)
}
