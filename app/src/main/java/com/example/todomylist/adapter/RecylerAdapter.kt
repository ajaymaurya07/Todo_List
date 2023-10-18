package com.example.todomylist.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todomylist.OnclickListner
import com.example.todomylist.databinding.PrototypeShowdataBinding
import com.example.todomylist.db.Todo

class RecylerAdapter(var list: List<Todo>,var onclickListner: OnclickListner): RecyclerView.Adapter<RecylerAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding:PrototypeShowdataBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding=PrototypeShowdataBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data=list[position]
        holder.binding.todoTitle.text=data.title
        holder.binding.todoDescription.text=data.discription
        holder.binding.deleteTodo.setOnClickListener {
            onclickListner.onitemclick(data)
        }
        holder.binding.editTodo.setOnClickListener {
            onclickListner.onitemupdate(data)
        }

    }

}