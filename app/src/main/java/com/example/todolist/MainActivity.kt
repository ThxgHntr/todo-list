package com.example.todolist

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var itemList = ArrayList<String>()
    private var helper = Helper()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemList = helper.readData(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        binding.lvJob.adapter = adapter
        binding.btnAdd.setOnClickListener {
            itemList.add(binding.editInput.text.toString())
            binding.editInput.text.clear()
            helper.writeData(itemList, applicationContext)
            adapter.notifyDataSetChanged()
        }

        binding.lvJob.setOnItemClickListener { _, _, position, _ ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item?")
            alert.setPositiveButton("Yes") { _, _ ->
                itemList.removeAt(position)
                adapter.notifyDataSetChanged()
                helper.writeData(itemList, applicationContext)
            }
            alert.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            alert.create()
            alert.show()
        }
    }
}