package com.example.finalproject_6_1.utils

object Constants {
    const val PREF_NAME = "hddt_pref"
    const val DEFAULT_LANGUAGE = "vi"
    const val PREF_KEY_USERNAME = "PREF_KEY_USERNAME"
    const val PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD"
    const val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"
    const val PREF_DELETE_TICKETPRINTED_AFTER_31_DAYS = "PREF_DELETE_TICKETPRINTED_AFTER_31_DAYS"
    const val PREF_KEY_SUPPORT_PRINT = "PREF_KEY_SUPPORT_PRINT"
    const val UPDATE_DATA_OLD = "UPDATE_DATA_OLD"
    const val DB_NAME = "hddt.db"
    const val TYPE_SETTING_INFO: Int = 0
    const val TYPE_SETTING_ACTION: Int = 1
    const val TYPE_LOG_OUT: Int = 2
    const val IS_FIRST_LOGIN_APP = "IS_FIRST_LOGIN_APP"
    const val MAX_SIZE_ITEM = 50
    const val SAVE_DB_FAIL = -1L
    const val DELETE_DB_SUCCESS = 1
    const val CAMERA_REQUEST = 1888
    const val DEVICE_NAME_HPRT_PRINTER = "TP808������������������������������������������������������"
    const val DEVICE_NAME_X_PRINTER = "Printer001"
    const val USB_PRINTER_PORT = "USB Printer Port"
    const val BASE_URL = "https://api-vinvoice.viettel.vn"

    const val CUSTOMERINFO = "CUSTOMERINFO"
    const val BILLINFO = "BILLINFO"
    const val ADDINFOR = "ADDINFOR"
    const val BILLDETAIL = "BILLDETAIL"
    const val ISNULL = "isnull"
    const val DISABLE = -92475624
    const val ENABLE = -97632547

    const val LOAD_TICKET_BUY = 1
    const val LOAD_TICKET_EXIST = 2
    const val PRINT = 5
    const val NOT_PRINT = 6
    const val NOT_DROPDOWN = 7
    const val DROPDOWN = 8
    const val MINUS_TICKET_PRINT = 9
    const val DELETE_TICKET = 10
    const val PRICE_TICKET = 11
    const val CHANGE_NAME_USER = 12
    const val CHANGE_ADDRESS = 13
    const val CHANGE_ADD_INFO = 14
    const val CHANGE_CUSTOMER = 15
    const val CHANGE_QRCODE = 16
    const val KEY_QUANTITY_PETRO = 17
    const val KEY_TOTAL_PRICE = 18
    //-------------------------------
    /** type object **/
    const val TYPE_OBJECT_CUSTOMER = 0
    const val TYPE_OBJECT_ADD_INFO = 1
    const val TYPE_OBJECT_QR_CODE = 2

    /** key **/
    //---Customer
    const val KEY_NAME = "0"
    const val KEY_BUYERCODE = "1"
    const val KEY_BUYERBIRTHDAY = "2"
    const val KEY_ADDRESS = "3"
    const val KEY_LEGALNAME = "4"
    const val KEY_TAXCODE = "5"
    const val KEY_POSTALCODE = "6"
    const val KEY_PHONENUMBER = "7"
    const val KEY_EMAIL = "8"
    const val KEY_TYPEPAPER = "9"
    const val KEY_IDNO = "10"
    const val KEY_BANKNAME = "11"
    const val KEY_BANKACCOUNT = "12"

    //---QrCode
    const val KEY_TOTAL_QRCODE = "13"
    const val KEY_START_DATE_QRCODE = "14"
    const val KEY_EXPIRY_DATE_QRCODE = "15"
    const val KEY_EXPIRY_NUMBER = "16"
    const val KEY_SUM_AMOUNT = "17"


    //-------------------------------
    /** status printed ticket **/
    const val NOT_SCAN = 0 // chua quet ve
    const val SCANNED = 1 // da quet ve
    const val CANCELED = 2 // da huy ve
    const val TIME_OUT = 3 // phat hanh nhung chua in duoc ve (time out)
    const val GET_SUCCESS_TIME_OUT = 4 // da lay lai thanh cong ve time out
    const val GET_FAIL_TIME_OUT = 5 // khong lay lai duoc ve time out

    const val OPEN_SCAN_TICKET = "9x12R34sDV2"

    /** get data invoice **/
    const val PAGE = 0
    const val SIZE = 50
    const val SORT = "desc"
    const val SUCCESS_1 = 200
    const val SUCCESS_2 = 201
    const val INVALID_DATA = 400
    const val UNCONFIRMED = 401
    const val NOT_ACCESS = 403
    //const val NOT_FOUND = 404

    /** Print type metaData **/
    const val ALL = "ALL"
    const val NAME = "NAME"
    const val VALUE = "VALUE"

    /** format number **/
    const val SIZE_NUMBER_DECIMAL = 4 // max 4
    const val MAX_SIZE_NUMBER = 15

    /** format input money **/
    const val FORMAT_INPUT_MONEY = "0123456789," //0123456789, -> numberDecimal

    /** Excel **/
    const val WRITE_FILE_REQUEST = 1999
    /****/
    const val SELECT_IMAGE_REQUEST = 1013
}