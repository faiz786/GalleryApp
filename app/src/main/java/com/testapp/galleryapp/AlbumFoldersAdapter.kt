package com.testapp.galleryapp

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_layout.view.*

class AlbumFoldersAdapter(val albumList: ArrayList<Albums>, val context: Context, val options: RequestOptions, val glide: RequestBuilder<Bitmap>, val glideMain: RequestManager, val inOnItemClick: IOnItemClick) : RecyclerView.Adapter<AlbumFoldersAdapter.ViewHolder>() {

//    override fun onViewRecycled(holder: ViewHolder?) {
//        if (holder != null) {
//            //glideMain.clear(holder.itemView.thumbnail)
//            // glide.clear(holder.itemView.thumbnail)
//            //Glide.get(context).clearMemory()
//            // holder?.itemView?.thumbnail?.setImageBitmap(null)
//        }// Glide.clear(holder?.itemView?.thumbnail)
//        super.onViewRecycled(holder)
//
//    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder != null) {
            // glideMain.clear(holder.itemView.thumbnail)
            //Glide.get(context).clearMemory()
            // holder?.itemView?.thumbnail?.setImageBitmap(null)

        }

        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindItems(albumList.get(position), glide, options, inOnItemClick, albumList.get(position).isVideo)

        holder?.itemView?.title?.setText(albumList.get(position).folderNames)
        if (albumList.get(position).isVideo)
            holder?.itemView?.photoCount?.setText("" + albumList.get(position).imgCount + " videos")
        else
            holder?.itemView?.photoCount?.setText("" + albumList.get(position).imgCount + " photos")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(albumList: Albums, glide: RequestBuilder<Bitmap>, options: RequestOptions, inOnItemClick: IOnItemClick, isVideo: Boolean) {
            glide.load(albumList.imagePath).apply { options }.thumbnail(0.4f)
                .into(itemView.thumbnail)

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    inOnItemClick.onItemClick(albumList.folderNames!!, isVideo)
                }
            })
        }}

//    interface IOnItemClick {
//        fun onItemClick(position: String, isVideo: Boolean)
//    }
}