package com.three.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 데이터 넣기
        val items = mutableListOf<String>()
        items.add("A")
        items.add("B")
        items.add("C")

        // 2. RecyclerView 가져오기
        val rv = findViewById<RecyclerView>(R.id.recyclerView)

        // 3. 어댑터 만들기
        val rvAdapter = RVAdapter(items)

        // 3-1. 어댑터 연결
        rv.adapter = rvAdapter

        // 4. LayoutManager 설정
        // layoutManager : RecyclerView에 각 아이템 배치
        // LinearLayoutManager : 수직 스크롤 리스트 형태로 아이템을 배치
        rv.layoutManager = LinearLayoutManager(this)

        // 5. 아이템 클릭 이벤트 처리
        // 어댑터 내부에 ItemClick 인터페이스 구현 및 클릭 이벤트 처리
        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(baseContext, items[position], Toast.LENGTH_LONG).show()
            }
        }
    }
}