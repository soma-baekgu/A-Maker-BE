package com.backgu.amaker.workspace.controller

import com.backgu.amaker.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.workspace.dto.request.WorkspaceCreateRequest
import com.backgu.amaker.workspace.dto.response.WorkspaceResponse
import com.backgu.amaker.workspace.dto.response.WorkspacesResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "workspaces", description = "워크스페이스 API")
interface WorkspaceSwagger {
    @Operation(summary = "workspace 생성", description = "워크스페이스를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "워크스페이스 생성 성공",
            ),
        ],
    )
    fun createWorkspace(
        @Parameter(hidden = true) token: JwtAuthentication,
        @RequestBody @Valid request: WorkspaceCreateRequest,
    ): ResponseEntity<Unit>

    @Operation(summary = "workspaces 조회", description = "유저가 참여하고 있는 모든 워크스페이스를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "워크스페이스 조회 성공",
            ),
        ],
    )
    fun findWorkspaces(
        @Parameter(hidden = true) token: JwtAuthentication,
    ): ResponseEntity<ApiResult<WorkspacesResponse>>

    @Operation(summary = "기본 workspace 조회", description = "유저가 가장 최근에 참여한 워크스페이스를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "워크스페이스 조회 성공",
            ),
        ],
    )
    fun getDefaultWorkspace(
        @Parameter(hidden = true) token: JwtAuthentication,
    ): ResponseEntity<ApiResult<WorkspaceResponse>>

    @Operation(summary = "그룹 채팅방 조회", description = "워크스페이스의 그룹 채팅방을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "그룹 채팅방 조회 성공",
            ),
        ],
    )
    fun getGroupChatRoom(
        @PathVariable
        @Parameter(description = "워크스페이스 ID", required = true, `in` = ParameterIn.PATH)
        workspaceId: Long,
        @Parameter(hidden = true) token: JwtAuthentication,
    ): ResponseEntity<ApiResult<ChatRoomResponse>>
}
