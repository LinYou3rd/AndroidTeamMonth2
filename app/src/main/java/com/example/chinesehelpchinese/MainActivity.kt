package com.example.chinesehelpchinese

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.enroll_frag.*
import kotlinx.android.synthetic.main.login_frag.accountText
import kotlinx.android.synthetic.main.login_frag.passwordText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //总觉得很多地方都能简写...语法还不是很熟
    private var mode = "一般用户"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(LoginFragment())

        administratorLogin.setOnClickListener{
            if (mode == "一般用户"||mode=="注册"){
                mode = "管理员"
                administratorLogin.text="一般用户登录"
                enroll.visibility=View.GONE
                modeLine.visibility=View.GONE
            }else{
                mode = "一般用户"
                administratorLogin.text="管理员登录"
                enroll.visibility=View.VISIBLE
                modeLine.visibility=View.VISIBLE
            }

        }

        loginButton.setOnClickListener {

            when(mode){
                "一般用户"-> {
                    val account = accountText.text.toString()
                    val password = passwordText.text.toString()
                    login(account, password, mode)
                }
                "管理员"-> {
                    val account = accountText.text.toString()
                    val password = passwordText.text.toString()
                    login(account, password, mode)
                }
                "注册"-> {
                    val account = accountText.text.toString()
                    val password = passwordText.text.toString()
                    val name = nameEnrollText.text.toString()
                    val identity = identityText.text.toString()
                    val contact = contactText.text.toString()
                    enroll(name,account,password,identity,contact)
                }
            }
        }

        enroll.setOnClickListener {
            replaceFragment(EnrollFragment())
            mode = "注册"
        }
        //loginButton.text="←注册→"  点击注册选项→back→按钮文本未回退，仅更换fragment  怎么单独监听该fragment的时的back


    }

    private fun replaceFragment (fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.loginLayout,fragment)
        transaction.commit()
    }

    private fun login(account:String,password:String,mode:String){
        val data = HttpLogin(account,password,mode)
        val userService = ServiceCreator.create(UserService::class.java)
        userService.login(data).enqueue(object :Callback<LoginReturn> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<LoginReturn>, response: Response<LoginReturn>) {

                if (response.body()?.code==200){
                    Toast.makeText(this@MainActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity,UseActivity::class.java)
                    intent.putExtra("token",response.body()?.token)
                    Log.d("UseInMainActivity",response.body()?.token.toString())
                    intent.putExtra("id",response.body()?.id)
                    intent.putExtra("name",response.body()?.name)
                    intent.putExtra("mode",mode)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@MainActivity,response.errorBody()?.string(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginReturn>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    private fun enroll(name :String, account: String,password: String,identity:String,contact:String){
        val data = HttpEnroll(name,account,password,identity,contact)
       val enrollService = ServiceCreator.create(UserService::class.java)
        enrollService.enroll(data).enqueue(object :Callback<EnrollReturn>{

            override fun onResponse(call: Call<EnrollReturn>, response: Response<EnrollReturn>) {
                if (response.body()?.code==200){
                    Toast.makeText(this@MainActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                    login(account, password, "一般用户")
                }else{
                    Toast.makeText(this@MainActivity,"注册失败",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EnrollReturn>, t: Throwable) {
                t.printStackTrace()
            }

        })



    }

}