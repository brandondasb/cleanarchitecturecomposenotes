package com.lambostudio.cleanarchitecturecomposenotes.di

import android.app.Application
import androidx.room.Room
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.data.data_source.NoteDatabase
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.data.repository.NoteRepositoryImpl
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.repository.NoteRepository
import com.lambostudio.cleanarchitecturecomposenotes.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * u don't want to test your class and useCase,
     * this function provide a note Repository that access our room database
     * you just need sep module that prove a fake note Repo that also implement the interfaces
     *
     * dependency injection will take care of everything else
     */
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUSeCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote =  AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}