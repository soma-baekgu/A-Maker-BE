package com.backgu.amaker.workspace.controller

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.request.WorkspaceCreateRequest
import com.backgu.amaker.workspace.dto.response.WorkspaceResponse
import com.backgu.amaker.workspace.dto.response.WorkspacesResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "workspaces", description = "워크스페이스 API")
interface WorkspaceSwagger {
    @Operation(summary = "workspace 생성", description = "워크스페이스를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "워크스페이스 생성 성공",
                content = [Content(schema = Schema(implementation = Unit::class))],
            ),
        ],
    )
    fun createWorkspace(
        @Parameter(hidden = true) token: JwtAuthentication,
        request: WorkspaceCreateRequest,
    ): ResponseEntity<Unit>

    @Operation(summary = "workspaces 조회", description = "유저가 참여하고 있는 모든 워크스페이스를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "워크스페이스 조회 성공",
                content = [Content(schema = Schema(implementation = WorkspacesResponse::class))],
            ),
        ],
    )
    fun findWorkspaces(
        @Parameter(hidden = true) token: JwtAuthentication,
    ): ResponseEntity<WorkspacesResponse>

    @Operation(summary = "기본 workspace 조회", description = "유저가 가장 최근에 참여한 워크스페이스를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "워크스페이스 조회 성공",
                content = [Content(schema = Schema(implementation = WorkspaceResponse::class))],
            ),
        ],
    )
    fun getDefaultWorkspace(
        @Parameter(hidden = true) token: JwtAuthentication,
    ): ResponseEntity<WorkspaceResponse>

    @Operation(summary = "워크스페이스 유저 활성화", description = "워크스페이스에 대해 초대된 사용자가 초대를 수락합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "워크스페이스 유저 활성화 성공",
                content = [Content(schema = Schema(implementation = Unit::class))],
            ),
        ],
    )
    fun activateWorkspaceInvite(
        @Parameter(hidden = true) token: JwtAuthentication,
        workspaceId: Long,
    ): ResponseEntity<Unit>
}
