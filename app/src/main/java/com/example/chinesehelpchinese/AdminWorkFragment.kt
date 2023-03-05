package com.example.chinesehelpchinese

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.admin_frag.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AdminWorkFragment:Fragment() {

    lateinit var projectList : MutableList<RaiseProject>
    lateinit var token :String
    private var  titltView:TextView?=null
    private var imageView:ImageView?=null
    private var introduceView:TextView?=null
    private var passButton:Button?=null
    private var noPassButton:Button?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titltView=view.findViewById(R.id.adminTitleView)
        imageView=view.findViewById(R.id.adminImageView)
        introduceView=view.findViewById(R.id.adminContentView)
        passButton=view.findViewById(R.id.pass)
        noPassButton=view.findViewById(R.id.noPass)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val (someA,someB)= UseActivity().getData()
        projectList=someA
        token=someB
        var i = projectList.size
        showWork(projectList[i-1])
        passButton?.setOnClickListener {
            val adminService = ServiceCreator.create(UserService::class.java)
            val data = HttpAdminWork(projectList[i].Title,"管理员","true")
            adminService.adminWork(data,token).enqueue(object :
                retrofit2.Callback<ErrorReturn> {
                override fun onResponse(call: Call<ErrorReturn>, response: Response<ErrorReturn>) {

                }

                override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })

            i--
            if (i==-1){
               // UseActivity().replaceFragment(RaiseProjectListFragment())
            }else{
                showWork(projectList[i])
                onResume()
            }
        }

        noPassButton?.setOnClickListener {
            val adminService = ServiceCreator.create(UserService::class.java)
            val data = HttpAdminWork(projectList[i].Title,"管理员","false")
            adminService.adminWork(data,token).enqueue(object :
                retrofit2.Callback<ErrorReturn> {
                override fun onResponse(call: Call<ErrorReturn>, response: Response<ErrorReturn>) {

                }

                override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })

            i--
            if (i==-1){
                //UseActivity().replaceFragment(RaiseProjectListFragment())
            }else{
                showWork(projectList[i])
                onResume()
            }
        }
    }

    private fun showWork(project: RaiseProject){
        introduceView?.text=project.Introduce
        println("0000")
        titltView?.text=project.Title
        UseActivity().getImageToShare(project.Image,imageView!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_frag,container,false)
    }
}