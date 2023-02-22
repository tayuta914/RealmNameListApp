package com.example.realmnamelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
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

        // 編集画面で編集したものを最後にTextで表示させるようにTextViewで設定
        val etName: TextView = binding.etName
        val etAge: TextView = binding.etAge

        realm = Realm.getDefaultInstance()

        // intentを受け取る
        val getId = intent.getLongExtra("ID", 0L)
        if (getId > 0) {
            val myModelResult = realm.where<MyModel>()
                .equalTo("id", getId).findFirst()
            etName.text = myModelResult?.name.toString()
            etAge.text = myModelResult?.age.toString()
            binding.btnDel.visibility = View.VISIBLE
        } else {
            binding.btnDel.visibility = View.INVISIBLE
        }

        binding.btnSave.setOnClickListener {
            var name = ""
            var age: Long = 0

            if (!binding.etName.text.isNullOrEmpty()) {
                name = binding.etName.text.toString()
            }
            if (!binding.etAge.text.isNullOrEmpty()) {
                age = binding.etAge.text.toString().toLong()
            }

            // DBに書き込む宣言
            if (getId == 0L) {
                realm.executeTransaction {
                    // 新規追加
                    val currentId = realm.where<MyModel>().max("id")
                    val nextId = (currentId?.toLong() ?: 0L) + 1L // 最高値に1を追加(最高値が0なら1に)
                    val myModel = realm.createObject<MyModel>(nextId)
                    myModel.name = name
                    myModel.age = age
                }
            } else {
                // 上書き保存
                realm.executeTransaction {
                    val myModel = realm.where<MyModel>()
                        .equalTo("id", getId).findFirst()
                    myModel?.name = name
                    myModel?.age = age
                }
            }
            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnDel.setOnClickListener {
            realm.executeTransaction {
                realm.where<MyModel>()
                    .equalTo("id", getId)
                    .findFirst()?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}