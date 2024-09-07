package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.event.dto.ReactionCommentCreateDto
import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import com.backgu.amaker.api.fixture.ReactionCommentFixtureFacade
import com.backgu.amaker.api.fixture.ReplyCommentFixtureFacade
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("EventFacadeService 테스트")
@Transactional
class EventCommentFacadeServiceTest : IntegrationTest() {
    @Autowired
    lateinit var eventCommentFacadeService: EventCommentFacadeService

    @Autowired
    lateinit var replyCommentFixtures: ReplyCommentFixtureFacade

    @Autowired
    lateinit var reactionCommentFixtures: ReactionCommentFixtureFacade

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

        // when & then
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

    @Test
    @DisplayName("reply comments 조회")
    fun findReplyComments() {
        // given
        val userId = "test-user-id"
        val replyEvent = replyCommentFixtures.setUp(userId = "test-user-id")
        ReplyCommentCreateDto("test-content")
        replyCommentFixtures.replyCommentFixture.createPersistedReplyComments(userId, replyEvent.id, 100)

        val pageable =
            PageRequest.of(
                0,
                20,
                Sort.by("id").ascending(),
            )

        // when
        val result = eventCommentFacadeService.findReplyComments(userId, replyEvent.id, pageable)

        // then
        assertThat(result).isNotNull
        assertThat(result.content.size).isEqualTo(20)
        assertThat(result.totalElements).isEqualTo(100)
        assertThat(result.content[0].id).isLessThan(result.content[1].id)
    }

    @Test
    @DisplayName("reply comments 조회 실패 테스트 - 워크스페이스에 속하지 않은 유저")
    fun failFindReplyCommentsNotInWorkspace() {
        // given
        val userId = "diff-user-id"
        val replyEvent = replyCommentFixtures.setUp(userId = "test-user-id")
        replyCommentFixtures.userFixture.createPersistedUser(userId)
        ReplyCommentCreateDto("test-content")
        replyCommentFixtures.replyCommentFixture.createPersistedReplyComments(userId, replyEvent.id, 100)

        val pageable =
            PageRequest.of(
                0,
                20,
                Sort.by("id").ascending(),
            )

        // when & then
        assertThatThrownBy {
            eventCommentFacadeService.findReplyComments(
                userId,
                replyEvent.id,
                pageable,
            )
        }.isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.WORKSPACE_UNREACHABLE)
    }

    @Test
    @DisplayName("reaction comment 생성 테스트")
    fun createReactionComment() {
        // given
        val userId = "test-user-id"
        val (reactionEvent, options) = reactionCommentFixtures.setUp(userId = "test-user-id")
        val reactionCommentCreateDto = ReactionCommentCreateDto(options[0].id)

        // when
        val result =
            eventCommentFacadeService.createReactionComment(userId, reactionEvent.id, reactionCommentCreateDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.eventId).isEqualTo(reactionEvent.id)
        assertThat(result.optionId).isEqualTo(options[0].id)
    }

    @Test
    @DisplayName("reaction comment 변경 테스트")
    fun toggleReactionComment() {
        // given
        val userId = "test-user-id"
        val (reactionEvent, options) = reactionCommentFixtures.setUp(userId = "test-user-id")
        reactionCommentFixtures.reactionCommentFixture.createPersistedReactionComment(
            userId,
            reactionEvent.id,
            options[0].id,
        )
        val reactionCommentCreateDto = ReactionCommentCreateDto(options[1].id)

        // when
        val result =
            eventCommentFacadeService.createReactionComment(userId, reactionEvent.id, reactionCommentCreateDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.eventId).isEqualTo(reactionEvent.id)
        assertThat(result.optionId).isEqualTo(options[1].id)
    }

    @Test
    @DisplayName("reaction comment 생성 실패 테스트 - 할당되지 않은 유저")
    fun failCreateReactionCommentNotAssignedUser() {
        // given
        val userId = "diff-user-id"
        val (reactionEvent, options) = reactionCommentFixtures.setUp(userId = "test-user-id")
        val reactionCommentCreateDto = ReactionCommentCreateDto(options[0].id)
        reactionCommentFixtures.userFixture.createPersistedUser(userId)

        // when & then
        assertThatThrownBy {
            eventCommentFacadeService.createReactionComment(
                userId,
                reactionEvent.id,
                reactionCommentCreateDto,
            )
        }.isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.EVENT_ASSIGNED_USER_NOT_FOUND)
    }
}
