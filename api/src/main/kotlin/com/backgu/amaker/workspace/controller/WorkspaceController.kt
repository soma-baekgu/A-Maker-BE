package com.backgu.amaker.workspace.controller

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.service.WorkspaceService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class WorkspaceController(
    private val workspaceService: WorkspaceService,
) {
    @PostMapping("/workspaces")
    fun createWorkspace(
        @RequestBody workspaceCreateDto: WorkspaceCreateDto,
    ) {
        // TODO : 로그인한 사용자의 아이디를 가져와서 넣어줘야함
        workspaceService.createWorkspace(workspaceCreateDto)
    }
}
