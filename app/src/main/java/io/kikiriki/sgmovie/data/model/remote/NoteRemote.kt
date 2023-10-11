package io.kikiriki.sgmovie.data.model.remote

import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.utils.DateFormat
import io.kikiriki.sgmovie.utils.extension.orNow
import io.kikiriki.sgmovie.utils.extension.toDate

data class NoteRemote(
    val id: Long?,
    val text: String?,
    val createdAt: String?,
    val updatedAt: String?
)

fun NoteRemote.toDomain(): Note {
    return Note(
        id = this.id,
        text = this.text,
        createdAt = this.createdAt.toDate(DateFormat.FULL_DATE).orNow(),
        updatedAt = this.updatedAt.toDate(DateFormat.FULL_DATE)
    )
}

fun List<NoteRemote>.toDomain() : List<Note> {
    return this.map { it.toDomain() }
}