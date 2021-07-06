package com.example.myshopingmall.activity.fragmentSignIn_SgnUp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.myshopingmall.FireBaseFragment
import com.example.myshopingmall.R
import com.example.myshopingmall.activity.SignInActivity
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : FireBaseFragment() {

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetPasswordFunction()
        reset_go_back.setOnClickListener {
            goBack()
        }
        reset_password_button.setOnClickListener {
            resetPasswordButton()
        }
    }

    @SuppressLint("NewApi")
    private fun resetPasswordButton() {
        TransitionManager.beginDelayedTransition(mail_check_sent_or_not)
        mail.visibility= View.VISIBLE
        resetprogressBar.visibility= View.VISIBLE


        reset_password_button.isEnabled= false
        reset_password_button.setTextColor(Color.argb(50, 255, 255, 255))

        firebaseAuth.sendPasswordResetEmail(resetPassword_edit_text.text.toString().trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("email sent successfully")
                } else {
                    val errorHandle = task.exception?.message.toString()
                    showToast(errorHandle)
                    resetprogressBar.visibility=View.GONE
                    mail.setColorFilter(resources.getColor(R.color.design_default_color_error))
                    TransitionManager.beginDelayedTransition(mail_check_sent_or_not)
                    mail.visibility = View.VISIBLE
                }
                reset_password_button.isEnabled = true
                reset_password_button.setTextColor(Color.rgb(255, 255, 255))
            }

    }


    private fun resetPasswordFunction() {

        resetPassword_edit_text.addTextChangedListener {
            val resetEditText = resetPassword_edit_text.text.toString().trim()
            if (resetEditText.isEmpty()) {
                reset_password_button.isEnabled = false
                reset_password_button.setTextColor(Color.argb(50, 255, 255, 255))
            } else {
                reset_password_button.isEnabled = true
                reset_password_button.setTextColor(Color.rgb(255, 255, 255))

            }

        }
    }

    private fun goBack() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)

    }
}