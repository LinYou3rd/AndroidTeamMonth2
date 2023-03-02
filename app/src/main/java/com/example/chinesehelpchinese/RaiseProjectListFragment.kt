package com.example.chinesehelpchinese

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_frag.*

class RaiseProjectListFragment:Fragment() {

//    private lateinit var recyclerView:RecyclerView
//    private lateinit var list : List<RaiseProject>
//    private lateinit var context: Context



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.project_list_frag,container,false)
        val context = view.context
        val manager = LinearLayoutManager(context)
        val myadapter = ProjectAdapter(context,UseActivity().getAllProject())

//        recycleView.adapter=myadapter
//        recycleView.layoutManager=manager
//        recycleView.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.HORIZONTAL))
//        val snapHelper = LinearSnapHelper()
//        snapHelper.attachToRecyclerView(recycleView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}