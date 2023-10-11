package io.kikiriki.sgmovie.domain.note

import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.data.repository.note.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke () : Result<List<Note>> {
        return noteRepository.getAll()
    }

}