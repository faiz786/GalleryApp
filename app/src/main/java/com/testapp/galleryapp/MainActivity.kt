package com.testapp.galleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_main_content.*

class MainActivity : AppCompatActivity(), IOnItemClick {

    private var folder_name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState != null)
            folder_name = savedInstanceState!!.getString("folder_name")!!

        setSupportActionBar(my_toolbar)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_menu_white_24dp))

        setupNavigationView()

        var extra = intent.extras;
        if (extra != null) {
            var extraData = extra.get("image_url_data") as ArrayList<Albums>
            select_fragment(extraData)
        }

        drawer_layout_listener()
        supportActionBar!!.setTitle("Folders")
    }

    // Navigation item click listener Kotlin source code.
    private fun setupNavigationView() {

        navigation.setNavigationItemSelectedListener { item ->
            drawer_layout.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                //                    R.id.nav_all_folders -> {
                //                    }
                //                    R.id.nav_hidden_folders -> {
                //                    }
            }
            false
        }
    }

    // drawer layout click listener in Kotlin source code.
    private fun drawer_layout_listener() {

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                TODO("Not yet implemented")
            }

            override fun onDrawerOpened(drawerView: View) {
//                supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_keyboard_backspace_white_24dp))
            }

            override fun onDrawerClosed(drawerView: View) {
//                supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.))
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        }
        )
    }

    public fun select_fragment(imagesList: ArrayList<Albums>) {

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160).skipMemoryCache(true)
            .error(R.drawable.ic_launcher_background)
        val glide = Glide.with(this)

        val builder = glide.asBitmap()
        rvAlbums?.layoutManager = GridLayoutManager(this, 2)

        rvAlbums?.setHasFixedSize(true)

        // AlbumFoldersAdapter.kt is RecyclerView Adapter class. we will implement shortly.
        rvAlbums?.adapter = AlbumFoldersAdapter(imagesList, this, options, builder, glide, this)


//        rvAlbums?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                when (newState) {
//                    RecyclerView.SCROLL_STATE_IDLE -> glide.resumeRequests()
//                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL, AbsListView.OnScrollListener.SCROLL_STATE_FLING -> glide.pauseRequests()
//                }
//            }
//        }
//        )

        fab_camera?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
//                launchCamera()
            }
        }
        )
    }

    override fun onItemClick(position: String, isVideo: Boolean) {

        var bundle = Bundle()
        bundle.putString("folder_name", position)
        var intent = Intent(this, AlbumActivity::class.java)
        intent.putExtra("folder_name", position)
        startActivity(intent)
    }
}