package com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.use_case

/**
 * using this wot wraps all senior features in 1 class to make it much cleaner
 */
data class NoteUseCases (
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)
