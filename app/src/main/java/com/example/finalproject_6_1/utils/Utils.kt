@file:Suppress("DEPRECATION")

package com.example.finalproject_6_1.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.*
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.utils.Constants.SIZE_NUMBER_DECIMAL
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern


object Utils {

    /**-----------------------------------------------------------------------------------------
     * -------------------------------------CHECK TYPE TICKET--------------------------------------
     * -----------------------------------------------------------------------------------------*/
//    fun convertStatusTicketToText(status: Int, context: Context): String{
//        return when(status){
//            Constants.NOT_SCAN ->{
//                context.getString(R.string.thanh_cong)
//            }
//            Constants.SCANNED ->{
//                context.getString(R.string.da_quet_ve)
//            }
//            Constants.CANCELED ->{
//                context.getString(R.string.ve_da_huy)
//            }
//            Constants.TIME_OUT ->{
//                context.getString(R.string.time_out)
//            }
//            Constants.GET_SUCCESS_TIME_OUT ->{
//                context.getString(R.string.da_lay_lai_so_ve)
//            }
//            Constants.GET_FAIL_TIME_OUT ->{
//                context.getString(R.string.phat_hanh_fail)
//            }
//            else ->{
//                ""
//            }
//        }
//    }

    /**-----------------------------------------------------------------------------------------
     * -------------------------------------CHECK TYPE TICKET--------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun checkTypeTicket(typeTicket: String): Int{
        // 1 chi in KH, DC
        // 2 in KH,DC, MCQT
        // 3 in chi in MCQT
        // 4 khong in gio, phut, giay
        // 0 ko in KH, DC, MCQT
        return when(typeTicket){
            "5/0017", "5/0010", "5/0011", "5/0021", "5/0029" ->{
                1
            }
            "5/0014", "5/0015", "5/0016", "5/0018", "5/0031" ->{
                2
            }
            "5/0030" ->{
                3
            }
            "5/0032" ->{
                4
            }
            else ->{
                0
            }
        }
    }

    /**-----------------------------------------------------------------------------------------
     * -------------------------------------CUSTOM TOAST--------------------------------------
     * -----------------------------------------------------------------------------------------*/
//    @SuppressLint("WrongConstant")
//    fun showCustomToast(message: String, activity: Activity)
//    {
//        val toast = Toast(activity)
//        val layout = activity.layoutInflater.inflate (R.layout.layout_custom_toast, activity.findViewById(R.id.toast_container))
//        val textView = layout.findViewById<TextView>(R.id.toast_text)
//        textView.text = message
//        toast.apply {
//            setGravity(Gravity.TOP, 0, 0)
//            duration = 10000
//            view = layout
//            show()
//        }
//    }

    /**-----------------------------------------------------------------------------------------
     * ------------------------------------REMOVE TEXT UNI KEY-----------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun deAccent(str: String?): String {
        try {
            val temp = Normalizer.normalize(str, Normalizer.Form.NFD)
            val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            return pattern.matcher(temp).replaceAll("").replace("đ".toRegex(), "d").replace("Đ".toRegex(), "D")
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    /**-----------------------------------------------------------------------------------------
     * ------------------------------GET TAX CODE FROM USER NAME---------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun validateTaxCode(taxCode: String): String{
        val index: Int = taxCode.indexOf("_")
        return if (index  != -1){
            taxCode.substring(0, index)
        }else{
            taxCode
        }
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------FORMAT MONEY---------------------------------------
     * -----------------------------------------------------------------------------------------*/
//    fun formatMoney(d: String): String {
//        if (d.isEmpty()) return ""
//        val number = d.toDouble()
//        val decimalFormat = DecimalFormat("#.####")
//        return decimalFormat.format(number).replace(",", ".")
//    }

    /**-----------------------------------------------------------------------------------------
     * -----------------------------------ADD DOT TO NUMBER-------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun getAddDotMoney(value: String?): String {
        try {
            if (value.isNullOrEmpty()) return ""
            val isSplit = (value.contains('.') && !value.contains("E"))
            val number = if (isSplit){
                value.split('.')[0].toDouble()
            }else{
                value.toDouble()
            }
            val symbols = DecimalFormatSymbols(Locale("vi", "VN"))
            symbols.currencySymbol = ""
            val formatter = DecimalFormat("#,##0.####", symbols)
            return formatter.format(number) + if (isSplit){","+if (value.split('.')[1].length > 4){ value.split('.')[1].substring(0, 4) }else{ value.split('.')[1] } }else{""}
        }catch (e: NumberFormatException){
            e.printStackTrace()
            return value.toString()
        }
    }

    /**-----------------------------------------------------------------------------------------
     * -----------------------------------FORMAT NUMBER-------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun formatMoney(value: String?): String {
        try {
            if (value.isNullOrEmpty()) return ""
            val number = value.toDouble()
            return String.format("%.4f", number).replace(",", ".")
        }catch (e: NumberFormatException){
            e.printStackTrace()
            return value.toString()
        }
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------REMOVE DOT FROM NUMBER-----------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun getClearDotMoney(s: String?): String {
        return try {
            if (s.isNullOrEmpty()){
                return ""
            }
            s.replace(".", "").replace(",", ".")
        } catch (e: Exception) {
            ""
        }
    }

    /**-----------------------------------------------------------------------------------------
     * -----------------------------------ADD DOT TO NUMBER-------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun getNumberFromStringPercent(s: String): Int {
        return try {
            val arr = s.split("%").toTypedArray()
            return arr[0].toInt()
        } catch (e: Exception) {
            0
        }
    }

    /**-----------------------------------------------------------------------------------------
     * ---------------------------------READ NUMBER TO TEXT------------------------------------
     * -----------------------------------------------------------------------------------------*/
    private val arr1 = arrayOf(
        " không", " mười", " hai mươi", " ba mươi", " bốn mươi", " năm mươi",
        " sáu mươi", " bảy mươi", " tám mươi", " chín mươi"
    )
    private val arr2 = arrayOf(
        " không", " một", " hai", " ba", " bốn", " năm", " sáu", " bảy", " tám", " chín"
    )
    private val arr3 = arrayOf(
        "", " mốt", " hai", " ba", " bốn", " lăm", " sáu", " bảy", " tám", " chín", " mười",
        " mười một", " mười hai", " mười ba", " mười bốn", " mười lăm", " mười sáu",
        " mười bảy", " mười tám", " mười chín"
    )

