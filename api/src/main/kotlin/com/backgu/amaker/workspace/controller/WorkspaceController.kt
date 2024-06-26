package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.service.WorkspaceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
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
    ): ResponseEntity<Long>
    {
        // TODO : 로그인한 사용자의 아이디를 가져와서 넣어줘야함
        return workspaceService.createWorkspace(token.id, workspaceCreateDto)
    }

    @GetMapping
    fun findWorkspaces(
        @AuthenticationPrincipal token: JwtAuthentication,
    ) {
        // TODO : 로그인한 사용자의 아이디를 가져와서 넣어줘야함
        workspaceService.findWorkspaces(token.id)
    }
}
