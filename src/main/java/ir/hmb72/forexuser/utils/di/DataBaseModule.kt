package ir.hmb72.forexuser.utils.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hmb72.forexuser.data.database.NoteDatabase
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.data.model.local.room.PointEntity
import ir.hmb72.forexuser.utils.NOTE_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTE_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db:NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun provideEntity()= NoteEntity()

    @Provides
    @Singleton
    fun provideDaoPoint(db:NoteDatabase) = db.pointDao()

    @Provides
    @Singleton
    fun providePointEntity()= PointEntity()



}