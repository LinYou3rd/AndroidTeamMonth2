package com.example.chinesehelpchinese

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.search_frag.*

class SearchFragment:Fragment() {
    protected var mview : View?=null
    private var button:Button?=null
    private var title :String?=null
    private var text :EditText?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mview= inflater.inflate(R.layout.search_frag,container,false)
        return mview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button=view.findViewById(R.id.searchButton)
        text=view.findViewById(R.id.searchText)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button?.setOnClickListener {
            title=text?.text.toString()
            UseActivity().searchButtonClick(title!!)
        }
    }
}