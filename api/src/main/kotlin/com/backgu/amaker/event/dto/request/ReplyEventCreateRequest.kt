package com.backgu.amaker.event.dto.request

import com.backgu.amaker.event.dto.ReplyEventCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class ReplyEventCreateRequest(
    @Schema(description = "제목", example = "제목 어때요?")
    @field:NotBlank(message = "이벤트 제목을 입력해주세요.")
    val eventTitle: String,
    @Schema(description = "상세내용", example = "상세내용 어때요?")
    @field:NotBlank(message = "이벤트 내용을 입력해주세요.")
    val eventDetails: String,
    @Schema(description = "답변을 요청할 인원", example = "[\"user1\", \"user2\"]")
    @field:Size(min = 1, message = "최소 한 명 이상의 인원을 지정해야 합니다.")
    val assignees: List<String>,
    @Schema(description = "마감 기한", example = "2021-08-01T00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val deadLine: LocalDateTime,
    @Schema(description = "알림 시작 시간", example = "1")
    val notificationStartHour: Int,
    @Schema(description = "알림 시작 분", example = "30")
    val notificationStartMinute: Int,
    @Schema(description = "알림 주기", example = "15")
    val interval: Int,
) {
    fun toDto() =
        ReplyEventCreateDto(
            eventTitle = eventTitle,
            eventDetails = eventDetails,
            assignees = assignees,
            deadLine = deadLine,
            notificationStartMinute = notificationStartMinute,
            notificationStartHour = notificationStartHour,
            interval = interval,
        )
}
