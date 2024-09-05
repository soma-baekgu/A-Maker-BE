package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.ReactionOption
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ReactionOption")
@Table(name = "reaction_option")
class ReactionOptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val eventId: Long,
    val content: String,
) {
    fun toDomain(): ReactionOption = ReactionOption(id = id, eventId = eventId, content = content)

    companion object {
        fun of(reactionOption: ReactionOption): ReactionOptionEntity =
            ReactionOptionEntity(eventId = reactionOption.eventId, content = reactionOption.content)
    }
}
