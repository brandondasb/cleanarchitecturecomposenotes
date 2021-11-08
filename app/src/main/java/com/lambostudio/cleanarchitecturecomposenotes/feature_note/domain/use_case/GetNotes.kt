package com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.use_case

import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.model.Note
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.repository.NoteRepository
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.util.NoteOrder
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    /**
     * use case should only have 1 public fun that can be called from the outside
     * private function are ok for utility functions, to help with code readability.
     * AS the name Use case focuses on 1 usecase.
     * People tend to call it an execute function, here we're using an operator invoke function,
     * this lets us call it the UseCase like a function
     *
     * this useCase also contain the sorting function, it makes use of the noteOrder
     * passed to the invoke function, and uses it to sort data before returning it
     */
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}