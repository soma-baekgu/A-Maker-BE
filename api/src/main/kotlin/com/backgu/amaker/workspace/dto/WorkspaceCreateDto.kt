package com.backgu.amaker.workspace.dto

data class WorkspaceCreateDto(
    val name: String,
    val inviteesEmails: Set<String>,
)
