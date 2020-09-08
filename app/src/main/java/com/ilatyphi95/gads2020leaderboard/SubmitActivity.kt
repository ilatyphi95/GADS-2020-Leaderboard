package com.ilatyphi95.gads2020leaderboard

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilatyphi95.gads2020leaderboard.databinding.ActivitySubmitBinding
import com.ilatyphi95.gads2020leaderboard.databinding.ConfirmSubmitDialogBinding
import kotlinx.coroutines.*
import java.util.*

class SubmitActivity : AppCompatActivity() {

    private val viewModel by viewModels<SubmitViewModel> {
        SubmitViewModelFactory(RetrofitBuilder.postService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        val binding: ActivitySubmitBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_submit)

        binding.apply {
            this.viewmodel = viewModel
            lifecycleOwner = this@SubmitActivity

            back.setOnClickListener { finish() }

            edFirstName.addTextChangedListener(MyWatcher(this@SubmitActivity) { name ->
                viewModel.dataEdited()

                if (!isValidName(name)) edFirstName.error = getString(R.string.invalid_first_name)
            })

            edLastName.addTextChangedListener(MyWatcher(this@SubmitActivity) { name ->
                viewModel.dataEdited()

                if (!isValidName(name)) edLastName.error = getString(R.string.invalid_last_name)
            })

            edEmail.addTextChangedListener(MyWatcher(this@SubmitActivity) { email ->
                viewModel.dataEdited()

                if (!isValidEmail(email)) edEmail.error = getString(R.string.invalid_email)
            })

            edProjectLink.addTextChangedListener(MyWatcher(this@SubmitActivity) { link ->
                viewModel.dataEdited()

                if (!isValidGitHubLink(link)) edProjectLink.error = getString(R.string.invalid_github_link)
            })
        }

        viewModel.apply {

            eventMessage.observe(this@SubmitActivity, EventObserver {
                Toast.makeText(this@SubmitActivity, getString(it), Toast.LENGTH_LONG).show()
            })

            submit.observe(this@SubmitActivity, EventObserver { submitted ->
                if (submitted) {
                    showAlertDialog(R.layout.success_dialog_layout) { finish() }
                } else {
                    showAlertDialog(R.layout.failure_dialog_layout) {}
                }
            })

            eventConfirmSubmission.observe(this@SubmitActivity, EventObserver { confirmed ->
                if (confirmed) {
                    showSubmitAlert { submissionConfirmed() }
                }
            })
        }
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
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }
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

    class MyWatcher(val activity: Activity, val operation: (String?) -> Unit) : TextWatcher {
        private var timer = Timer()
        private val delay: Long = 1500L

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    activity.runOnUiThread {
                        operation(s.toString())
                    }
                }
            }, delay)
        }
    }
}