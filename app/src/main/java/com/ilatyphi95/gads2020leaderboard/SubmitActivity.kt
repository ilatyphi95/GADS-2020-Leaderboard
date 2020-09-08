package com.ilatyphi95.gads2020leaderboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilatyphi95.gads2020leaderboard.databinding.ActivitySubmitBinding
import com.ilatyphi95.gads2020leaderboard.databinding.ConfirmSubmitDialogBinding
import kotlinx.coroutines.*

class SubmitActivity : AppCompatActivity() {

    private val viewModel by viewModels<SubmitViewModel> {
        SubmitViewModelFactory(RetrofitBuilder.postService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        val binding : ActivitySubmitBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit)

        binding.apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@SubmitActivity

            back.setOnClickListener{ finish() }

            edFirstName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                checkField(view, hasFocus, R.string.invalid_name, ::isValidName)
            }

            edLastName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                checkField(view, hasFocus, R.string.invalid_name, ::isValidName)
            }

            edEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                checkField(view, hasFocus, R.string.invalid_email, ::isValidEmail)
            }

            edProjectLink.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                checkField(view, hasFocus, R.string.invalid_github_link, ::isValidGitHubLink)
            }
        }

        viewModel.apply {

            eventMessage.observe(this@SubmitActivity, EventObserver {
                Toast.makeText(this@SubmitActivity, getString(it), Toast.LENGTH_LONG).show()
            })

            submit.observe(this@SubmitActivity, EventObserver{ submitted ->
                if(submitted) {
                    showAlertDialog(R.layout.success_dialog_layout) { finish() }
                } else {
                    showAlertDialog(R.layout.failure_dialog_layout) {}
                }
            })

            eventConfirmSubmission.observe(this@SubmitActivity, EventObserver{ confirmed ->
                if(confirmed) {
                    showSubmitAlert { submissionConfirmed() }
                }
            })
        }
    }

    private fun checkField(view: View?, hasFocus: Boolean, @StringRes errorMsg: Int,
                           evaluateFun: (String?) -> Boolean) {
        val text = view as EditText
        if (!evaluateFun(text.text.toString()) && !hasFocus) text.error =
            getString(errorMsg)
        else viewModel.dataEdited()
    }

    private fun showAlertDialog(@LayoutRes layout: Int, expression: () -> Unit) {
        val alertDialog = AlertDialog.Builder(this@SubmitActivity)
            .setView(layout)
            .setOnDismissListener {
                expression()
            }
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        CoroutineScope(Job() + Dispatchers.Main).launch {
            delay(3000)
            if(alertDialog.isShowing){
                alertDialog.dismiss()
        } }
    }

    private fun showSubmitAlert(expression: () -> Unit) {
        val binding = ConfirmSubmitDialogBinding
            .inflate(this@SubmitActivity.layoutInflater, null, false)

        val alertDialog = AlertDialog.Builder(this@SubmitActivity)
            .setView(binding.root)
            .setOnDismissListener {
                expression()
            }
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        binding.cancel.setOnClickListener {
            alertDialog.cancel()
        }

        binding.confirm.setOnClickListener {
            expression()
            alertDialog.cancel()
        }
    }
}