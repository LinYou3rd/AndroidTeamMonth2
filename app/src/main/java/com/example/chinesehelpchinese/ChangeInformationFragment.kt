package com.example.chinesehelpchinese

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.change_frag.*

class ChangeInformationFragment:Fragment() {
    private var button:Button?=null
    private var nameText:EditText?=null
    private var identityText:EditText?=null
    private var contactText:EditText?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_frag,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameText=view.findViewById(R.id.changeNameText)
        identityText=view.findViewById(R.id.changeIdentityText)
        contactText=view.findViewById(R.id.changeContactText)
        button=view.findViewById(R.id.changeButton)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button?.setOnClickListener {
            val contact = changeContactText.text.toString()
            val name = changeNameText.text.toString()
            val identity = changeIdentityText.text.toString()
            UseActivity().changeButtonClick(contact,name,identity)
        }
    }
}