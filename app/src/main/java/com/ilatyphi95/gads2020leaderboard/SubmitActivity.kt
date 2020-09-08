package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilatyphi95.gads2020leaderboard.databinding.ActivitySubmitBinding

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
                    Toast.makeText(this@SubmitActivity, getString(R.string.submission_successful),
                        Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@SubmitActivity, getString(R.string.submission_not_successful),
                        Toast.LENGTH_LONG).show()
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
}