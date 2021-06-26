package com.example.researchsurveydrateeeq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.researchsurveydrateeeq.databinding.ActivityMainBinding
import com.example.researchsurveydrateeeq.modal.researchdata
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnEnterSurvey.setOnClickListener {
            val intent = Intent(this,ResearchForm::class.java)
            startActivity(intent)
        }
        try{
            val patient= researchdata("age","drug","gender","education","glucose","checking")

            FirebaseFirestore.getInstance().collection("Patients").document("abc")
                .set(patient).addOnSuccessListener {
                    Toast.makeText(this,"Data Added Succefully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Network Or Server Issue", Toast.LENGTH_SHORT).show()
                    Log.i("bbbbb",it.message.toString())
                }
        }
        catch (e: Exception){
            Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show()

        }
    }
}