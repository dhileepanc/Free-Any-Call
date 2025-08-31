package com.freeanycall.app.utils

class Constants {

    companion object{

        val TAG=Constants::class.java.simpleName
        const val SITE_URL = "http://192.168.234.232:8000/"

        const val END_POINT ="api/"
        val API_URL = "$SITE_URL$END_POINT" //base url for all api


        const val TAG_FIRST_NAME ="first_name"
        const val TAG_LAST_NAME ="last_name"
       const val TAG_EMAIL = "email"
       const val TAG_PASSWORD = "password"



    }
}