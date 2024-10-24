package com.backgu.amaker.domain.event

enum class EventStatus {
    ONGOING {
        override fun filter(
            event: Event,
            assignedUsers: List<EventAssignedUser>,
        ): Boolean = !event.isAchieved(assignedUsers) && !event.isClosed()
    },
    EXPIRED {
        override fun filter(
            event: Event,
            assignedUsers: List<EventAssignedUser>,
        ): Boolean = event.isClosed() && !event.isAchieved(assignedUsers)
    },
    COMPLETED {
        override fun filter(
            event: Event,
            assignedUsers: List<EventAssignedUser>,
        ): Boolean = event.isAchieved(assignedUsers)
    }, ;

    abstract fun filter(
        event: Event,
        assignedUsers: List<EventAssignedUser>,
    ): Boolean
}
