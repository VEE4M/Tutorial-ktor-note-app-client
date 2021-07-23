package com.androiddevs.ktornoteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.ktornoteapp.data.local.entities.LocallyDeletedNoteID
import com.androiddevs.ktornoteapp.data.local.entities.Note

/**
 *Veli-Matti Tikkanen, 21.6.2021
 */
@Database(entities = [Note::class, LocallyDeletedNoteID::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase(): RoomDatabase() {

    abstract fun noteDao(): NoteDao

}