package com.yoenas.notesapp.data

import androidx.lifecycle.LiveData
import com.yoenas.notesapp.data.entity.Notes
import com.yoenas.notesapp.data.room.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes: LiveData<List<Notes>> = notesDao.getAllNotes()
    val sortByHighPriority: LiveData<List<Notes>> = notesDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<Notes>> = notesDao.sortByLowPriority()

    suspend fun insertNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        notesDao.updateNotes(notes)
    }

    suspend fun deleteNotes(notes: Notes) {
        notesDao.deleteNotes(notes)
    }

    suspend fun deleteAll() {
        notesDao.deleteAll()
    }

    fun searchNoteByQuery(searchQuery: String): LiveData<List<Notes>> {
        return notesDao.searchNoteByQuery(searchQuery)
    }
}