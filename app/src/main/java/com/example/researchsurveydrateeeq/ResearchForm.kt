package com.example.researchsurveydrateeeq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.researchsurveydrateeeq.databinding.ActivityMainBinding
import com.example.researchsurveydrateeeq.databinding.ActivityResearchFormBinding
import com.example.researchsurveydrateeeq.modal.researchdata
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class ResearchForm : AppCompatActivity() {
    private lateinit var binding:ActivityResearchFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research_form)
        binding = ActivityResearchFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val list:Spinner = binding.spgenderlist
        ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                list.adapter=adapter
            }
        val edlist:Spinner =binding.speducation
        ArrayAdapter.createFromResource(this,R.array.education,android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    edlist.adapter=adapter
                }
        val gllist:Spinner = binding.spgulocose
        ArrayAdapter.createFromResource(this,R.array.glucoselevel,android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    gllist.adapter = adapter
                }
        val chlist:Spinner = binding.spchecked
        ArrayAdapter.createFromResource(this,R.array.checking,android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    chlist.adapter = adapter
                }
        /////////////////Now Sending Data To Firebase
        var age:String
        var drug:String
        var gender:String
        var education:String
        var glucose:String
        var checking:String
        age=binding.edtage.text.toString()
        drug=binding.edtdrugused.text.toString()
        fun validate():Boolean{
            return age.isNullOrEmpty() ||
                    drug.isNullOrEmpty()

        }


        binding.btndubmit.setOnClickListener {
            Toast.makeText(this,"RAN",Toast.LENGTH_SHORT).show()
             age=binding.edtage.text.toString()
             drug=binding.edtdrugused.text.toString()
             gender=list.selectedItem.toString()
             education=edlist.selectedItem.toString()
             glucose=gllist.selectedItem.toString()
             checking=chlist.selectedItem.toString()
             val patient=researchdata(age,drug,gender,education,glucose,checking)
            if (validate()){
                Toast.makeText(this,"Please Fill All Field",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Else",Toast.LENGTH_SHORT).show()
                try{
                FirebaseFirestore.getInstance().collection("Patient").document(age)
                        .set(patient).addOnSuccessListener {
                            Toast.makeText(this,"Data Added Succefully",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(this,"Network Or Server Issue",Toast.LENGTH_SHORT).show()
                            Log.i("bbbbb",it.message.toString())
                        }
            }
                catch (e:Exception){
                    Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()

                }                }
        }


    }

}