    fun convertNumberToTextVND(value: String): String {
        var valueReturn = ""
        val number = value.split(".").toTypedArray()
        repeat(number.size){
            if (it == 1){
                valueReturn += " phẩy"
                if (number[it].isEmpty()) return valueReturn
            }
            if (number[it] == "0") {
                valueReturn += " không"
                if (it == 1){
                    return "$valueReturn đồng"
                }
            }
            val sNumber: String
            val mask = "000000000000000000000"
            val df = DecimalFormat(mask)
            sNumber = df.format(number[it].toLong()).toString()
            val bBillions = sNumber.substring(0, 3).toInt()
            val mBillions = sNumber.substring(3, 6).toInt()
            val hBillions = sNumber.substring(6, 9).toInt()
            val billions = sNumber.substring(9, 12).toInt()
            val millions = sNumber.substring(12, 15).toInt()
            val hundredThousands = sNumber.substring(15, 18).toInt()
            val thousands = sNumber.substring(18, 21).toInt()
            var result = if (it == 0){
                tradFirst(" triệu ", mBillions)+
                        tradFirst(" nghìn ", hBillions)+
                        tradFirst(" tỷ ", billions)+
                        tradFirst(" triệu ", millions)+
                        tradFirst(" nghìn ", hundredThousands)+
                        tradFirst("", thousands)
            }else{
                if (number[it].length == SIZE_NUMBER_DECIMAL && number[it].toInt() > 0){
                    tradLast(" nghìn", ""+number[it].toInt()/1000) +
                            tradLast("", ""+number[it].substring(1, SIZE_NUMBER_DECIMAL))
                }else{
                    tradLast("", number[it])
                }
            }
            if (bBillions != 0){
                result = arr2[bBillions] + " tỷ tỷ " + result
            }
            valueReturn += if (it == 1){
                result.toLowerCase(Locale.getDefault())
            }else{
                returnResult(result)
            }
        }
        return "$valueReturn đồng"
    }

    private fun tradFirst(dv: String, n: Int): String{
        val resultTrad: String = when (n) {
            0 -> {
                when (dv.trim()) {
                    "tỷ","" -> {
                        dv.trim()
                    }
                    else -> {
                        " "
                    }
                }
            }
            else -> convertMoneyFirst(n)+dv
        }
        return resultTrad
    }

    private fun tradLast(dv: String, n: String): String{
        val resultTrad: String = when (dv.trim()) {
            "nghìn" -> {
                arr2[n.toInt()]+dv
            }
            else ->{
                convertMoneyLast(n)+dv
            }
        }
        return resultTrad
    }

    private fun convertMoneyFirst(_number: Int): String {
        var number = _number
        var soFar: String
        if (number % 100 < 20) {
            if (number % 100 in 1..9){
                soFar = " lẻ" + arr2[number % 100]
                number /= 100
            }else{
                soFar = arr3[number % 100]
                number /= 100
            }
        } else {
            soFar = arr3[number % 10]
            number /= 10
            soFar = arr1[number % 10] + soFar
            number /= 10
        }
        return arr2[number] + " trăm" + soFar
    }

    private fun convertMoneyLast(_number: String): String {
        val number = _number.toInt()
        var value = ""
        if (number == 0){
            return " không"
        }
        fun read1(n: Int): String{
            return arr2[n]
        }
        fun read2(n: Int): String{
            return when (n){
                in 0..9 ->{
                    " không" + arr2[n]
                }
                in 10 ..19 ->{
                    arr3[n]
                }
                else ->{
                    arr1[n/10]+ arr3[n%10]
                }
            }
        }
        fun read3(n: Int): String{
            return when (n) {
                in 1..9 -> {
                    " không trăm lẻ" + read1(n)
                }
                in 10..99 -> {
                    " không trăm" + read2(n)
                }
                else -> {
                    arr2[n / 100] + " trăm" + when (n % 100) {
                        0 ->{
                            ""
                        }
                        in 1..9 -> {
                            " lẻ" + arr2[n % 10]
                        }
                        else ->{
                            read2(n % 100)
                        }
                    }
                }
            }
        }
        when(_number.length){
            1 ->{
                value = read1(number)
            }
            2->{
                value = read2(number)
            }
            3->{
                value = read3(number)
            }
        }
        return value
    }

    private fun returnResult(_result: String): String{
        var result = _result.trim()

        if (result.startsWith("tỷ")){
            result = result.trim().substring(3)
        }
        if (result.trim().startsWith("k")){
            result = result.trim().substring(11)
        }
        if (result.trim().startsWith("l")){
            result = result.trim().substring(3)
        }
        result = result.trim().substring(0,1).uppercase() + result.trim().substring(1)
        return result.replace("  ", " ").replace("  ", " ")
    }
}
