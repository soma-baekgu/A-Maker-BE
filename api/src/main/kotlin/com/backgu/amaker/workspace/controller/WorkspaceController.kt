package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.service.WorkspaceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class WorkspaceController(
    private val workspaceService: WorkspaceService,
) {
    @PostMapping("/workspaces")
    fun createWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody workspaceCreateDto: WorkspaceCreateDto,
    ): ResponseEntity<Long> = ResponseEntity.ok().body(workspaceService.createWorkspace(token.id, workspaceCreateDto))
}
