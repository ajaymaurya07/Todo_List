package com.example.todomylist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todomylist.adapter.RecylerAdapter
import com.example.todomylist.databinding.ActivityMainBinding
import com.example.todomylist.databinding.DialogBoxBinding
import com.example.todomylist.db.Todo
import com.example.todomylist.db.TodoDao
import com.example.todomylist.db.TodoDatabase
import com.example.todomylist.repository.Repository
import com.example.todomylist.viewmodel.MyViewModel
import com.example.todomylist.viewmodelfactory.ViewModelFactory

class MainActivity : AppCompatActivity(), OnclickListner {
    lateinit var binding:ActivityMainBinding
    lateinit var viewmodel:MyViewModel
    lateinit var dialog: Dialog
    lateinit var dialogBoxBinding:DialogBoxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)


        dialogBoxBinding= DialogBoxBinding.inflate(LayoutInflater.from(this))
        dialog=Dialog(this)
        dialog.setContentView(dialogBoxBinding.root)
        var layoutManager = WindowManager.LayoutParams()
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutManager

        binding.fBtn.setOnClickListener {
            dialog.show()
        }
        dialogBoxBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        var tododao=TodoDatabase.getInstance(this).todoDao
        var repository=Repository(tododao)
        var factory=ViewModelFactory(repository)
        viewmodel=ViewModelProvider(this,factory)[MyViewModel::class.java]
        dialogBoxBinding.viewmodel=viewmodel
        dialogBoxBinding.lifecycleOwner=this

        viewmodel.message.observe(this, Observer {
            Toast.makeText(this, "${it.gethandle()}", Toast.LENGTH_SHORT).show()
        })

        viewmodel.data.observe(this, Observer {
            var myadapter=RecylerAdapter(it,this)
            binding.recylerView.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            binding.recylerView.adapter=myadapter
        })
        viewmodel.dialog(dialog)

    }

    override fun onitemclick(todo: Todo) {
        var alertDialog=AlertDialog.Builder(this)
            .setTitle("U want to delete it..")
            .setMessage("Are u sure?")

            .setPositiveButton("Yes",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewmodel.adapterclickeditem(todo)
                dialog!!.dismiss()
                }
            })
            .setNegativeButton("No",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }

            })
            alertDialog.create()
        alertDialog.show()

    }

    override fun onitemupdate(todo: Todo) {
        dialog.show()
        dialogBoxBinding.todoTitle.editText!!.setText(todo.title)
        dialogBoxBinding.tilTodoDiscr.editText!!.setText(todo.discription)
        dialogBoxBinding.saveBtn.setOnClickListener {
            viewmodel.itemupdate(todo)
        }
        dialogBoxBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }


}