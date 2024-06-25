package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.domain.WorkspaceUser
import org.springframework.data.jpa.repository.JpaRepository

interface WorkspaceUserRepository : JpaRepository<WorkspaceUser, Long>
