package com.example.finalproject_6_1.Ui.setup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_6_1.R
import com.example.finalproject_6_1.Ui.adapter.ItemDialogBottomSheetAdapter
import com.example.finalproject_6_1.Ui.bill_list.BillListActivity
import com.example.finalproject_6_1.data.local.db.AppDatabase
import com.example.finalproject_6_1.data.local.db.model.BillDetail
import com.example.finalproject_6_1.data.local.db.model.BillInfo
import com.example.finalproject_6_1.data.local.db.model.CustomerInfo
import com.example.finalproject_6_1.data.local.db.model.QrCodeInfo
import com.example.finalproject_6_1.databinding.ActivityHomeAdminBinding
import com.example.finalproject_6_1.databinding.FragmentSetupInfoBinding
import com.example.finalproject_6_1.utils.UtilDate
import com.example.finalproject_6_1.utils.UtilDate.insertFirstZero
import com.example.finalproject_6_1.utils.UtilDate.setupStyleDate
import com.example.finalproject_6_1.utils.UtilDate.setupStyleTime
import com.example.finalproject_6_1.utils.UtilRegex
import com.example.finalproject_6_1.utils.UtilWindow
import com.example.finalproject_6_1.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToLong

class SetupActivity : AppCompatActivity() {
    private lateinit var binding: FragmentSetupInfoBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var itemDialogBottomSheetAdapter: ItemDialogBottomSheetAdapter
    private lateinit var recyclerViewBottomSheet: RecyclerView
    private var customerInfoEdit: CustomerInfo? = null
    private var billInfoEdit: BillInfo? = null
    private var billDetailEdit: BillDetail? = null
    private var calendarBirthDay: Calendar? = null
    private var calendar: Calendar? = null
    private var priceI: String = "0"
    private var priceS: String = ""
    companion object {
        private const val SUCCESS = 0
        private const val FAIL = 1

        const val TAX_CHANGE = 0
        const val DEFAULT = 1
        const val LIST_VALUE_FIX = "listValueFix"
    }
    private var exsitBillDetail: List<BillDetail>? = null
    private var calendarStartTimeQR: Calendar? = null
    private var calendarExpiryDateQR: Calendar? = null
    private var isExistTypeTicket = false
    private var isDisQrCode = false
    private var mSecond: Int = 0
    private val  listItemBottomSheet =  ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSetupInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        onClick()
        listenInputValue()
        isDisableViewInfoUser(true)
        isDisableViewQrCode(true)
    }

    private fun initUi() {
//        CoroutineScope(Dispatchers.IO).launch {
//            exsitBillDetail = mViewModel.getBillDetailByTypeTicketUpper(mViewModel.getUsername())
//        }
        calendarBirthDay = Calendar.getInstance()
        calendarStartTimeQR = Calendar.getInstance()
        calendarExpiryDateQR = Calendar.getInstance()
        calendarExpiryDateQR!!.add(Calendar.DATE, 1)
        calendar = Calendar.getInstance()

        calendarStartTimeQR!!.set(
            calendar!!.get(Calendar.YEAR),
            calendar!!.get(Calendar.MONTH),
            calendar!!.get(Calendar.DAY_OF_MONTH),
            calendar!!.get(Calendar.HOUR_OF_DAY),
            calendar!!.get(Calendar.MINUTE),
            0
        )
        val dateText = "${setupStyleDate(calendar!!.get(Calendar.DAY_OF_MONTH), calendar!!.get(Calendar.MONTH), calendar!!.get(Calendar.YEAR))} ${setupStyleTime(calendar!!.get(Calendar.MINUTE), calendar!!.get(Calendar.HOUR_OF_DAY))}"
        val editableDate = Editable.Factory.getInstance().newEditable(dateText)
        binding.blockBillInfor.txtDate.text = editableDate

        binding.blockBillDetail.edtAmount.setText("1")
//        getViewDataBinding().blockBillDetail.edtAmount.transformationMethod = null
//        getViewDataBinding().blockBillDetail.edtMoneyAfterTax.transformationMethod = null
//        getViewDataBinding().blockBillDetail.edtPercentTax.transformationMethod = null

        binding.blockQrCode.edtTimeScan.setText("1")
//        getViewDataBinding().blockQrCode.edtTimeScan.transformationMethod = null
        binding.blockQrCode.txtStartTimeV1.text =
            Editable.Factory.getInstance().newEditable(
                UtilDate.setUpDateFromCalendar(calendarStartTimeQR!!, ::setDate, ::setTime)
            )
    }

    private fun listenInputValue() {
        binding.blockInfoUser.cbGetBill.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
//                getViewDataBinding().edtError.requestFocus()
                cleanInput()
                isDisableViewInfoUser(true)
            }else{
                isDisableViewInfoUser(false)
            }
            customerInfoEdit?.apply {
                buyNotReciver = isChecked
            }
        }
        binding.blockInfoUser.edtBuyer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
