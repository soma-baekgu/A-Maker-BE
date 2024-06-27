package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.dto.WorkspaceDto
import com.backgu.amaker.workspace.dto.WorkspacesDto
import com.backgu.amaker.workspace.service.WorkspaceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/workspaces")
class WorkspaceController(
    private val workspaceService: WorkspaceService,
) {
    @PostMapping
    fun createWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody workspaceCreateDto: WorkspaceCreateDto,
    ): ResponseEntity<Long> = ResponseEntity.ok().body(workspaceService.createWorkspace(token.id, workspaceCreateDto))

    @GetMapping
    fun findWorkspaces(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspacesDto> = ResponseEntity.ok().body(workspaceService.findWorkspaces(token.id))

    @GetMapping("/default")
    fun getDefaultWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspaceDto> = ResponseEntity.ok().body(workspaceService.getDefaultWorkspace(token.id))
}
