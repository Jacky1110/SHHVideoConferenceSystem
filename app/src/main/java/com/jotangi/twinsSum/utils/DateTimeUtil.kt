package com.jotangi.twinsSum.utils

import android.app.Activity
import android.app.DatePickerDialog
import timber.log.Timber
import java.time.LocalDate
import java.time.Period

class DateTimeUtil {

    private fun fixLength(i: Int) = if (i < 10) "0$i" else "$i"

    fun selectDay(
        date: String,
        activity: Activity,
        dateTxt: (String) -> Unit
    ) {

        val localDate = kotlin.runCatching {
            LocalDate.parse(date)
        }.getOrDefault(LocalDate.now())

        val dialog = DatePickerDialog(activity, { _, year, month, day ->

            val dayValue = "${year}-${fixLength(month + 1)}-${fixLength(day)}"
            Timber.w("dayValue: $dayValue")

            dateTxt(dayValue)
        }, localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)

        dialog.datePicker.maxDate = System.currentTimeMillis()

        dialog.show()
    }

    fun howOldMuch(day: String?) = kotlin.runCatching {
        Period.between(LocalDate.parse(day), LocalDate.now()).years.toString()
    }.getOrDefault("")
}