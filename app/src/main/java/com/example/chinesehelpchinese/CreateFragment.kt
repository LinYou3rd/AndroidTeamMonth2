package com.example.chinesehelpchinese

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.create_frag.*

class CreateFragment :Fragment(){
    var createTitle :EditText?=null
    var createTarget :EditText?=null
    var  createIntroduce:EditText?=null
    var imageSaveButton:Button?=null
    var createSureButton:Button?=null
    var imageSaveView:ImageView?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_frag,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTitle=view.findViewById(R.id.createTitle)
       createTarget =view.findViewById(R.id.createTargetMoney)
        createIntroduce=view.findViewById(R.id.createIntroduce)
        createSureButton=view.findViewById(R.id.createSureButton)
        imageSaveButton=view.findViewById(R.id.imageSaveButton)
        imageSaveView=view.findViewById(R.id.imageSaveView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageSaveButton?.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type="image/*"
            startActivityForResult(intent,1)
        }
        createSureButton?.setOnClickListener {
            val title = createTitle?.text.toString()
            val introduce = createIntroduce?.text.toString()
            val targetMoney = createTarget?.text.toString().toInt()
            val image = UseActivity().putImageToShare(imageSaveView)
            UseActivity().createSureButtonClick(title,introduce,targetMoney,image)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{
                if (resultCode== Activity.RESULT_OK&&data!=null){
                    data.data?.let { uri->
                        imageSaveView?.let { Glide.with(it).asBitmap().load(uri).into(imageSaveView!!)}
                    }
                }
            }
        }
    }
}