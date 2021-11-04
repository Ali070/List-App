package com.alisamir.listapp


import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.alisamir.listapp.Model.Person
import com.alisamir.listapp.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var firstClick = false
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val personsList = ArrayList<Person>()
        personsList.add(Person("Ali","0123231222",R.drawable.man))
        personsList.add(Person("Mohamed","011212323",R.drawable.man))
        personsList.add(Person("Mona","0154543544",R.drawable.woman))
        personsList.add(Person("Salma","0177848799",R.drawable.woman))
        val adapter = listAdapter(personsList)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        val animation  = AnimationUtils.loadAnimation(this,R.anim.layout_anim)
        val animation2  = AnimationUtils.loadAnimation(this,R.anim.layout_anim2)

        binding.addBtn.setOnClickListener {
            if (!firstClick){

                binding.inputLayout.visibility = View.VISIBLE
                binding.inputLayout.startAnimation(animation)

                firstClick = true
            }else if(firstClick){
                if (binding.personNameET.text.isEmpty()){
                    binding.personNameET.setError("Enter your name")
                    binding.personNameET.requestFocus()
                }else if(binding.personNumberET.text.isEmpty()){
                    binding.personNumberET.setError("Enter your phone number")
                    binding.personNumberET.requestFocus()
                }else {
                    if(binding.maleBtn.isChecked){
                        personsList.add(Person(binding.personNameET.text.toString().trim(),binding.personNumberET.text.toString().trim(),R.drawable.man))
                    }else if(binding.femaleBtn.isChecked){
                        personsList.add(Person(binding.personNameET.text.toString().trim(),binding.personNumberET.text.toString().trim(),R.drawable.woman))
                    }
                    adapter.notifyItemInserted(personsList.size-1)
                    binding.personNameET.text.clear()
                    binding.personNumberET.text.clear()
                    binding.inputLayout.startAnimation(animation2)
                    binding.inputLayout.visibility = View.GONE

                    firstClick = false
                }
            }
        }
        /*adapter.seOnClickListner(object :listAdapter.onItemClickLstner{
            override fun OnEdit(position: Int) {
                val person = personsList.get(position)

            }*/

            override fun OnDelete(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setMessage("Are you sure ?").setTitle("Confirmation")
                    .setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            personsList.removeAt(position)
                            adapter.notifyItemRemoved(position)
                            Toast.makeText(applicationContext,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                        }

                    })
                    .setNegativeButton("No",object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            Toast.makeText(applicationContext,"Canceled",Toast.LENGTH_SHORT).show()
                        }

                    }).create()
                    dialog.show()
            }

        })
    }
}