package com.example.chinesehelpchinese

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_raise_project.*
import kotlinx.android.synthetic.main.activity_use.*

class RaiseProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_project)
        //实际上还没确定从哪传过来，下面这几行除了变量名都可以堪称是废的
        val projectTitle = intent.getStringExtra("PROJECT_TITLE")
        val projectIntroduce = intent.getStringExtra("PROJECT_INTRODUCE")
        val projectImage = intent.getStringExtra("PROJECT_IMAGE").toString()
        val projectTarget = intent.getIntExtra("PROJECT_TARGET",0)
        val projectNow = intent.getIntExtra("PROJECT_NOW",0)
        var token = intent.getStringExtra("token")
        var mode = intent.getStringExtra("mode")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        UseActivity().getImageToShare(projectImage,projectImageView)
//        Glide.with(this).load(projectImageId).into(projectImageView)
        projectContent.text=projectIntroduce
        projectTitleView.text=projectTitle
        raiseProgressBar.progress=projectTarget/projectNow



        raiseMoney.setOnClickListener {
            val dialog = MoneyDialogFragment()
            val bundle =Bundle()
            bundle.putString("title",projectTitle)
            bundle.putString("token",token)
            bundle.putString("mode",mode)
            bundle.putInt("nowMoney",projectNow)
            bundle.putInt("targetMoney",projectTarget)
            dialog.arguments=bundle
            dialog.onCreateDialog(null).show()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
//进度条.progress=数据库拔下来的已捐款数目/目标数目