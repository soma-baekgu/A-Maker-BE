package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import com.backgu.amaker.api.fixture.ReplyCommentFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("EventFacadeService 테스트")
@Transactional
@SpringBootTest
class EventCommentFacadeServiceTest {
    @Autowired
    lateinit var eventCommentFacadeService: EventCommentFacadeService

    @Autowired
    lateinit var replyCommentFixtures: ReplyCommentFixtureFacade

    @Test
    @DisplayName("reply comment 생성 테스트")
    fun createReplyComment() {
        // given
        val userId = "test-user-id"
        val replyEvent = replyCommentFixtures.setUp(userId = "test-user-id")
        val replyCommentCreateDto = ReplyCommentCreateDto("test-content")

        // when
        val result =
            eventCommentFacadeService.createReplyComment(userId, replyEvent.id, replyCommentCreateDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.content).isIn("test-content")
    }

    @Test
    @DisplayName("reply comment 생성 실패 테스트 - 할당되지 않은 유저")
    fun failCreateReplyCommentNotAssignedUser() {
        // given
        val userId = "diff-user-id"
        val replyEvent = replyCommentFixtures.setUp(userId = "test-user-id")
        val replyCommentCreateDto = ReplyCommentCreateDto("test-content")
        replyCommentFixtures.userFixture.createPersistedUser(userId)

        // when * then
        assertThatThrownBy {
            eventCommentFacadeService.createReplyComment(
                userId,
                replyEvent.id,
                replyCommentCreateDto,
            )
        }.isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.EVENT_ASSIGNED_USER_NOT_FOUND)
    }
}