//                checkInputObligatory()
                customerInfoEdit?.apply {
                    name = p0.toString().trim()
                }
                if (p0.toString().trim().isEmpty() && binding.blockInfoUser.edtUnitName.text?.trim().isNullOrEmpty()){
                    binding.blockInfoUser.txtUnitNameRed.visibility = View.VISIBLE
                }else if (p0.toString().trim().isEmpty() && binding.blockInfoUser.edtUnitName.text!!.trim().isNotEmpty()){
                    binding.blockInfoUser.txtUnitNameRed.visibility = View.VISIBLE
                    binding.blockInfoUser.txtBuyerRed.visibility = View.GONE
                }else if (p0.toString().trim().isNotEmpty()){
                    binding.blockInfoUser.txtUnitNameRed.visibility = View.GONE
                    binding.blockInfoUser.txtBuyerRed.visibility = View.VISIBLE
                }
            }
        })
        binding.blockInfoUser.edtAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
//                checkInputObligatory()
                customerInfoEdit?.apply {
                    address = p0.toString().trim()
                }
            }
        })
        binding.blockInfoUser.edtUnitName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
//                checkInputObligatory()
                customerInfoEdit?.apply {
                    legalName = p0.toString().trim()
                }
                if (p0.toString().trim().isEmpty() && binding.blockInfoUser.edtBuyer.text?.trim().isNullOrEmpty()){
                    binding.blockInfoUser.txtBuyerRed.visibility = View.VISIBLE
                }else if (binding.blockInfoUser.edtBuyer.text!!.trim().isEmpty()){
                    binding.blockInfoUser.txtBuyerRed.visibility = View.GONE
                    binding.blockInfoUser.txtUnitNameRed.visibility = View.VISIBLE
                }
            }
        })
//        binding.blockInfoUser.edtCodeBuyer.addTextChangedListener {
////            checkInputObligatory()
//            customerInfoEdit?.apply {
//                taxCode = it.toString().trim()
//            }
//        }
        binding.blockInfoUser.edtPhoneNumber.addTextChangedListener {
//            checkInputObligatory()
            customerInfoEdit?.apply {
                phoneNumber = it.toString().trim()
            }
        }
        binding.blockInfoUser.edtEmail.addTextChangedListener {
//            checkInputObligatory()
            customerInfoEdit?.apply {
                email = it.toString().trim()
            }
        }

        binding.blockBillInfor.edtNameCompany.addTextChangedListener{
//            checkInputObligatory()
            billInfoEdit?.apply {
                nameCompany = it.toString().trim()
            }
        }
        binding.blockBillInfor.txtAddress.addTextChangedListener{
//            checkInputObligatory()
            billInfoEdit?.apply {
                address = it.toString().trim()
            }
        }
        binding.blockBillInfor.txtMonney.addTextChangedListener {
            billInfoEdit?.apply {
                moneyType = it.toString().trim()
            }
        }
//        binding.blockBillInfor.txtTaxCode.addTextChangedListener {
//            billInfoEdit?.apply {
//                taxCode = it.toString().trim()
//            }
//        }
        binding.blockBillInfor.txtPayments.addTextChangedListener{
            billInfoEdit?.apply {
                paymentType = it.toString().trim()
            }
        }
        binding.blockBillInfor.txtDate.addTextChangedListener {
            billInfoEdit?.apply {
                date = it.toString().trim()
            }
        }
//        binding.blockBillInfor.txtBillForm.addTextChangedListener {
//            billInfoEdit?.apply {
//                billForm = it.toString().trim()
//            }
//        }
        //block bill detail
        binding.blockBillDetail.txtProperty.addTextChangedListener {
            billDetailEdit?.apply {
                property = it.toString().trim()
            }
        }
        binding.blockBillDetail.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
//                checkInputObligatory()
                billDetailEdit?.apply {
                    amount = p0.toString().trim()
                }
            }
        })
        binding.blockBillDetail.edtTaxMoney.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

