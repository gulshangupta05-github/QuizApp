package com.example.myshopingmall.activity.fragmentSignIn_SgnUp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myshopingmall.FireBaseFragment
import com.example.myshopingmall.R
import com.example.myshopingmall.activity.MainActivity
import com.example.myshopingmall.activity.RejisterActivity
import com.example.myshopingmall.activity.SignInActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.sign_in_button

class SignUpFragment : FireBaseFragment() {

    var alreadyhaveanAccocunt: TextView? = null
    var parentFrameLayout: FrameLayout? = null

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        alreadyhaveanAccocunt = view.findViewById(R.id.sign_up_already_acc)
//        parentFrameLayout = activity?.findViewById(R.id.rajister_fram_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alreadyhaveanAccocunt?.setOnClickListener {
            setFragment(SignInFragment())
        }
        sign_in_button.setOnClickListener {
            userRajistration()
        }
//        sign_up_close_btn_end.setOnClickListener {
//            gotoMainActivity()
//        }
    }




    private fun userRajistration() {
        val userName = sign_up_user_name.text.toString().trim()
        val userEmail = sign_up_email_id.text.toString().trim()
        val userPassword = sign_up_new_password.text.toString().trim()
        val userConfirmPassword = sign_up_confirm_password.text.toString().trim()

        if (userName === "" || userEmail === "" || !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                .matches() || userPassword === "".equals(userConfirmPassword)
                .toString() || userConfirmPassword === ""
        ) {
            sign_up_user_name.error = "Please Enter a Name"
            sign_up_email_id.error = "Please Enter a Valid Email"
            sign_up_new_password.error = "Please Enter a Password"
            sign_up_confirm_password.error = "Enter Confirm Password"
        } else {
            sign_up_progresss_bar.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sign_up_progresss_bar.visibility = View.GONE
                        val data = hashMapOf("fullname" to userName)
                        firestore.collection("USERS").add(data)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    startActivity(
                                        Intent(requireContext(), MainActivity::class.java).setFlags(
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        )
                                    )
                                    (context as RejisterActivity).finish()
                                    showToast("Login Successfully...")
                                } else {
                                    sign_up_user_name.error = "Please Enter a Name"
                                    sign_up_email_id.error = "Please Enter a Valid Email"
                                    sign_up_new_password.error = "Please Enter a Password"
                                    sign_up_confirm_password.error = "Enter Confirm Password"
                                    showToast("Authentication failed.")
                                    sign_up_progresss_bar.visibility = View.GONE
                                }
                            }

                    } else {
                        sign_up_user_name.error = "Please Enter a Name"
                        sign_up_email_id.error = "Please Enter a Valid Email"
                        sign_up_new_password.error = "Please Enter a Password"
                        sign_up_confirm_password.error = "Enter Confirm Password"
                        showToast("Authentication failed.")
                        sign_up_progresss_bar.visibility = View.GONE
                    }
                }

        }
    }

    private fun setFragment(fragment: Fragment) {
        startActivity(
            Intent(
                requireContext(),
                SignInActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        Toast.makeText(requireContext(), "button pressed", Toast.LENGTH_SHORT).show()


    }

    private fun gotoMainActivity() {

        startActivity(
            Intent(requireContext(), MainActivity::class.java).setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        )
        (context as SignInActivity).finish()

    }
}