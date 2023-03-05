package com.example.chinesehelpchinese

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_raise_project.*
import kotlinx.android.synthetic.main.activity_use.*
import kotlinx.android.synthetic.main.money_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class RaiseProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_project)
        //实际上还没确定从哪传过来，下面这几行除了变量名都可以堪称是废的
        val projectTitle = intent.getStringExtra("PROJECT_TITLE")
        val projectIntroduce = intent.getStringExtra("PROJECT_INTRODUCE")
        val projectImage = intent.getStringExtra("PROJECT_IMAGE")
        val projectTarget = intent.getIntExtra("PROJECT_TARGET",0)
        val projectNow = intent.getStringExtra("PROJECT_NOW")?.toInt()
        var token = intent.getStringExtra("token")
        var mode = intent.getStringExtra("mode")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        UseActivity().getImageToShare(projectImage!!,projectImageView)
        projectContent.text=projectIntroduce
        projectTitleView.text=projectTitle
        raiseProgressBar.max=projectTarget
        raiseProgressBar.progress=projectNow!!



        raiseMoney.setOnClickListener {
            AlertDialog.Builder(this)
                .setView(layoutInflater.inflate(R.layout.money_dialog,null,false))
                .setPositiveButton("确定"){_,_->
                    val service = ServiceCreator.create(UserService::class.java)
                    val dataGive=HttpGiveMoney(projectTitle!!,"1",mode!!)
                    service.giveMoney(dataGive,token!!).enqueue(object :Callback<ErrorReturn>{
                        override fun onResponse(
                            call: Call<ErrorReturn>,
                            response: Response<ErrorReturn>
                        ) {
                            if (response.body()?.code==200){
                                Toast.makeText(this@RaiseProjectActivity,"捐款成功",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ErrorReturn>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
                    if ((1+projectNow!!)>=projectTarget){
                        val dataDelete = HttpDeleteProject(projectTitle!!)
                        service.deleteProject(dataDelete,token)
                    }
                }
                .setNegativeButton("取消"){_,_->

                }.create().show()


//                    val dialog = MoneyDialogFragment()
//                    val bundle =Bundle()
//                    bundle.putString("title",projectTitle)
//                    bundle.putString("token",token)
//                    bundle.putString("mode",mode)
//                    bundle.putInt("nowMoney",projectNow)
//                    bundle.putInt("targetMoney",projectTarget)
//                    dialog.arguments=bundle
//                    dialog.onCreateDialog(null).show()
                }

        }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            android.R.id.home->{
//                finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}


//进度条.progress=数据库拔下来的已捐款数目/目标数目