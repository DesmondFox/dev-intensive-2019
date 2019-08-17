package ru.skillbranch.devintensive.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        iv_avatar.setOnClickListener {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
