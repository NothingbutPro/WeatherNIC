package com.parag.indianicweather.di


/**
 * Api interface
 *
 * @constructor Create empty Api interface
 * Will be used for API calling
 */
interface ApiInterface {
    companion object {
        const val HEADER_VALUE = "Accept: application/json"
        const val HEADER_CONTENT_TYPE = "Content-type: application/json"
        const val AUTHORIZATION = "auth/Authorization"

        //   const val WS_GET_TERMS_AND_CONDITION= "admin/termsAndCondition/getTermsAndCondition/userType"
        const val WS_GENERATE_OTP = "generateOtp"
        const val WS_VERIFY_OTP = "verify"
        const val WS_DELETE = "delete"




    }

}