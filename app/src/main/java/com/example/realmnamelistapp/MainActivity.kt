package com.example.realmnamelistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.realmnamelistapp.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var realm: Realm
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var layoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realm = Realm.getDefaultInstance()

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val realmResults = realm.where(MyModel::class.java)
            .findAll().sort("id", Sort.DESCENDING)

        recyclerView = binding.rv
        recyclerAdapter = RecyclerAdapter(realmResults)
        recyclerView.adapter = recyclerAdapter

        //縦並び配置
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}