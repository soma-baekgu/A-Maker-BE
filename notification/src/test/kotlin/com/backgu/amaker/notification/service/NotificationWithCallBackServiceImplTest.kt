package com.backgu.amaker.notification.service

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.application.notification.service.NotificationWithCallBackService
import com.backgu.amaker.infra.mail.constants.EmailConstants
import com.backgu.amaker.infra.mail.service.EmailHandler
import com.backgu.amaker.notification.common.container.IntegrationTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class NotificationWithCallBackServiceImplTest : IntegrationTest() {
    @Autowired
    lateinit var notificationWithCallBackServiceImpl: NotificationWithCallBackService

    @MockkBean
    lateinit var emailHandler: EmailHandler

    @Test
    @DisplayName("handleNotificationEvent 테스트")
    fun handleNotificationEventTest() {
        // given
        every { emailHandler.handleEmailEvent(any()) } returns Unit

        val emailEvent =
            EmailEvent(
                email = "abc@gmail.com",
                emailConstants = EmailConstants.WORKSPACE_INVITED,
                title = "hi",
                content = "bye",
            )
        // when
        notificationWithCallBackServiceImpl.handleNotificationEvent(emailEvent)

        // then
        verify(exactly = 1) { emailHandler.handleEmailEvent(emailEvent) }
    }
}
