package com.taichung.bryant.kotlin_mvvm.utils

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.taichung.bryant.kotlin_mvvm.config.Config
import com.taichung.bryant.kotlin_mvvm.databinding.DialogListLangBinding

fun showToast(context: Context, str: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, str, length).show()
}