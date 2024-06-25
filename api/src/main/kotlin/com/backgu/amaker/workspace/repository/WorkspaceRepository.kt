package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.domain.Workspace
import org.springframework.data.jpa.repository.JpaRepository

interface WorkspaceRepository : JpaRepository<Workspace, Long>
