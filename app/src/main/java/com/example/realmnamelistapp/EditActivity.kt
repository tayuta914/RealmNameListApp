package com.example.realmnamelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.realmnamelistapp.databinding.ActivityEditBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        binding.btnSave.setOnClickListener {
            var name: String = ""
            var age: Long = 0

            if (!binding.etName.text.isNullOrEmpty()) {
                name = binding.etName.toString()
            }
            if (!binding.etAge.text.isNullOrEmpty()) {
                age = binding.etAge.toString().toLong()
            }

            // DBに書き込む宣言
            realm.executeTransaction {
                val currentId = realm.where<MyModel>().max("id")
                val nextId = (currentId?.toLong() ?: 0L) + 1L // 最高値に1を追加(最高値が0なら1に)
                val myModel = realm.createObject<MyModel>(nextId)
                myModel.name = name
                myModel.age = age
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}