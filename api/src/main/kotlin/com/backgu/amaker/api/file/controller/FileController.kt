package com.backgu.amaker.api.file.controller

import com.backgu.amaker.api.file.service.FileFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    val fileFacadeService: FileFacadeService,
    val apiHandler: ApiHandler,
) : FileSwagger {
    @GetMapping("/url")
    override fun generateFileSaveUrl(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestParam(name = "path") path: String,
        @RequestParam(name = "extension") extension: String,
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<ApiResult<String>> =
        ResponseEntity.ok(apiHandler.onSuccess(fileFacadeService.getSavePath(path, token.id, name, extension)))
}
