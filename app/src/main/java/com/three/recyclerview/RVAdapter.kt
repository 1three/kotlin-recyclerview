package com.three.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(val items: MutableList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    // RecyclerView 아이템 가져오기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        // RecyclerView의 아이템 레이아웃을 inflate하여 ViewHolder를 생성
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    // 각 아이템에 대한 클릭 이벤트 처리 시 (1)
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    // 각 아이템에 대한 클릭 이벤트 처리 시 (2)
    var itemClick: ItemClick? = null

    // 가져온 아이템을 ViewHolder에 바인딩 (뷰바인딩 : Adapter → 아이템 → RecyclerView)
    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {

        // 각 아이템에 대한 클릭 이벤트 처리 시 (3)
        if (itemClick != null) {
            holder.itemView.setOnClickListener { view ->
                itemClick?.onClick(view, position)
            }
        }

        // ViewHolder에 데이터 바인딩 및 RecyclerView에 표시
        holder.bindItems(items[position])
    }

    // 전체 RecyclerView의 개수 알리기
    override fun getItemCount(): Int {
        return items.size
    }

    // ViewHolder : view 재사용
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: String) {
            // RecyclerView에 데이터 매핑
            val rv_text = itemView.findViewById<TextView>(R.id.recyclerViewItem)
            rv_text.text = item
        }
    }
}