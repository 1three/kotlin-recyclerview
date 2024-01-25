# kotlin-recyclerview
[Kotlin] `RecyclerView` 공부하기

<br>

## ListView와 RecyclerView 비교

```md
- ListView
스크롤 시, 화면에 보이던 itemView를 삭제하고 아래에서 새롭게 보일 itemView 객체를 새로 생성합니다.
스크롤을 위 아래로 몇번 반복하다보면 수백, 수천개의 itemView의 생성/삭제가 반복되어 메모리 낭비가 심합니다.

- RecyclerView
스크롤 시, 화면에 보이던 itemView를 삭제하지 않고, 아래에서 새롭게 보일 itemView 객체를 새로 생성합니다.
이전에 보였던 itemVie를 삭제하지 않고 itemView를 재사용합니다.
```

<img width="700" alt="RecyclerView와 ListView" src="https://github.com/1three/kotlin-recyclerview/assets/94810322/5981a771-9c98-4063-ae48-15518638c768">

`출처 : WiseITeach`

<br>

## 🗄️ RecyclerView란?
_**RecyclerView**_ 는 안드로이드에서 사용되는 리스트나 그리드 형태의 데이터를 표시하는 위젯입니다.

```
- Adapter
데이터를 가져와서 각 아이템 뷰에 바인딩하고, 아이템 뷰를 생성하고 관리

- LayoutManager
아이템 뷰의 배치와 스크롤 동작을 관리
(LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager)

- ViewHolder
아이템 뷰의 재사용을 위해 필요한 데이터와 뷰를 보관
```

<br>

## 🗂️ RecyclerView 사용

<img width="500" alt="RecyclerView 사용" src="https://github.com/1three/kotlin-recyclerview/assets/94810322/b37c490d-80c1-43c2-b76a-5f6f412616aa">

<br>
<br>

### 📂 MainActivity.kt

MainActivity에서는 RecyclerView를 사용하여 데이터를 표시할 수 있습니다.

```Kotlin
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
        rv.layoutManager = LinearLayoutManager(this)

        // 5. 아이템 클릭 이벤트 처리
        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(baseContext, items[position], Toast.LENGTH_LONG).show()
            }
        }
    }
}
```

<br>

### 📂 RVAdapter.kt

RVAdapter.kt는 RecyclerView의 어댑터 클래스로, 데이터와 아이템 뷰를 연결하는 역할을 합니다.

```Kotlin
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
```

<br>

### 📂 recycler_view_item.xml

recycler_view_item.xml은 RecyclerView의 아이템을 나타내는 XML 파일입니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="70dp">

    <TextView
        android:id="@+id/recyclerViewItem"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="RecyclerView의 개별 아이템"
        android:textSize="20sp" />
</LinearLayout>
```

<br>

### 📂 activity_main.xml

activity_main.xml은 메인 액티비티의 레이아웃 파일입니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

<br>

## 🎨 그림으로 이해하는 RecyclerView

<img width="700" alt="RecyclerView1" src="https://github.com/1three/kotlin-recyclerview/assets/94810322/7d9e5f7d-4180-48b9-9ee8-117511789ed1">

<br>

<img width="700" alt="RecyclerView2" src="https://github.com/1three/kotlin-recyclerview/assets/94810322/d4ac879b-fc27-452d-b27f-91243652238e">

`출처 : 35개 프로젝트로 배우는 Android 앱 개발 feat. Jetpack Compose 초격차 패키지 Online`

```
Adapter
- Data와 ViewHolder를 합쳐 ItemView로 보여주는 역할

onCreateViewHolder
- (아직은 내용이 빈) ViewHolder를 만드는 역할

onBindViewHolder
- ViewHolder의 View에 Data를 가져와 binding 해주는 역할
- Data를 호출하기 위한 정보 + 화면에 보여지기 위한 View 정보
```

<br>

## 🐟 붕어빵 가게로 보는 RecyclerView

_RecyclerView에 대해 이해하기 위해 붕어빵 가게를 비유해보겠습니다._

```
🏠 Adapter (붕어빵 가게)
Adapter는 붕어빵 가게 자체를 나타냅니다.
붕어빵 가게는 손님들이 주문한 붕어빵을 만들어서 제공하는 역할을 합니다.
마찾가지로, Adapter는 데이터를 가져와서 화면에 보여주는 역할을 수행합니다.

🫘 Data (붕어빵을 만들기 위한 재료)
Data는 붕어빵을 만들기 위해 필요한 재료들을 나타냅니다.
붕어빵 가게에서는 붕어빵을 만들기 위해 손님들이 주문한 재료들을 사용합니다.
마찬가지로, RecyclerView에서는 각 아이템에 표시할 데이터들이 필요합니다.

🦿 ViewHolder (붕어빵 틀)
ViewHolder는 붕어빵을 담을 틀을 의미합니다.
붕어빵 가게에서는 붕어빵을 만들기 위해 붕어빵 틀을 사용합니다.
이 틀은 붕어빵의 형태를 결정하고, 붕어빵을 담을 공간을 제공합니다.
RecyclerView에서의 ViewHolder도 비슷한 개념으로, 각 아이템의 뷰를 담을 틀 역할을 합니다.

🙆🏻 ItemView (붕어빵 가게 손님)
ItemView는 붕어빵 가게에 방문한 손님들을 나타냅니다.
붕어빵 가게에서는 붕어빵을 만들어서 손님들에게 제공합니다.
RecyclerView에서는 각 아이템의 뷰를 보여주는 역할을 수행합니다.
```