//                binding.blockBillDetail.edtTaxMoney.hideIconClear(true)
            }

        })
        var previousMoneyAfterTax = ""
        binding.blockBillDetail.edtMoneyAfterTax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                previousMoneyAfterTax = p0.toString().trim()
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString().trim()
                if (previousMoneyAfterTax != input){
//                    checkInputObligatory()
                    binding.blockBillDetail.edtMoneyAfterTax.removeTextChangedListener(this)
                    if (input.isEmpty()) {
                        binding.blockBillDetail.edtTaxMoney.setText("")
                        binding.blockBillDetail.edtPriceUnit.text = ""
                        binding.blockBillDetail.txtMoneyAfterTax.text = ""
                        binding.blockBillDetail.edtMoneyAfterTax.addTextChangedListener(this)
                        return
                    }
                    if (input.isNotEmpty()){
                        if (!UtilRegex.validateMoney(input) && previousMoneyAfterTax.isNotEmpty()){
                            binding.blockBillDetail.edtMoneyAfterTax.setText(previousMoneyAfterTax)
                            binding.blockBillDetail.edtMoneyAfterTax.setSelection(previousMoneyAfterTax.length)
                        }else{
                            try {
                                priceI = Utils.getClearDotMoney(input)
                                priceS = Utils.getAddDotMoney(priceI)
                                priceI = Utils.getClearDotMoney(priceS)
                                binding.blockBillDetail.edtMoneyAfterTax.setText(priceS)
                                binding.blockBillDetail.txtMoneyAfterTax.text = Utils.convertNumberToTextVND(priceI)
                                binding.blockBillDetail.edtMoneyAfterTax.setSelection(priceS.length)
                                setTextAuto(TAX_CHANGE) // nếu edtTaxMoney cho phép nhập.. chuyển sang DEFAULT
                            }catch (e: Exception){
                                cleanMoney()
                                e.printStackTrace()
                            }
                            billDetailEdit?.apply {
                                priceUnit = input
                            }
                        }
                        binding.blockBillDetail.edtMoneyAfterTax.addTextChangedListener(this)
                    }
                }
            }
        })
        binding.blockBillDetail.edtPercentTax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                setTextAuto(TAX_CHANGE)
                billDetailEdit?.apply {
                    tax = p0.toString().trim()
                }
            }
        })
        binding.blockBillDetail.edtMerchandise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                checkInputObligatory()
            }
            override fun afterTextChanged(p0: Editable?) {
                billDetailEdit?.apply {
                    merchandise = p0.toString().trim()
                }
            }
        })
        /**--------------------------------------------------------------------------------------**/
//        binding.blockBillDetail.edtCodeMerchandise.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                checkInputObligatory()
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                billDetailEdit?.apply {
//                    codeMerchandise = p0.toString().trim()
//                }
//            }
//        })
        binding.blockBillDetail.edtTypeTicket.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                checkInputObligatory()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()){
                    binding.blockBillDetail.txtExsitTypeTicket.visibility = View.GONE
                    return
                }
////                if (!isUpdate) {
//                    isExistTypeTicket = false
//                    if (exsitBillDetail != null && exsitBillDetail!!.isNotEmpty()){
//                        val valueTypeTicket = binding.blockBillDetail.edtTypeTicket.text.toString().trim()
//                        for (bill: BillDetail in exsitBillDetail!!){
//                            if (valueTypeTicket.uppercase() == bill.typeTicket?.uppercase()){
//                                isExistTypeTicket = true
//                                break
//                            }
//                        }
//                    }
//                    if (isExistTypeTicket){
//                        binding.blockBillDetail.txtExsitTypeTicket.visibility = View.VISIBLE
//                    }else{
//                        binding.blockBillDetail.edtTypeTicket.background = ContextCompat.getDrawable(this@SetupActivity, R.drawable.bg_boder_focus_text_field)
//                        binding.blockBillDetail.txtExsitTypeTicket.visibility = View.GONE
//                        isExistTypeTicket = false
//                    }
////                    checkInputObligatory()
////                }

                billDetailEdit?.apply {
                    typeTicket = p0.toString().trim()
                }
            }
        })
        binding.blockBillDetail.edtCalcUnit.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                checkInputObligatory()
            }

            override fun afterTextChanged(p0: Editable?) {
                billDetailEdit?.apply {
                    calcUnit = p0.toString().trim()
                }
            }
        })
        binding.blockBillDetail.edtPercentTax.addTextChangedListener {
            setTextAuto(TAX_CHANGE)
            billDetailEdit?.apply {
                tax = it.toString().trim()
            }
        }
        binding.blockBillDetail.edtTaxMoney.addTextChangedListener {
            billDetailEdit?.apply {
                taxMoney = it.toString().trim()
            }
        }
        binding.blockBillDetail.edtMoneyAfterTax.addTextChangedListener {
            billDetailEdit?.apply {
                totalMoneyAfterTax = it.toString().trim()
            }
        }
        binding.blockQrCode.cbCreateQr.setOnCheckedChangeListener { _, p1 ->
            if (p1){
//                getViewDataBinding().edtError.requestFocus()
                isDisableViewQrCode(false)
                cleanInputQrCode()
            }else{
//                getViewDataBinding().edtError.requestFocus()
//                UtilWindow.hideKeyboard(activity)
                isDisableViewQrCode(true)
            }
        }
        binding.blockQrCode.edtTimeScan.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.blockQrCode.edtTimeScan.text.toString().trim().isEmpty() || binding.blockQrCode.edtTimeScan.text.toString().trim() == "0"){
                    binding.blockQrCode.edtTimeScan.setText("1")
                }else if (binding.blockQrCode.edtTimeScan.text.toString().trim().toInt() <= 99){
//                    binding.blockQrCode.edtTimeScan.focusError(false)
                }
