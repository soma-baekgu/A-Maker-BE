package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.common.dto.response.PageResponse
import com.backgu.amaker.api.event.dto.ReplyCommentWithUserDto
import org.springframework.data.domain.Page

data class ReplyCommentsViewResponse(
    override val content: List<ReplyCommentWithUserResponse>,
    override val pageNumber: Int,
    override val pageSize: Int,
    override val totalElements: Long,
    override val totalPages: Int,
    override val hasNext: Boolean,
    override val hasPrevious: Boolean,
    override val isFirst: Boolean,
    override val isLast: Boolean,
) : PageResponse<ReplyCommentWithUserResponse> {
    companion object {
        fun of(replyComments: Page<ReplyCommentWithUserDto>): PageResponse<ReplyCommentWithUserResponse> {
            val content = replyComments.content.map { ReplyCommentWithUserResponse.of(it) }
            return ReplyCommentsViewResponse(
                content = content,
                pageNumber = replyComments.number,
                pageSize = replyComments.size,
                totalElements = replyComments.totalElements,
                totalPages = replyComments.totalPages,
                hasNext = replyComments.hasNext(),
                hasPrevious = replyComments.hasPrevious(),
                isFirst = replyComments.isFirst,
                isLast = replyComments.isLast,
            )
        }
    }
}
