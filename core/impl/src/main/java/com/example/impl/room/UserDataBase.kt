package com.example.impl.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.impl.presentation.registration.RegistrationFragment.Companion.DB_NAME
import com.example.impl.room.dao.UserDao
import com.example.impl.room.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var database: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    DB_NAME
                ).build()
            }
            return database as UserDataBase
        }
    }
}