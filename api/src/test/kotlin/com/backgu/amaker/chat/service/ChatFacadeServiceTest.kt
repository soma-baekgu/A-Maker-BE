package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.dto.ChatDto
import com.backgu.amaker.chat.dto.GeneralChatCreateDto
import com.backgu.amaker.fixture.ChatFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("ChatFacadeService 테스트")
@Transactional
@SpringBootTest
class ChatFacadeServiceTest {
    @Autowired
    lateinit var chatFacadeService: ChatFacadeService

    @Autowired
    lateinit var fixture: ChatFixtureFacade

    @BeforeEach
    fun setUp() {
        fixture.setUp()
    }

    @Test
    @DisplayName("일반 채팅 생성 테스트")
    fun createGeneralChat() {
        // given
        val userId = "tester"
        fixture.user.createPersistedUser(userId)
        val workspace = fixture.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixture.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)
        val chatRoom =
            fixture.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.GROUP)
        fixture.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(userId))

        val generalChatCreateDto =
            GeneralChatCreateDto(
                userId = userId,
                content = "content",
            )

        // when
        val chatDto: ChatDto =
            chatFacadeService.createGeneralChat(generalChatCreateDto = generalChatCreateDto, chatRoomId = chatRoom.id)

        // then
        assertThat(chatDto.userId).isEqualTo(userId)
        assertThat(chatDto.chatRoomId).isEqualTo(chatRoom.id)
        assertThat(chatDto.content).isEqualTo(generalChatCreateDto.content)
    }
}
