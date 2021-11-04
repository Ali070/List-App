package com.alisamir.listapp

import com.alisamir.listapp.Model.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alisamir.listapp.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class listAdapter(val listPersons:ArrayList<Person>): RecyclerView.Adapter<listAdapter.myViewHolder>() {
    inner class myViewHolder(val binding: ListItemBinding, val listen:onItemClickLstner) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person:Person,position: Int){
            binding.nameTV.text = person.name
            binding.numberTV.text = person.number
            Picasso.get().load(person.image).resize(100,100).into(binding.circleImageView)
            binding.deleteIV.setOnClickListener {
                    listen.OnDelete(position)

            }
        }

    }
    interface onItemClickLstner{
        fun OnEdit(position: Int)
        fun OnDelete(position: Int)
    }
    lateinit var listner:onItemClickLstner
    fun seOnClickListner(l:onItemClickLstner){
        listner = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listAdapter.myViewHolder {
        return myViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),listner)
    }

    override fun onBindViewHolder(holder: listAdapter.myViewHolder, position: Int) {
        val person = Person(listPersons.get(position).name,listPersons.get(position).number,listPersons.get(position).image)
        holder.bind(person,position)
    }

    override fun getItemCount(): Int {
        return listPersons.size
    }
}