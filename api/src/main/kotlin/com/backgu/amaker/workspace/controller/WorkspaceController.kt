package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.request.WorkspaceCreateRequest
import com.backgu.amaker.workspace.dto.response.WorkspaceResponse
import com.backgu.amaker.workspace.dto.response.WorkspacesResponse
import com.backgu.amaker.workspace.service.WorkspaceFacadeService
import com.backgu.amaker.workspace.service.WorkspaceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/workspaces")
class WorkspaceController(
    private val workspaceFacadeService: WorkspaceFacadeService,
    private val workspaceService: WorkspaceService,
) : WorkspaceSwagger {
    @PostMapping
    override fun createWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody request: WorkspaceCreateRequest,
    ): ResponseEntity<Unit> =
        ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(workspaceFacadeService.createWorkspace(token.id, request.toDto()))
                    .toUri(),
            ).build()

    @GetMapping
    override fun findWorkspaces(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspacesResponse> =
        ResponseEntity.ok().body(
            WorkspacesResponse.of(workspaceFacadeService.findWorkspaces(token.id)),
        )

    @GetMapping("/default")
    override fun getDefaultWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<WorkspaceResponse> =
        ResponseEntity.ok().body(
            WorkspaceResponse.of(workspaceFacadeService.getDefaultWorkspace(token.id)),
        )
}