//                binding.blockQrCode.edtTimeScan.hideIconClear(true)
            }else{
//                binding.blockQrCode.edtTimeScan.focusing()
//                getViewDataBinding().blockQrCode.edtTimeScan.hideIconClear(false)
            }
        }
        binding.blockQrCode.edtTimeScan.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                var value = s.toString().trim()
                if (value == "0"){
                    value = "1"
                    binding.blockQrCode.edtTimeScan.removeTextChangedListener(this)
                    binding.blockQrCode.edtTimeScan.setText(value)
                    binding.blockQrCode.edtTimeScan.setSelection(value.length)
                    binding.blockQrCode.edtTimeScan.addTextChangedListener(this)
                }
                if (value.isNotEmpty() && value.toInt() > 99){
//                    binding.blockQrCode.edtTimeScan.setErrorInput(true)
//                    binding.blockQrCode.edtTimeScan.focusError(true)
                    binding.blockQrCode.txtErrorTimeScan.visibility = View.VISIBLE
                }else  {
//                    getViewDataBinding().blockQrCode.edtTimeScan.setErrorInput(false)
//                    getViewDataBinding().blockQrCode.edtTimeScan.focusing()
                    binding.blockQrCode.txtErrorTimeScan.visibility = View.GONE
                }
            }

        })
        binding.blockQrCode.edtExpiryNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                var rangeNumber = s.toString().trim()
                if (rangeNumber.isEmpty()){
                    binding.blockQrCode.txtExpiryDateV1.setText("")
                    binding.blockQrCode.imgClearExpiryDate.visibility = View.GONE
                }else{
                    if (rangeNumber == "0"){
                        binding.blockQrCode.edtExpiryNumber.removeTextChangedListener(this)
                        rangeNumber = "1"
                        binding.blockQrCode.edtExpiryNumber.setText(rangeNumber)
                        binding.blockQrCode.edtExpiryNumber.setSelection(rangeNumber.length)
                        binding.blockQrCode.edtExpiryNumber.addTextChangedListener(this)
                    }
                    if (binding.blockQrCode.edtExpiryNumber.hasFocus()){
                        calendarExpiryDateQR!!.set(
                            calendarStartTimeQR!!.get(Calendar.YEAR),
                            calendarStartTimeQR!!.get(Calendar.MONTH),
                            calendarStartTimeQR!!.get(Calendar.DAY_OF_MONTH),
                            calendarStartTimeQR!!.get(Calendar.HOUR_OF_DAY),
                            calendarStartTimeQR!!.get(Calendar.MINUTE),
                            0
                        )
                        calendarExpiryDateQR!!.add(Calendar.DATE, rangeNumber.toInt())
                    }
                    binding.blockQrCode.txtExpiryDateV1.text = Editable.Factory.getInstance().newEditable(
                        UtilDate.setUpDateFromCalendar(calendarExpiryDateQR!!, ::setDate, ::setTime)
                    )

                    binding.blockQrCode.txtStartTimeV1.text = Editable.Factory.getInstance().newEditable(
                        UtilDate.setUpDateFromCalendar(calendarStartTimeQR!!, ::setDate, ::setTime)
                    )

                    binding.blockQrCode.imgClearExpiryDate.visibility = View.VISIBLE
                }
