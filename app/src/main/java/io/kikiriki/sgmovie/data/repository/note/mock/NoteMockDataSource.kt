package io.kikiriki.sgmovie.data.repository.note.mock

import io.kikiriki.sgmovie.data.model.domain.Note
import io.kikiriki.sgmovie.data.repository.note.NoteRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

class NoteMockDataSource @Inject constructor()  : NoteRepository.MockDataSource {

    override suspend fun create(note: Note): Result<Note> {
        return try {
            delay(1000)
            Result.success(
                Note(
                    id = Random.nextLong(),
                    text = note.text,
                    createdAt = note.createdAt,
                    updatedAt = note.updatedAt
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(note: Note): Result<Note> {
        return try {
            delay(1000)
            Result.success(
                Note(
                    id = note.id,
                    text = note.text,
                    createdAt = note.createdAt,
                    updatedAt = note.updatedAt
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(note: Note): Result<Note> {
        return try {
            delay(1000)
            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: Long): Result<Note> {
        return try {
            delay(1000)
            Result.success(
                Note(
                    id = id,
                    text = "Lorem ipsum dolor sit amet\n, consectetur adipiscing elit. Quisque tempus non risus vel aliquam. Vivamus ullamcorper vitae risus non molestie. Praesent elit quam, placerat ut neque et, interdum consequat mauris. Quisque sed sem id augue pellentesque vehicula. Vestibulum convallis erat massa, et varius sapien ultricies quis. Morbi elementum, ante sed sollicitudin malesuada, ex magna euismod metus, et accumsan est nunc eget lorem. Vivamus ut interdum nisl, accumsan ornare sapien. Maecenas ac vehicula quam. Nulla sit amet dignissim eros. Etiam mauris urna, lacinia vitae blandit iaculis, tristique eget purus. Pellentesque tortor eros, volutpat eu turpis at, auctor varius lorem. Mauris id semper sapien.",
                    createdAt = Date(),
                    updatedAt = Date()
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<Note>> {
        return try {
            delay(1000)
            Result.success(listOf(
                Note(
                    id = Random.nextLong(),
                    text = "Lorem ipsum dolor sit amet\n, consectetur adipiscing elit. Quisque tempus non risus vel aliquam. Vivamus ullamcorper vitae risus non molestie. Praesent elit quam, placerat ut neque et, interdum consequat mauris. Quisque sed sem id augue pellentesque vehicula. Vestibulum convallis erat massa, et varius sapien ultricies quis. Morbi elementum, ante sed sollicitudin malesuada, ex magna euismod metus, et accumsan est nunc eget lorem. Vivamus ut interdum nisl, accumsan ornare sapien. Maecenas ac vehicula quam. Nulla sit amet dignissim eros. Etiam mauris urna, lacinia vitae blandit iaculis, tristique eget purus. Pellentesque tortor eros, volutpat eu turpis at, auctor varius lorem. Mauris id semper sapien.",
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                Note(
                    id = Random.nextLong(),
                    text = "Lorem ipsum dolor sit amet\n, consectetur adipiscing elit. Quisque tempus non risus vel aliquam. Vivamus ullamcorper vitae risus non molestie. Praesent elit quam, placerat ut neque et, interdum consequat mauris. Quisque sed sem id augue pellentesque vehicula. Vestibulum convallis erat massa, et varius sapien ultricies quis. Morbi elementum, ante sed sollicitudin malesuada, ex magna euismod metus, et accumsan est nunc eget lorem. Vivamus ut interdum nisl, accumsan ornare sapien. Maecenas ac vehicula quam. Nulla sit amet dignissim eros. Etiam mauris urna, lacinia vitae blandit iaculis, tristique eget purus. Pellentesque tortor eros, volutpat eu turpis at, auctor varius lorem. Mauris id semper sapien.",
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                Note(
                    id = Random.nextLong(),
                    text = "Lorem ipsum dolor sit amet\n, consectetur adipiscing elit. Quisque tempus non risus vel aliquam. Vivamus ullamcorper vitae risus non molestie. Praesent elit quam, placerat ut neque et, interdum consequat mauris. Quisque sed sem id augue pellentesque vehicula. Vestibulum convallis erat massa, et varius sapien ultricies quis. Morbi elementum, ante sed sollicitudin malesuada, ex magna euismod metus, et accumsan est nunc eget lorem. Vivamus ut interdum nisl, accumsan ornare sapien. Maecenas ac vehicula quam. Nulla sit amet dignissim eros. Etiam mauris urna, lacinia vitae blandit iaculis, tristique eget purus. Pellentesque tortor eros, volutpat eu turpis at, auctor varius lorem. Mauris id semper sapien.",
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                Note(
                    id = Random.nextLong(),
                    text = "Lorem ipsum dolor sit amet\n, consectetur adipiscing elit. Quisque tempus non risus vel aliquam. Vivamus ullamcorper vitae risus non molestie. Praesent elit quam, placerat ut neque et, interdum consequat mauris. Quisque sed sem id augue pellentesque vehicula. Vestibulum convallis erat massa, et varius sapien ultricies quis. Morbi elementum, ante sed sollicitudin malesuada, ex magna euismod metus, et accumsan est nunc eget lorem. Vivamus ut interdum nisl, accumsan ornare sapien. Maecenas ac vehicula quam. Nulla sit amet dignissim eros. Etiam mauris urna, lacinia vitae blandit iaculis, tristique eget purus. Pellentesque tortor eros, volutpat eu turpis at, auctor varius lorem. Mauris id semper sapien.",
                    createdAt = Date(),
                    updatedAt = Date()
                )
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}