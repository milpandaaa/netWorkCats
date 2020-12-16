package com.example.networkcats

import android.content.Context
import android.widget.Toast

fun Context.toast(description: String) = Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