//                checkInputObligatory()
            }

        })

    }

    private fun setTextAuto(type: Int) {

        val price = priceI
        try {
            when(type){
                TAX_CHANGE ->{
                    val inputPercentTax = binding.blockBillDetail.edtPercentTax.text.toString().trim()
                    val valuePercentTax = binding.blockBillDetail.edtPercentTax.text.toString().trim()
                    if (price.isNotEmpty()) {
                        if (inputPercentTax.isNotEmpty()){
                            val resultPriceUnit: Long = Utils.formatMoney((100.00*price.toDouble()/(100.00+inputPercentTax.toDouble())).toString()).toDouble().roundToLong()
                            val resultTax: Long = Utils.formatMoney((priceI.toDouble() - resultPriceUnit).toString()).toDouble().roundToLong()
                            setMoney(resultTax.toString(), resultPriceUnit.toString())
                            return
                        }else{
                            cleanMoney()
                        }
                        if (valuePercentTax.isNotEmpty()){
                            val resultPriceUnit: Long = Utils.formatMoney((100.00*price.toDouble()/(100.00+Utils.getNumberFromStringPercent(valuePercentTax))).toString()).toDouble().roundToLong()
                            val resultTax: Long = Utils.formatMoney((priceI.toDouble() - resultPriceUnit).toString()).toDouble().roundToLong()
                            setMoney(resultTax.toString(), resultPriceUnit.toString())
                        }else{
                            cleanMoney()
                        }
                    } else {
                        cleanMoney()
                    }
                }
                DEFAULT ->{
                    val moneyTax = binding.blockBillDetail.edtTaxMoney.text.toString().replace(getString(R.string.vnd), "").trim()
                    val resultTax: Double = Utils.getClearDotMoney(moneyTax).toDouble()
                    val resultPriceUnit: Double = price.toDouble() - resultTax
                    val rs = "${Utils.getAddDotMoney(resultPriceUnit.toString())} ${getString(R.string.vnd)}"
                    binding.blockBillDetail.edtPriceUnit.text = rs
                }
            }
        }catch (e: NumberFormatException){
            cleanMoney()
            e.printStackTrace()
        }
    }

    private fun setMoney(_resultTax: String, _resultPriceUnit: String){
        binding.blockBillDetail.edtTaxMoney.setText("${Utils.getAddDotMoney(_resultTax)} ${getString(R.string.vnd)}")
        binding.blockBillDetail.edtPriceUnit.text = "${Utils.getAddDotMoney(_resultPriceUnit)} ${getString(R.string.vnd)}"
        //getViewDataBinding().blockBillDetail.txtMoneyAfterTax.text = UtilsKt.ConvertNumberToTextVND(_resultMoney)
    }

    private fun cleanMoney() {
        binding.blockBillDetail.edtTaxMoney.setText("")
        binding.blockBillDetail.edtPriceUnit.text = "$priceS ${getString(R.string.vnd)}"
    }

