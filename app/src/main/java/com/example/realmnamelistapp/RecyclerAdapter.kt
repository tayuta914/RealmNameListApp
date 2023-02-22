package com.example.realmnamelistapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class RecyclerAdapter(realmResults: RealmResults<MyModel>) :
    RecyclerView.Adapter<ViewHolderItem>() {
    private val rResults: RealmResults<MyModel> = realmResults

    // １行だけのレイアウト
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_lauyout, parent, false)
        return ViewHolderItem(oneXml)
    }

    //position番目のデータを表示
    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val myModel = rResults[position]
        holder.oneTvName.text = myModel?.name.toString()
        holder.oneTvAge.text = myModel?.age.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,EditActivity::class.java)
            // idを渡す
            intent.putExtra("ID",myModel?.id)
            it.context.startActivity(intent)
        }
    }


    // リストの件数(結果件数)
    override fun getItemCount(): Int {
        return rResults.size
    }
}