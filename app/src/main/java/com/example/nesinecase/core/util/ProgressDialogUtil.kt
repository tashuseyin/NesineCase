package com.example.nesinecase.core.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.nesinecase.R

object ProgressDialogUtil {
    private var progressBar: AlertDialog? = null

    fun checkProgressDialog(context: Context, isLoading: Boolean) {
        if (isLoading) {
            showProgress(context)
        } else {
            hideProgress()
        }
    }

    private fun showProgress(context: Context) {
        if (progressBar == null) {
            val dialog = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null)
            dialog.setView(view)
            dialog.setCancelable(false)
            progressBar = dialog.create()
            progressBar?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        progressBar?.show()
    }

    private fun hideProgress() {
        progressBar?.hide()
    }
}