//    private fun checkInputObligatory() {
//        if (checkInput(false) == SUCCESS) {
//            enableBtnContinue()
//        } else {
//            disableBtnContinue()
//        }
//        checkInputData()
//    }

    private fun cleanInputQrCode() {
        calendarStartTimeQR = Calendar.getInstance()
        calendarExpiryDateQR = Calendar.getInstance()
        calendarExpiryDateQR!!.add(Calendar.DATE, 1)
        calendar = Calendar.getInstance()

        calendarStartTimeQR!!.set(
            calendar!!.get(Calendar.YEAR),
            calendar!!.get(Calendar.MONTH),
            calendar!!.get(Calendar.DAY_OF_MONTH),
            calendar!!.get(Calendar.HOUR_OF_DAY),
            calendar!!.get(Calendar.MINUTE),
            0
        )
//
        val blQrCode = binding.blockQrCode
        blQrCode.edtTimeScan.setText("1")
//        blQrCode.edtTimeScan.hideIconClear(true)
//        blQrCode.edtTimeScan.focusError(false)
        blQrCode.txtStartTimeV1.text = UtilDate.setUpDateFromCalendar(calendarStartTimeQR!!, ::setDate, ::setTime)
        blQrCode.txtExpiryDateV1.text = ""
        blQrCode.imgClearExpiryDate.visibility = View.GONE
        blQrCode.edtExpiryNumber.setText("")
    }

    private fun isDisableViewQrCode(b: Boolean) {
        isDisQrCode = b
        binding.blockQrCode.edtTimeScan.isEnabled = !b
        binding.blockQrCode.txtStartTimeV1.isEnabled = !b
        binding.blockQrCode.txtExpiryDateV1.isEnabled = !b
        binding.blockQrCode.edtExpiryNumber.isEnabled = !b
        if (b) {
            binding.blockQrCode.edtExpiryNumber.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockQrCode.txtExpiryDateV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockQrCode.txtStartTimeV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockQrCode.edtTimeScan.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
        } else {
            binding.blockQrCode.edtTimeScan.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockQrCode.txtStartTimeV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockQrCode.txtExpiryDateV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockQrCode.edtExpiryNumber.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
        }
    }

    private fun isDisableViewInfoUser(b: Boolean) {
        binding.blockInfoUser.edtBuyer.isEnabled = !b
//        binding.blockInfoUser.edtCodeBuyer.isEnabled = !b
        binding.blockInfoUser.edtAddress.isEnabled = !b
        binding.blockInfoUser.edtPhoneNumber.isEnabled = !b
        binding.blockInfoUser.edtUnitName.isEnabled = !b
        binding.blockInfoUser.edtEmail.isEnabled = !b
//        binding.blockInfoUser.txtBirthdayV1.isEnabled = !b
        if (b) {
            binding.blockInfoUser.edtBuyer.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
//            binding.blockInfoUser.edtCodeBuyer.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockInfoUser.edtAddress.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockInfoUser.edtPhoneNumber.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockInfoUser.edtUnitName.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
            binding.blockInfoUser.edtEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
//            binding.blockInfoUser.txtBirthdayV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_bgcolor_f3f3f3_radius_4dp, null)
        } else {
            binding.blockInfoUser.edtBuyer.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
//            binding.blockInfoUser.edtCodeBuyer.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockInfoUser.edtAddress.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockInfoUser.edtPhoneNumber.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockInfoUser.edtUnitName.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
            binding.blockInfoUser.edtEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
//            binding.blockInfoUser.txtBirthdayV1.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_four_border, null)
        }
    }

    private fun cleanInput() {
        binding.blockInfoUser.edtBuyer.setText("")
//        binding.blockInfoUser.edtCodeBuyer.setText("")
        binding.blockInfoUser.edtAddress.setText("")
        binding.blockInfoUser.edtPhoneNumber.setText("")
        binding.blockInfoUser.edtUnitName.setText("")
        binding.blockInfoUser.edtEmail.setText("")
//        binding.blockInfoUser.txtBirthdayV1.setText("")
    }

    private fun showBottomSheet(listItemBottomSheet: ArrayList<String>) {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet,null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerViewBottomSheet = dialogView.findViewById(R.id.rv_choose_item)
        itemDialogBottomSheetAdapter = ItemDialogBottomSheetAdapter(listItemBottomSheet,object : ItemDialogBottomSheetAdapter.OnItemClickListener {
            override fun onItemClick(selectedItem: String) {
                val newText = Editable.Factory.getInstance().newEditable(selectedItem)
                binding.blockBillInfor.txtPayments.text = newText
                binding.blockBillInfor.txtPayments.setSelection(newText.length)
                dialog.dismiss()
            }
        })
        recyclerViewBottomSheet.adapter = itemDialogBottomSheetAdapter
        dialog.show()
    }

    private fun onClick() {
        binding.blockInfoUser.rlBlockInfoUser.setOnClickListener {
            if (binding.blockInfoUser.llGroupInfoUser.isVisible) {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                binding.blockInfoUser.llGroupInfoUser.visibility = View.GONE
                binding.blockInfoUser.imgDropDownInfoUser.setImageResource(R.drawable.ic_drop_down)
            } else {
                binding.blockInfoUser.llGroupInfoUser.visibility = View.VISIBLE
                binding.blockInfoUser.imgDropDownInfoUser.setImageResource(R.drawable.ic_drop_down_ed)
            }
        }
        binding.blockBillInfor.rlBlockInfoBill.setOnClickListener {
            if (binding.blockBillInfor.llGroupInfoBill.isVisible) {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                binding.blockBillInfor.llGroupInfoBill.visibility = View.GONE
                binding.blockBillInfor.imgDropDownInfoBill.setImageResource(R.drawable.ic_drop_down)
            } else {
                binding.blockBillInfor.llGroupInfoBill.visibility = View.VISIBLE
                binding.blockBillInfor.imgDropDownInfoBill.setImageResource(R.drawable.ic_drop_down_ed)
            }
        }
        binding.blockBillDetail.rlBlockDetailBill.setOnClickListener {
            if (binding.blockBillDetail.llGroupDetailBill.isVisible) {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                binding.blockBillDetail.llGroupDetailBill.visibility = View.GONE
                binding.blockBillDetail.imgBillDetail.setImageResource(R.drawable.ic_drop_down)
            } else {
                binding.blockBillDetail.llGroupDetailBill.visibility = View.VISIBLE
                binding.blockBillDetail.imgBillDetail.setImageResource(R.drawable.ic_drop_down_ed)
            }
        }
        binding.blockQrCode.rlBlockQrCode.setOnClickListener {
            if (binding.blockQrCode.llGroupSetUpTime.isVisible) {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                binding.blockQrCode.llGroupSetUpTime.visibility = View.GONE
                binding.blockQrCode.imgDropDownQrCode.setImageResource(R.drawable.ic_drop_down)
            } else {
                binding.blockQrCode.llGroupSetUpTime.visibility = View.VISIBLE
                binding.blockQrCode.imgDropDownQrCode.setImageResource(R.drawable.ic_drop_down_ed)
            }
        }
        binding.blockBillInfor.txtPayments.setOnClickListener {
            val list = arrayListOf(
                "Chuyển khoản", "Tiền mặt", "CK/TM", "Khác"
            )
            for(i in list){
                listItemBottomSheet.add(i)
            }
            showBottomSheet(list)
        }
        binding.btnCreateSetup.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                createSetup()
            }
        }
        binding.btnBack.setOnClickListener {
            showUnsavedDataAlertDialog()
        }
        binding.btnDeleteDataInput.setOnClickListener {
            showDeleteDataAlertDialog()
        }
        binding.blockQrCode.txtStartTimeV1.setOnClickListener {
//            binding.edtError.requestFocus()
            calendar = Calendar.getInstance()
            val d = DatePickerDialog(this, { _, _yyyy, _mm, _dd  ->
                val mTimePicker = TimePickerDialog(this, { _, _hour, _minute ->
                    if (_yyyy == calendar!!.get(Calendar.YEAR) &&
                        _mm == calendar!!.get(Calendar.MONTH) &&
                        _dd == calendar!!.get(Calendar.DAY_OF_MONTH) &&
                        (_hour < calendar!!.get(Calendar.HOUR_OF_DAY) ||
                                (_hour == calendar!!.get(Calendar.HOUR_OF_DAY) && _minute < calendar!!.get(Calendar.MINUTE))))
                    {
                        calendarStartTimeQR = Calendar.getInstance()
                        Toast.makeText(this, getString(R.string.lost_time_current), Toast.LENGTH_LONG).show()
                    }else {
                        calendarStartTimeQR!!.set(_yyyy, _mm, _dd, _hour, _minute, mSecond)
                    }
                    binding.blockQrCode.txtStartTimeV1.text = Editable.Factory.getInstance().newEditable(UtilDate.setUpDateFromCalendar(calendarStartTimeQR!!, ::setDate,::setTime))
                    if (!binding.blockQrCode.edtExpiryNumber.text.isNullOrEmpty()){
                        calendarExpiryDateQR!!.set(
                            calendarStartTimeQR!!.get(Calendar.YEAR),
                            calendarStartTimeQR!!.get(Calendar.MONTH),
                            calendarStartTimeQR!!.get(Calendar.DAY_OF_MONTH),
                            calendarStartTimeQR!!.get(Calendar.HOUR_OF_DAY),
                            calendarStartTimeQR!!.get(Calendar.MINUTE),
                            0
                        )
                        val rangeNumber = binding.blockQrCode.edtExpiryNumber.text.toString().trim()
                        calendarExpiryDateQR!!.add(Calendar.DATE, rangeNumber.toInt())
                        binding.blockQrCode.txtExpiryDateV1.text = Editable.Factory.getInstance().newEditable(UtilDate.setUpDateFromCalendar(calendarExpiryDateQR!!, ::setDate, ::setTime))
//                        binding.blockQrCode.edtExpiryNumber.hideIconClear(true)
                    }
//                    checkInputObligatory()
                }, calendarStartTimeQR!!.get(Calendar.HOUR_OF_DAY), calendarStartTimeQR!!.get(Calendar.MINUTE), true)
                mTimePicker.show()
            }, calendarStartTimeQR!!.get(Calendar.YEAR), calendarStartTimeQR!!.get(Calendar.MONTH), calendarStartTimeQR!!.get(Calendar.DAY_OF_MONTH))
            d.datePicker.minDate = calendar!!.time.time
            d.show()
        }

        binding.blockQrCode.txtExpiryDateV1.setOnClickListener {
//            binding.edtError.requestFocus()
            if (!binding.blockQrCode.imgClearExpiryDate.isVisible){
                calendarExpiryDateQR!!.set(
                    calendarStartTimeQR!!.get(Calendar.YEAR),
                    calendarStartTimeQR!!.get(Calendar.MONTH),
                    calendarStartTimeQR!!.get(Calendar.DAY_OF_MONTH),
                    calendarStartTimeQR!!.get(Calendar.HOUR_OF_DAY),
                    calendarStartTimeQR!!.get(Calendar.MINUTE),
                    0
                )
                calendarExpiryDateQR!!.add(Calendar.DATE, 1)
            }
            val d = DatePickerDialog(this, { _, _yyyy, _mm, _dd  ->
                val mTimePicker = TimePickerDialog(this, { _, _hour, _minute ->
                    calendarExpiryDateQR!!.set(_yyyy, _mm, _dd, _hour, _minute, mSecond)
                    binding.blockQrCode.txtExpiryDateV1.text = Editable.Factory.getInstance().newEditable(UtilDate.setUpDateFromCalendar(calendarExpiryDateQR!!, ::setDate, ::setTime))
                    binding.blockQrCode.imgClearExpiryDate.visibility = View.VISIBLE
                    binding.blockQrCode.edtExpiryNumber.setText(UtilDate.rangeBetweenTwoDate(calendarStartTimeQR!!, calendarExpiryDateQR!!).toString())
//                    binding.blockQrCode.edtExpiryNumber.hideIconClear(true)
                }, calendarExpiryDateQR!!.get(Calendar.HOUR_OF_DAY), calendarExpiryDateQR!!.get(Calendar.MINUTE), true)
                mTimePicker.show()
            }, calendarExpiryDateQR!!.get(Calendar.YEAR), calendarExpiryDateQR!!.get(Calendar.MONTH), calendarExpiryDateQR!!.get(Calendar.DAY_OF_MONTH))
            d.datePicker.minDate = calendarStartTimeQR!!.time.time+86400000
            d.datePicker.maxDate = calendarStartTimeQR!!.timeInMillis+86313600000
            d.show()
        }
    }

    private fun showDeleteDataAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Xác nhận xóa dữ liệu")
        alertDialogBuilder.setMessage("Bạn có chắc chắn muốn xóa toàn bộ dữ liệu? Thao tác này không thể hoàn tác.")
        alertDialogBuilder.setPositiveButton("Xác nhận") { _, _ ->
            // Xử lý logic xóa toàn bộ dữ liệu ở đây
            clearAllData()
        }
        alertDialogBuilder.setNegativeButton("Hủy", null)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun clearAllData() {
        cleanEdtInputData(binding.rlMainScreen)
    }

    private fun showUnsavedDataAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Cảnh báo")
        alertDialogBuilder.setMessage("Có vẻ bạn có thông tin chưa được lưu. Bạn có chắc muốn quay lại?")
        alertDialogBuilder.setPositiveButton("Quay lại") { _, _ ->
            navigateToSetupActivity()
        }
        alertDialogBuilder.setNegativeButton("Ở lại", null)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private suspend fun createSetup() {
        val buyerNotGetTicket = if (binding.blockInfoUser.cbGetBill.isChecked){ 1 }else{ 0 }
        val customerInfo = if (buyerNotGetTicket == 1){
            CustomerInfo(buyNotReciver = true, name = "", address = "", legalName = "", phoneNumber = "", email = "")
        }else{
            CustomerInfo(
                buyNotReciver = false,
                name = binding.blockInfoUser.edtBuyer.toString(),
                address = binding.blockInfoUser.edtAddress.toString(),
                legalName = binding.blockInfoUser.edtUnitName.toString(),
                phoneNumber = binding.blockInfoUser.edtPhoneNumber.toString(),
                email = binding.blockInfoUser.edtEmail.toString()
            )
        }
        val billInfo = BillInfo(
            nameCompany = binding.blockBillInfor.edtNameCompany.toString(),
            address = binding.blockBillInfor.txtAddress.toString(),
            paymentType = binding.blockBillInfor.txtPayments.toString(),
            moneyType =  binding.blockBillInfor.txtMonney.toString(),
            date = binding.blockBillInfor.txtDate.toString()
        )
        val hasQRCode = if (isDisQrCode){ 0 }else{ 1 }
        val qrCodeInfo = if (hasQRCode == 1){
            QrCodeInfo(
                isCreate = true,
                totalQrcode = binding.blockQrCode.edtTimeScan.text.toString().trim(),
                startDateQrcode = UtilDate.convertDateTimeToString(calendarStartTimeQR!!.time, UtilDate.TIME_ISO_8601),
                endDateQrcode = UtilDate.convertDateTimeToString(calendarExpiryDateQR!!.time, UtilDate.TIME_ISO_8601)
            )
        } else{
            QrCodeInfo(isCreate = false,totalQrcode="",startDateQrcode="",endDateQrcode="")
        }
        val billDetail = BillDetail(
            property= binding.blockBillDetail.txtProperty.toString(),
            merchandise = binding.blockBillDetail.edtMerchandise.toString(),
            typeTicket = binding.blockBillDetail.edtTypeTicket.toString(),
            calcUnit = binding.blockBillDetail.edtCalcUnit.toString(),
            amount = binding.blockBillDetail.edtAmount.toString(),
            priceUnit = binding.blockBillDetail.edtPriceUnit.toString(),
            tax = binding.blockBillDetail.edtPercentTax.toString(),
            taxMoney = binding.blockBillDetail.edtTaxMoney.toString(),
            totalMoneyAfterTax = binding.blockBillDetail.edtMoneyAfterTax.toString()

        )
        withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(this@SetupActivity).customerInfoDao().insert(customerInfo)
            AppDatabase.getDatabase(this@SetupActivity).billInfoDao().insert(billInfo)
            AppDatabase.getDatabase(this@SetupActivity).qrCodeInfoDao().insert(qrCodeInfo)
            AppDatabase.getDatabase(this@SetupActivity).billDetailDao().insert(billDetail)
        }


    }

    private fun cleanEdtInputData(parentLayout: ViewGroup) {
        for (count in 0 until parentLayout.childCount) {
            val view: View = parentLayout.getChildAt(count)
            if (view is EditText) {
                view.setText("")
                binding.blockBillInfor.txtMonney.setText("VNĐ")
                binding.blockBillDetail.txtProperty.setText("Hàng hóa")
            } else if (view is ViewGroup) {
                cleanEdtInputData(view)
            }
        }
    }

    private fun navigateToSetupActivity() {
        val intent = Intent(this, BillListActivity::class.java)
        startActivity(intent)
    }
    fun setDate(dd: Int, mm: Int, yyyy: Int) = insertFirstZero(dd.toString()) +"/"+ insertFirstZero(mm.toString()) +"/"+yyyy

    fun setTime(hh: Int, mm: Int, ss: Int) = insertFirstZero(hh.toString()) +":"+ insertFirstZero(mm.toString()) +":"+ insertFirstZero(ss.toString())
}