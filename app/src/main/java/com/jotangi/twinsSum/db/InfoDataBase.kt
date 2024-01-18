package com.jotangi.twinsSum.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [InfoTable::class],
    version = 1,
    exportSchema = true
)
abstract class InfoDataBase : RoomDatabase() {

    abstract fun getInfoDao(): InfoDao
}