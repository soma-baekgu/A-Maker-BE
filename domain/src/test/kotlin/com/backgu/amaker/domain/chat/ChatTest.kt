package com.backgu.amaker.domain.chat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import java.time.LocalDateTime
import kotlin.test.Test

@DisplayName("Chat 테스트")
class ChatTest {
    @Test
    @DisplayName("reply 이벤트 생성 테스트")
    fun creatReplyEvent() {
        // given
        val chat =
            Chat(
                id = 1,
                userId = "user1",
                chatRoomId = 1,
                content = "안녕하세요",
                chatType = ChatType.GENERAL,
            )
        val deadLine = LocalDateTime.now().plusDays(1)
        val notificationStartHour = 1
        val notificationStartMinute = 30

        // when
        val replyEvent =
            chat.createReplyEvent(
                deadLine = deadLine,
                notificationStartHour = notificationStartHour,
                notificationStartMinute = notificationStartMinute,
                notificationInterval = 10,
                eventDetails = "이벤트 상세 내용",
            )

        // then
        assertThat(replyEvent.eventTitle).isEqualTo(chat.content)
        assertThat(replyEvent.deadLine).isEqualTo(deadLine)
        assertThat(replyEvent.notificationStartTime).isEqualTo(
            deadLine.minusHours(notificationStartHour.toLong()).minusMinutes(notificationStartMinute.toLong()),
        )
    }

    @Test
    @DisplayName("reaction 이벤트 생성 테스트")
    fun creatReactionEvent() {
        // given
        val chat =
            Chat(
                id = 1,
                userId = "user1",
                chatRoomId = 1,
                content = "안녕하세요",
                chatType = ChatType.GENERAL,
            )
        val deadLine = LocalDateTime.now().plusDays(1)
        val notificationStartHour = 1
        val notificationStartMinute = 30

        // when
        val replyEvent =
            chat.createReactionEvent(
                deadLine = deadLine,
                notificationStartHour = notificationStartHour,
                notificationStartMinute = notificationStartMinute,
                notificationInterval = 10,
            )

        // then
        assertThat(replyEvent.eventTitle).isEqualTo(chat.content)
        assertThat(replyEvent.deadLine).isEqualTo(deadLine)
        assertThat(replyEvent.notificationStartTime).isEqualTo(
            deadLine.minusHours(notificationStartHour.toLong()).minusMinutes(notificationStartMinute.toLong()),
        )
    }
}
