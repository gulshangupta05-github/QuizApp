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
import com.example.myshopingmall.activity.ResetPasswordActivity
import com.example.myshopingmall.activity.SignInActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : FireBaseFragment() {

    companion object {
        fun newInstance() = SignInFragment()
        private const val TAG = "SignInFragment"

    }

    var dontHaveAnAccount: TextView? = null
    var parentFrameLayout: FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_an_account)
//        parentFrameLayout = activity?.findViewById(R.id.rajister_fram_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_dont_have_an_account?.setOnClickListener {
                setfragment(SignUpFragment())
        }
        sign_in_button.setOnClickListener {
            loginFunctionality()
        }
        sign_up_close_btn_end.setOnClickListener {
            gotoMainActivity()
        }
            sign_in_forgot_password.setOnClickListener {
                gotoForGotpassword()
            }

    }



    private fun loginFunctionality() {
        val email=sign_in_email_id.text.toString().trim()
        val password = sign_in_new_password.text.toString().trim()
        if (email === "" || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password === "") {
            sign_in_email_id.error = "Please enter a valid email."
            sign_in_new_password.error = "Please enter a valid password."
        } else {
            login(email, password)
        }

    }

    private fun login(email: String, password: String) {

        sign_in_progress_bar.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(context as SignInActivity) {task->
                if (task.isSuccessful) {
                    //    Log.d("LOGIN_FRAGMENT", "signInWithEmail:success")
                    sign_in_progress_bar.visibility = View.GONE
                    gotoMainActivity()
                    showToast("Login Successfully...")
                } else {
                    //  Log.d("LOGIN_FRAGMENT", "signInWithEmail:failure", task.exception)
                    sign_in_email_id.error = "Please enter a valid email."
                    sign_in_new_password.error = "Please enter a valid password."
                    showToast("Authentication failed.")
                    sign_in_progress_bar.visibility = View.GONE
                }
            }
    }

    private fun setfragment(fragment: Fragment) {
        Toast.makeText(requireContext(), "button pressed", Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), RejisterActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

    }


    private fun gotoMainActivity() {

        startActivity(Intent(context as SignInActivity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        (context as SignInActivity).finish()
    }

    private fun gotoForGotpassword() {
    val intent = Intent(requireActivity(),ResetPasswordActivity::class.java)
        startActivity(intent)


    }


}