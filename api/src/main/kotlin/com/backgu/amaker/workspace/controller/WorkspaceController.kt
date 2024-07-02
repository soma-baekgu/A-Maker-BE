package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.request.WorkspaceCreateRequest
import com.backgu.amaker.workspace.dto.response.WorkspaceResponse
import com.backgu.amaker.workspace.dto.response.WorkspacesResponse
import com.backgu.amaker.workspace.service.WorkspaceFacadeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/workspaces")
class WorkspaceController(
    private val workspaceFacadeService: WorkspaceFacadeService,
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
                    .buildAndExpand(workspaceFacadeService.createWorkspace(token.id, request.toDto()).workspaceId)
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

    @PutMapping("/{workspaceId}/invite/activate")
    override fun activateWorkspaceInvite(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable workspaceId: Long,
    ): ResponseEntity<Unit> {
        workspaceFacadeService.activateWorkspaceUser(token.id, workspaceId)
        return ResponseEntity.noContent().build()
    }
}
