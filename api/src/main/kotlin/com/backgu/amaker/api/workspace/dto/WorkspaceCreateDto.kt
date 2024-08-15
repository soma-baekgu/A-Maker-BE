package com.backgu.amaker.api.workspace.dto

data class WorkspaceCreateDto(
    val name: String,
    val inviteesEmails: Set<String>,
)
