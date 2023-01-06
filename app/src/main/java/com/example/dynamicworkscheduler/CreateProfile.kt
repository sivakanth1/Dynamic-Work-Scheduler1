package com.example.dynamicworkscheduler

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dynamicworkscheduler.databinding.ActivityCreateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class CreateProfile : AppCompatActivity() {
    private var breaks_cnt = 0
    lateinit var mBottom_Pane: ConstraintLayout
    lateinit var mWork_starting_hour_Tv: TextView
    lateinit var mWork_ending_hour_Tv: TextView
    lateinit var mAdd_Breaks_TV: TextView
    var gender_selected = "Gender"
    lateinit var mGender_LL: LinearLayout
    lateinit var mRoot_LL: LinearLayout
    lateinit var mGender_BTN: Button
    lateinit var mMale_BTN: Button
    lateinit var mFemale_BTN: Button
    lateinit var mNext_IV: ImageView
    lateinit var mBack_IV: ImageView
    private lateinit var binding:ActivityCreateProfileBinding
    private lateinit var auth:FirebaseAuth
    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        mGender_LL = binding.GenderLL
        mGender_BTN = binding.Gender
        mMale_BTN = binding.MaleBTN
        mFemale_BTN = binding.FemaleBTN
        mBottom_Pane = binding.BottomPaneCL
        mWork_starting_hour_Tv = binding.WorkStartingHourTv
        mWork_ending_hour_Tv = binding.WorkEndingHourTv
        mAdd_Breaks_TV = binding.AddBreaksTV
        mNext_IV = binding.NextIV
        mRoot_LL = binding.RootLL
        mNext_IV.setOnClickListener {
//            val visibility =
//                if (mBottom_Pane.visibility == View.GONE) View.VISIBLE else View.GONE
//            TransitionManager.beginDelayedTransition(
//                mBottom_Pane,
//                AutoTransition()
//            )
//            mBottom_Pane.visibility = visibility
            signUp()
        }
        mWork_starting_hour_Tv.setOnClickListener {
            Toast.makeText(this, "Start Time", Toast.LENGTH_SHORT).show()
        }
        mWork_ending_hour_Tv.setOnClickListener {
            Toast.makeText(this, "End Time", Toast.LENGTH_SHORT).show()
        }
        mAdd_Breaks_TV.setOnClickListener {
            Toast.makeText(this, "Break Time", Toast.LENGTH_SHORT).show()
            val breakView =
                layoutInflater.inflate(R.layout.add_break_layout, null)
            //            mRoot_LL.addView(breakView);
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(10, 20, 10, 20)
            val mContainerLayout =
                breakView.findViewById<LinearLayout>(R.id.Container_layout)
            mContainerLayout.layoutParams = layoutParams
            val mBreakHeading = breakView.findViewById<TextView>(R.id.Break_Heading_TV)
            val mBreakStartingHour =
                breakView.findViewById<TextView>(R.id.Break_Starting_hour)
            val mBreakEndingHour =
                breakView.findViewById<TextView>(R.id.Break_ending_hour)
            val mDeleteBreakTimeIv =
                breakView.findViewById<ImageView>(R.id.Delete_Break_time_IV)
            val breakHeading = "Break #" + ++breaks_cnt + " "
            mBreakHeading.text = breakHeading
            mBreakStartingHour.setOnClickListener {
                Toast.makeText(this, breakHeading + "Start Clicked", Toast.LENGTH_SHORT).show()
            }
            mBreakEndingHour.setOnClickListener {
                Toast.makeText(this, breakHeading + "End Clicked", Toast.LENGTH_SHORT).show()
            }
            mDeleteBreakTimeIv.setOnClickListener {
                mRoot_LL.removeView(breakView)
                --breaks_cnt
            }

            mRoot_LL.addView(breakView)
        }
        mGender_BTN.setOnClickListener {
            mGender_LL.visibility = View.VISIBLE
            mFemale_BTN.setOnClickListener {
                mGender_BTN.setText("Female")
                mGender_BTN.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_female, 0, 0, 0)
                mGender_LL.visibility = View.GONE
            }
            mMale_BTN.setOnClickListener {
                mGender_BTN.text = "Male"
                mGender_BTN.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_male, 0, 0, 0)
                mGender_LL.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun signUp(){
        val email = binding.EmailET
        val password = binding.PasswordET
        val name = binding.TitleET
        val age = binding.AgeET

        // check pass
        if (email.text.toString().isBlank()) {
            email.error ="Enter Your Mail Id"
        }else if(password.text.toString().isBlank()){
            password.error = "Password must be filled out."
        }else if(name.text.toString().isBlank()){
            name.error = "Enter Your name"
        }
        if(age.text.toString().isBlank()){
            age.error= "Enter Your Age"
        }
        if(password.text.toString().length>=8 && isValidPassword(password.text.toString())){
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name.text.toString()
                            //     photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                        }
                        user!!.updateProfile(profileUpdates)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener {
                    if (it.message == "The email address is already in use by another account."){
                        Toast.makeText(this,"User already exists",Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this,"Password length should contain minimum 8",Toast.LENGTH_SHORT).show()
        }
    }
    private fun isValidPassword(s: String?): Boolean {
        val passwordPattern: Pattern = Pattern.compile(
            "[a-zA-Z0-9!@#$]{8,15}"
        )
        return !TextUtils.isEmpty(s) && s?.let { passwordPattern.matcher(it).matches() } == true
    }
}