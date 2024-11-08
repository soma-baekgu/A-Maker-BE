package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.TaskCommentWithUserDto
import com.backgu.amaker.common.http.response.PageResponse
import org.springframework.data.domain.Page

data class TaskCommentsViewResponse(
    override val content: List<TaskCommentWithUserResponse>,
    override val pageNumber: Int,
    override val pageSize: Int,
    override val totalElements: Long,
    override val totalPages: Int,
    override val hasNext: Boolean,
    override val hasPrevious: Boolean,
    override val isFirst: Boolean,
    override val isLast: Boolean,
) : PageResponse<TaskCommentWithUserResponse> {
    companion object {
        fun of(taskComments: Page<TaskCommentWithUserDto>): PageResponse<TaskCommentWithUserResponse> {
            val content = taskComments.content.map { TaskCommentWithUserResponse.of(it) }
            return TaskCommentsViewResponse(
                content = content,
                pageNumber = taskComments.number,
                pageSize = taskComments.size,
                totalElements = taskComments.totalElements,
                totalPages = taskComments.totalPages,
                hasNext = taskComments.hasNext(),
                hasPrevious = taskComments.hasPrevious(),
                isFirst = taskComments.isFirst,
                isLast = taskComments.isLast,
            )
        }
    }
}
