package com.example.chinesehelpchinese

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Size
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.core.view.drawToBitmap
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_use.*
import kotlinx.android.synthetic.main.admin_frag.*
import kotlinx.android.synthetic.main.change_frag.*
import kotlinx.android.synthetic.main.create_frag.*
import kotlinx.android.synthetic.main.project_list_frag.*
import kotlinx.android.synthetic.main.search_frag.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.properties.Delegates

class UseActivity : AppCompatActivity() {
//侧边栏功能完善。搜素框功能优化

    companion object{
        var id by Delegates.notNull<Int>()
        lateinit var token:String
        lateinit var name: String
        lateinit var mode:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use)

        val toolToken = intent.getStringExtra("token").toString()
        token=toolToken
        val toolId =intent.getIntExtra("id",0)
        id=toolId
        val toolName = intent.getStringExtra("name").toString()
        name=toolName
        val toolMode = intent.getStringExtra("mode").toString()
        mode =toolMode


       replaceFragment(RaiseProjectListFragment())

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu)
        }
       //在这里编写侧边栏菜单的事件...呃...还有菜单需要什么功能...记得想一想
        sideView.setNavigationItemSelectedListener {

            item->when(item.itemId){

                R.id.changeInformation->{if (mode=="管理员"){
                    Toast.makeText(this,"去干活，别管这个",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                    replaceFragment(ChangeInformationFragment())
                    drawerLayout.closeDrawers()
                    true
                }

            R.id.search->{
                replaceFragment(SearchFragment())
                drawerLayout.closeDrawers()
                true
            }


            R.id.admin->{
                if (mode!="管理员"){
                    Toast.makeText(this,"你谁啊",Toast.LENGTH_SHORT).show()
                    true
                }else{
                    replaceFragment(AdminWorkFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                //想想Fragment那边怎么搞，就是，审核的项目展示怎么整
            }

            R.id.create->{
                replaceFragment(CreateFragment())
                drawerLayout.closeDrawers()
                true
            }

            else->{
                drawerLayout.closeDrawers()
                false
            }

            }
        }
    }

         fun getData():Pair<MutableList<RaiseProject>,String>{
             Log.d("ADMIN","进入获取数据的方法")
        val projectList:MutableList<RaiseProject> = mutableListOf()
        val adminService = ServiceCreator.create(UserService::class.java)
        adminService.getProjectForAdmin(token).enqueue(object :Callback<GetAllProjectReturn>{

            override fun onResponse(
                call: Call<GetAllProjectReturn>,
                response: Response<GetAllProjectReturn>
            ) {
                if (response.body()?.code==200){
                    //Toast.makeText(this@UseActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                    val data = response.body()?.data
                    if (data != null) {
                        Log.d("ADMIN","开始解析数据")
                        for (str in data){
                            val project = RaiseProject(str["title"].toString(),str["introduce"].toString(),
                                str["targetMoney"]?.toInt() ?: 0,str["nowMoney"]?.toInt()?:0,str["image"].toString())
                            projectList.add(project)
                        }
                    }

                }else{
                    Toast.makeText(this@UseActivity,"前端请求失败",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAllProjectReturn>, t: Throwable) {
                t.printStackTrace()
            }

        })
             if (projectList.size==0){
                 projectList.add(RaiseProject("test","JUST_TEST",12,4,""))
                 projectList.add(RaiseProject("test2","JUST_TEST2",12,4,""))
             }
             Log.d("ADMIN","方法结束，返回list")
        return Pair(projectList,token)
    }

    fun createSureButtonClick(title: String,introduce:String,targetMoney:Int,image:String){
            val createService = ServiceCreator.create(UserService::class.java)
            val data = HttpCreateProject(title,introduce,image,targetMoney)
            createService.createProject(data, token).enqueue(object :Callback<ErrorReturn>{

                override fun onResponse(call: Call<ErrorReturn>, response: Response<ErrorReturn>) {
                    Toast.makeText(this@UseActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })
            //  replaceFragment(RaiseProjectListFragment())
    }

    fun changeButtonClick(contact:String,name:String,identity:String){
            val userService = ServiceCreator.create(UserService::class.java)
            val data = HttpChange(token,id.toUInt(),name,identity,contact)
            userService.changeInformation(data).enqueue(object :Callback<ChangeInformationReturn>{

                override fun onResponse(
                    call: Call<ChangeInformationReturn>,
                    response: Response<ChangeInformationReturn>
                ) {
                    //Toast.makeText(com.example.chinesehelpchinese.UseActivity(),response.body()?.message,Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ChangeInformationReturn>, t: Throwable) {
                    t.printStackTrace()
                }

            })
           //replaceFragment(RaiseProjectListFragment())
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemService = ServiceCreator.create(UserService::class.java)
        when(item.itemId){//这里也还没完工那...
            android.R.id.home->drawerLayout.openDrawer(GravityCompat.START)
            R.id.powerOff->{
                val data  = HttpCreateProject("","","",0)
                itemService.createProject(data,"")
                finish()
            }
        }
        return true
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            albumCode->{
//                if (resultCode==Activity.RESULT_OK&&data!=null){
//                    data.data?.let { uri->
//                        val bitmap = getBitmapFromUri(uri)
//                        println("启动")
//                        imageSaveView.setImageBitmap(bitmap)
//                    }
//                }
//            }
//        }
//    }

//   fun getBitmapFromUri (uri:Uri)= contentResolver.openFileDescriptor(uri,"r")
//        ?.use {
//            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
//        }

    fun putImageToShare(imageView: ImageView?) :String {
        val bitmap = imageView?.drawToBitmap()
        val byStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG,80,byStream)
        val byteArray = byStream.toByteArray()
        val imgString = Base64.encodeToString(byteArray,Base64.DEFAULT)
        return imgString
    }

    fun getImageToShare(imgString: String,imageView: ImageView){
        val byteArray = Base64.decode(imgString,Base64.DEFAULT)
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        Glide.with(imageView).load(bitmap).into(imageView)
    }

     fun getAllProject ():List<RaiseProject>{
        val userService = ServiceCreator.create(UserService::class.java)
        val projectList:MutableList<RaiseProject> = mutableListOf()
        userService.getAllProject(token).enqueue(object :Callback<GetAllProjectReturn>{

            override fun onResponse(
                call: Call<GetAllProjectReturn>,
                response: Response<GetAllProjectReturn>
            ) {
                if (response.body()?.code==200){
                    Toast.makeText(this@UseActivity,response.body()?.message,Toast.LENGTH_SHORT).show()

                    val data = response.body()?.data

                    if (data != null) {
                        for (str in data){
                            val project = RaiseProject(str["Title"].toString(),str["Introduce"].toString(),
                                str["TargetMoney"]?.toInt() ?: 0,str["NowMoney"]?.toInt()?:0,str["Image"].toString())
                            projectList.add(project)
                        }
                    }

                }else{
                    Toast.makeText(this@UseActivity,"前端请求失败",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAllProjectReturn>, t: Throwable) {
                t.printStackTrace()
            }

        })
        if (projectList==null){
            var project = RaiseProject("空标题","空介绍",10,0,"无图言卵")
            repeat(12){
                projectList.add(project)
            }
        }
        return projectList
    }

     private fun replaceFragment (fragment: Fragment){
        val fragmentManager = supportFragmentManager
         val transaction = fragmentManager.beginTransaction()
         transaction.replace(R.id.projectListLayout,fragment)
        transaction.commit()
    }

    fun getProjectInformation ( title:String){
        val projectService = ServiceCreator.create(UserService::class.java)
        projectService.getTargetProject(title,token).enqueue(object :Callback<GetTargetProjectReturn>{
            override fun onResponse(
                call: Call<GetTargetProjectReturn>,
                response: Response<GetTargetProjectReturn>
            ) {
                if (response.body()?.code==200){
                    val intent = Intent(this@UseActivity,RaiseProjectActivity::class.java)
                    intent.putExtra("token",token)
                    intent.putExtra("PROJECT_TITLE",response.body()?.title)
                    intent.putExtra("PROJECT_INTRODUCE",response.body()?.introduce)
                    intent.putExtra("PROJECT_NOW",response.body()?.nowMoney)
                    intent.putExtra("PROJECT_IMAGE",response.body()?.image)
                    intent.putExtra("PROJECT_TARGET",response.body()?.targetMoney)
                    intent.putExtra("mode",mode)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@UseActivity,"请求发送成功，但失败了",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetTargetProjectReturn>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}