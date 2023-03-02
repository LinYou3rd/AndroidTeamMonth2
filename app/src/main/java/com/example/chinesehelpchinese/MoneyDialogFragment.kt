package com.example.chinesehelpchinese

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.money_dialog.*

class MoneyDialogFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val title = bundle?.getString("title").toString()
        val token = bundle?.getString("token").toString()
        val mode = bundle?.getString("mode").toString()
        val now = bundle?.getInt("nowMoney",0)
        val target = bundle?.getInt("targetMoney",0)
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            builder.setView(inflater.inflate(R.layout.money_dialog,null))
                .setPositiveButton("确定",DialogInterface.OnClickListener {
                    _,_ ->{
                    val service = ServiceCreator.create(UserService::class.java)
                    val dataGive=HttpGiveMoney(title,numberOfMoney.text.toString().toInt(),mode,token)
                    service.giveMoney(dataGive)
                    if (numberOfMoney.text.toString().toInt()>(now?.let { it1 -> target?.minus(it1) }!!)){
                        val dataDelete = HttpDeleteProject(title,token)
                        service.deleteProject(dataDelete)
                        }
                }
                })
                .setPositiveButton("取消",DialogInterface.OnClickListener {
                        _,_ ->
                })
            builder.create()
        }?:throw java.lang.IllegalStateException("Activity cannot be null")

    }
}