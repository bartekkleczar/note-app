package com.example.note

fun preview(string: String): String{
    return if(string.length < 50){
        string
    } else {
        var returnString = ""
        for(i in 0..string.length) if(i < 50) returnString += string[i] else break
        "$returnString..."
    }
}