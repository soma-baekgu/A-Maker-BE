package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.WorkspaceCreate
import com.backgu.amaker.workspace.dto.WorkspaceDto
import com.backgu.amaker.workspace.dto.WorkspacesDto
import com.backgu.amaker.workspace.service.WorkspaceFacadeService
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
    private val workspaceFacadeService: WorkspaceFacadeService,
) {
    @PostMapping
    fun createWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody workspaceCreate: WorkspaceCreate,
    ): ResponseEntity<Long> = ResponseEntity.ok().body(workspaceFacadeService.createWorkspace(token.id, workspaceCreate).id)

    @GetMapping
    fun findWorkspaces(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspacesDto> = ResponseEntity.ok().body(workspaceFacadeService.findWorkspaces(token.id))

    @GetMapping("/default")
    fun getDefaultWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspaceDto> = ResponseEntity.ok().body(workspaceFacadeService.getDefaultWorkspace(token.id))
}
