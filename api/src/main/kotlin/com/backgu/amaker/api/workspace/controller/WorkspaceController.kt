package com.backgu.amaker.api.workspace.controller

import com.backgu.amaker.api.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.workspace.dto.WorkspaceUserDto
import com.backgu.amaker.api.workspace.dto.request.WorkspaceCreateRequest
import com.backgu.amaker.api.workspace.dto.response.WorkspaceResponse
import com.backgu.amaker.api.workspace.dto.response.WorkspacesResponse
import com.backgu.amaker.api.workspace.service.WorkspaceFacadeService
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import jakarta.validation.Valid
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
    private val apiHandler: ApiHandler,
) : WorkspaceSwagger {
    @PostMapping
    override fun createWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody @Valid request: WorkspaceCreateRequest,
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
    ): ResponseEntity<ApiResult<WorkspacesResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                WorkspacesResponse.of(workspaceFacadeService.findWorkspaces(token.id)),
            ),
        )

    @GetMapping("/default")
    override fun getDefaultWorkspace(
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<ApiResult<WorkspaceResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                WorkspaceResponse.of(workspaceFacadeService.getDefaultWorkspace(token.id)),
            ),
        )

    @GetMapping("{workspace-id}/default-chat-room")
    override fun getDefaultChatRoom(
        @PathVariable("workspace-id") workspaceId: Long,
        @AuthenticationPrincipal token: JwtAuthentication,
    ): ResponseEntity<ApiResult<ChatRoomResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatRoomResponse.of(
                    workspaceFacadeService.getDefaultChatRoom(workspaceId, token.id),
                ),
            ),
        )

    @PutMapping("/{workspace-id}/invite/activate")
    override fun activateWorkspaceInvite(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<WorkspaceUserDto>> =
        ResponseEntity
            .ok()
            .body(apiHandler.onSuccess(workspaceFacadeService.activateWorkspaceUser(token.id, workspaceId)))
}
