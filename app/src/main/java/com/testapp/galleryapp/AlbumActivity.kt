package com.testapp.galleryapp

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_album.*

class AlbumActivity : AppCompatActivity() {

    var adapter: SingleAlbumAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        setSupportActionBar(my_album_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val folder_name = intent.getStringExtra("folder_name")
        supportActionBar!!.setTitle("" + folder_name)
        val isVideo = intent.getBooleanExtra("isVideo", false)
        init_ui_views(folder_name, isVideo)
    }

    private fun init_ui_views(folderName: String?, isVideo: Boolean?) {

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true).error(R.drawable.ic_image_unavailable)
        val glide = Glide.with(this)
        val builder = glide.asBitmap()

        rvAlbumSelected.layoutManager = GridLayoutManager(this, 2)
        rvAlbumSelected?.setHasFixedSize(true)
        adapter = SingleAlbumAdapter(getAllShownImagesPath(this, folderName, isVideo), this, options, builder, glide, this)
        rvAlbumSelected?.adapter = adapter

        rvAlbumSelected?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> glide.resumeRequests()
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL, AbsListView.OnScrollListener.SCROLL_STATE_FLING -> glide.pauseRequests()
                }
            }
        }
        )
    }

// Read all images path from specified directory.

    private fun getAllShownImagesPath(activity: Activity, folderName: String?, isVideo: Boolean?): MutableList<String> {

        val uri: Uri
        val cursorBucket: Cursor
        val column_index_data: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null

        val selectionArgs = arrayOf("%" + folderName + "%")

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.Media.DATA + " like ? "

        val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)!!

        column_index_data = cursorBucket.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursorBucket.moveToNext()) {
            absolutePathOfImage = cursorBucket.getString(column_index_data)
            if (absolutePathOfImage != "" && absolutePathOfImage != null)
                listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages.asReversed()
    }

    override fun onItemClick(position: String, isVideo: Boolean) {
        val intent = Intent(this, PhotoActivity::class.java)
        intent.putExtra("folder_name", position)
        startActivity(intent)
    }
}