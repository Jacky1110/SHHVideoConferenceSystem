package com.jotangi.twinsSum.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jotangi.twinsSum.utils.Const


@Dao
interface InfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(table: InfoTable)

    @Query(
        "SELECT ${
            Const.infoValue
        } FROM ${
            Const.infoTableName
        } WHERE ${
            Const.infoKey
        } LIKE :infoKey"
    )
    fun keyByValue(infoKey: String): String?

    @Query("DELETE FROM ${Const.infoTableName}")
    fun emptyAll()
}