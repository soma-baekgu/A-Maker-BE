package com.backgu.amaker.api.event.config

import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.event.service.EventService
import com.backgu.amaker.application.event.service.ReplyCommentService
import com.backgu.amaker.application.event.service.ReplyEventService
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedUserRepository
import com.backgu.amaker.infra.jpa.event.repository.EventRepository
import com.backgu.amaker.infra.jpa.event.repository.ReplyCommentRepository
import com.backgu.amaker.infra.jpa.event.repository.ReplyEventRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventServiceConfig {
    @Bean
    fun replyEventService(replyEventRepository: ReplyEventRepository): ReplyEventService = ReplyEventService(replyEventRepository)

    @Bean
    fun eventAssignedUserService(eventAssignedUserRepository: EventAssignedUserRepository): EventAssignedUserService =
        EventAssignedUserService(eventAssignedUserRepository)

    @Bean
    fun replyCommentService(replyCommentRepository: ReplyCommentRepository): ReplyCommentService =
        ReplyCommentService(replyCommentRepository)

    @Bean
    fun eventService(eventRepository: EventRepository): EventService = EventService(eventRepository)
}
