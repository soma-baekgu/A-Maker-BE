package com.backgu.amaker.domain.chat

enum class ChatType {
    GENERAL,
    TASK,
    REPLY,
    REACTION,
    FILE,
    IMAGE,
    ;

    companion object {
        fun isEventChat(type: ChatType): Boolean =
            when (type) {
                REPLY, TASK, REACTION -> true
                else -> false
            }
    }
}
