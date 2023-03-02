package com.example.chinesehelpchinese

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.admin_frag.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AdminWorkFragment:Fragment() {

    lateinit var projectList : MutableList<RaiseProject>
    lateinit var token :String

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        val (someA,someB)= UseActivity().getData()
        projectList=someA
        token=someB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var i = projectList.size
        showWork(projectList[i])

        pass.setOnClickListener {
            val adminService = ServiceCreator.create(UserService::class.java)
            val data = HttpAdminWork(projectList[i].Title,"管理员","true",token)
            adminService.adminWork(data).enqueue(object :
                retrofit2.Callback<ErrorReturn> {
                override fun onResponse(call: Call<ErrorReturn>, response: Response<ErrorReturn>) {

                }

                override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })

            showWork(projectList[i-1])
            i--
            if (i==-1){
                UseActivity().replaceFragment(RaiseProjectListFragment())
            }
        }

        noPass.setOnClickListener {
            val adminService = ServiceCreator.create(UserService::class.java)
            val data = HttpAdminWork(projectList[i].Title,"管理员","false",token)
            adminService.adminWork(data).enqueue(object :
                retrofit2.Callback<ErrorReturn> {
                override fun onResponse(call: Call<ErrorReturn>, response: Response<ErrorReturn>) {

                }

                override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })

            showWork(projectList[i-1])
            i--
            if (i==-1){
                UseActivity().replaceFragment(RaiseProjectListFragment())
            }
        }
    }

    private fun showWork(project: RaiseProject){
        adminContentView.text=project.Introduce
        adminTitleView.text=project.Title
        UseActivity().getImageToShare(project.Image,adminImageView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_frag,container,false)
    }
}