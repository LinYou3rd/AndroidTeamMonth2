package com.example.chinesehelpchinese

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.search_frag.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val userService = ServiceCreator.create(UserService::class.java)
            userService.getTargetProject(title!!, UseActivity.token).enqueue(object :
                Callback<GetTargetProjectReturn> {
                override fun onResponse(
                    call: Call<GetTargetProjectReturn>,
                    response: Response<GetTargetProjectReturn>
                ) {
                    if (response.body()?.code==200){
                        val intent = Intent(context,RaiseProjectActivity::class.java)
                        intent.putExtra("token", UseActivity.token)
                        intent.putExtra("PROJECT_TITLE",response.body()?.title)
                        intent.putExtra("PROJECT_INTRODUCE",response.body()?.introduce)
                        intent.putExtra("PROJECT_NOW",response.body()?.nowMoney)
                        intent.putExtra("PROJECT_IMAGE",response.body()?.image)
                        intent.putExtra("PROJECT_TARGET",response.body()?.targetMoney)
                        intent.putExtra("mode", UseActivity.mode)
                        startActivity(intent)
                    }else{
                        Toast.makeText(context,"请求发送成功，但失败了", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetTargetProjectReturn>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}