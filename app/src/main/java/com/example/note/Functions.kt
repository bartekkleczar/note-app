package com.example.note

fun preview(string: String): String{
    val constraint = 25
    return if(string.length < constraint){
        string
    } else {
        var returnString = ""
        for(i in 0..string.length) if(i < constraint) returnString += string[i] else break
        "$returnString..."
    }
}