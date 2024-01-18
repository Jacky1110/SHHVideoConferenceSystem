package com.jotangi.twinsSum.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jotangi.twinsSum.utils.Const
import java.time.LocalDateTime


@Entity(tableName = Const.infoTableName)
data class InfoTable(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = Const.infoKey)
    var key: String? = null,
    @ColumnInfo(name = Const.infoValue)
    var value: String? = null,
    @ColumnInfo(name = Const.infoTime)
    var time: String? = LocalDateTime.now().toString(),
)
