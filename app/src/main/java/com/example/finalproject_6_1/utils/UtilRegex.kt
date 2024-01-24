package com.example.finalproject_6_1.utils

import com.example.finalproject_6_1.utils.Constants.MAX_SIZE_NUMBER
import com.example.finalproject_6_1.utils.Constants.SIZE_NUMBER_DECIMAL
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object UtilRegex {
    private const val EMAIL = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z.]+"

    private val MONEY = "[0-9.]{0,%d}".format(MAX_SIZE_NUMBER)
    private val MONEY_DECIMAL = "[0-9.]{0,%d}[,]?[0-9]{0,%d}".format(MAX_SIZE_NUMBER, SIZE_NUMBER_DECIMAL)

    const val SYMBOL = "[cC]{1}[a-zA-Z0-9]{2}M[a-zA-Z0-9]+"

    /**-----------------------------------------------------------------------------------------
     * ---------------------------------VALIDATE WITH MONEY----------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun validateMoney(input: String): Boolean{
        val pattern1: Pattern = Pattern.compile(MONEY)
        return if (pattern1.matcher(input).matches()){ true }else{
            if (input.substringAfter(",").length <= MAX_SIZE_NUMBER){
                val pattern2: Pattern = Pattern.compile(MONEY_DECIMAL)
                return pattern2.matcher(input).matches()
            }else{
                false
            }
        }
    }

    /**-----------------------------------------------------------------------------------------
     * ---------------------------------VALIDATE WITH REGEX----------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun validateAny(regex: String, input: String): Boolean{
        val pattern: Pattern = Pattern.compile(regex)
        return pattern.matcher(input).matches()
    }

    /**-----------------------------------------------------------------------------------------
     * ------------------------------------VALIDATE EMAIL---------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun validateEmail(email: String?): Boolean{
        if (email.isNullOrEmpty() || email == Constants.ISNULL || email == "null"){
            return true
        }
        val pattern: Pattern = Pattern.compile(EMAIL)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**-----------------------------------------------------------------------------------------
     * ------------------------------------VALIDATE TIME---------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun convertTimestampToDateString(timestamp: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(timestamp.toLong())
        return dateFormat.format(date)
    }
    /**-----------------------------------------------------------------------------------------
     * ------------------VALIDATE FORMAT NUMBER TO FOUR DECIMAL PLACES--------------------------
     * -----------------------------------------------------------------------------------------*/
    fun formatStringToFourDecimalPlaces(input: String): String {
        val originalNumber = input.toDoubleOrNull() ?: return "Invalid input"
        val roundedValue = Math.round(originalNumber * 10000.0) / 10000.0
        return roundedValue.toString()
    }
}