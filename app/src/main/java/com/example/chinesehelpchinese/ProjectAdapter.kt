package com.example.chinesehelpchinese

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideModule

class ProjectAdapter(val context: Context,val projectList : List<RaiseProject>) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    inner class ViewHolder (view: View):RecyclerView.ViewHolder(view){
        val projectImage : ImageView = view.findViewById(R.id.projectImage)
        val projectTitle : TextView = view.findViewById(R.id.projectTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.project_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val project = projectList[position]
            UseActivity().getProjectInformation(project.Title)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val raiseProject = projectList[position]
        holder.projectTitle.text=raiseProject.Title
        UseActivity().getImageToShare(raiseProject.Image,holder.projectImage)
        //Glide.with(context).load(raiseProject.Image).into(holder.projectImage)
    }

    override fun getItemCount() = projectList.size


}

