package com.backgu.amaker.batch.component

import com.backgu.amaker.batch.query.EventNotificationQuery
import com.backgu.amaker.infra.jpa.event.entity.QEventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.entity.QEventEntity
import com.backgu.amaker.infra.jpa.notification.entity.QEventNotificationEntity
import com.backgu.amaker.infra.jpa.user.entity.QUserEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class EventNotificationReader(
    private val entityManagerFactory: EntityManagerFactory,
) {
    @Bean
    fun reader(): QuerydslPagingItemReader<EventNotificationQuery> =
        QuerydslPagingItemReader(
            entityManagerFactory = entityManagerFactory,
            pageSize = 1000,
        ) { queryFactory: JPAQueryFactory ->
            val eventAssignedUser = QEventAssignedUserEntity("eau")
            val event = QEventEntity("e")
            val eventNotification = QEventNotificationEntity("en")
            val users = QUserEntity("u")

            val latestNotificationSubquery =
                JPAExpressions
                    .select(eventNotification.id.max())
                    .from(eventNotification)
                    .where(
                        eventNotification.userId
                            .eq(eventAssignedUser.userId)
                            .and(eventNotification.eventId.eq(event.id)),
                    ).groupBy(eventNotification.eventId, eventNotification.userId)

            queryFactory
                .select(
                    Projections.constructor(
                        EventNotificationQuery::class.java,
                        eventAssignedUser.id,
                        eventAssignedUser.userId,
                        eventAssignedUser.eventId,
                        event.eventTitle,
                        event.notificationStartTime,
                        event.notificationInterval,
                        event.deadLine,
                        eventNotification.id,
                        eventNotification.createdAt,
                        users.email,
                    ),
                ).from(eventAssignedUser)
                .join(event)
                .on(eventAssignedUser.eventId.eq(event.id))
                .leftJoin(eventNotification)
                .on(
                    eventNotification.id.eq(
                        latestNotificationSubquery,
                    ),
                ).join(users)
                .on(eventAssignedUser.userId.eq(users.id))
                .where(eventAssignedUser.isFinished.isFalse)
        }
}
