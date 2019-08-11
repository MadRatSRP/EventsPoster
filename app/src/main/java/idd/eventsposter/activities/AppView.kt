package ui.app_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import idd.eventsposter.R
import kotlinx.android.synthetic.main.activity_app.*

class AppView : AppCompatActivity(), AppVP.View {


    private var appPresenter: AppPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        //toolbar = findViewById(R.id.toolbar);
        setUp()
    }

    /*override fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment).commit()
    }*/

    override fun setUp() {
        setMVP()
        setSupportActionBar(toolbar)
        //getSupportActionBar().setTitle("AppMain");
        //appPresenter!!.addFragment(MapView())
    }

    override fun setMVP() {
        appPresenter = AppPresenter(this)
    }
}
