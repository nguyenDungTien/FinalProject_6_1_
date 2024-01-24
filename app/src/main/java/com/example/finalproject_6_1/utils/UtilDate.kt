package com.example.finalproject_6_1.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
@SuppressLint("SimpleDateFormat")
object UtilDate {
    const val DATE_FORMAT_SS = "dd/MM/yyyy HH:mm:ss"
    const val DATE_FORMAT_TIME = "dd/MM/yyyy - hh:mm a"
    const val DATE_FORMAT_TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss'-05:00'"
    const val TIME_ISO_8601 = "yyyy-MM-dd'T'hh:mm:ss.SSSZ"

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------SET UP DATE-----------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun setUpDateFromCalendar(calendar: Calendar, setDate:(Int, Int, Int) -> String, setTime:(Int, Int, Int) -> String): String{ // dd/mm/yyyy hh:mm:ss
        return setDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(
            Calendar.YEAR))+" "+setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(
            Calendar.SECOND))
    }
    fun setUpOnlyDateFromCalendar(calendar: Calendar, setDate:(Int, Int, Int) -> String): String{ // dd/mm/yyyy
        return setDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(
            Calendar.YEAR))
    }

    /**-----------------------------------------------------------------------------------------
     * -----------------------------------RANGE BETWEEN TWO DATE---------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun rangeBetweenTwoDate(calendarFrom: Calendar, calendarUntil: Calendar): Int{
        var count = 0
        while (calendarFrom <= calendarUntil) {
            calendarFrom.add(Calendar.DATE, 1)
            count ++
        }
        if (count > 0){
            calendarFrom.add(Calendar.DATE, 0-count)
        }
        return --count
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------TIME EPOCH------------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun convertEpoch(_date: String): String {
        val l = _date.length
        return if (l > 3){
            val date = _date.substring(0, l-3)
            "${date}000"
        }else{
            _date
        }
    }

    fun insertFirstZero(s: String): String{
        return if (s.length == 1) {
            "0$s"
        } else {
            s
        }
    }


    fun setupStyleDate(dd: Int, mm: Int, yy: Int): String {
        val month: String = if (mm + 1 < 10) "0" + (mm + 1) else "" + (mm + 1)
        val day: String = if (dd < 10) "0$dd" else "" + dd

        return "$day/$month/$yy"
    }

    fun setupStyleTime(minute: Int, hourOfDay: Int): String {
        val hour = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val min = if (minute < 10) "0$minute" else "" + minute

        return "$hour:$min"
    }

    fun convertDateTimeToString(date: Date?, pattern: String?): String? {
        val dateFormat = SimpleDateFormat(pattern)
        return try {
            if (date == null) {
                null
            } else dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun convertLongTimeToString(longTime: Long, pattern: String?): String {
        val dateFormat = SimpleDateFormat(pattern)
        val date = Date()
        date.time = longTime
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}