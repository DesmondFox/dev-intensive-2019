package ru.skillbranch.devintensive.ui.profile

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.InitialsBitmap
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        Log.d(javaClass.name, "onCreate")
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
        savedInstanceState?.let { isEditMode = it.getBoolean(IS_EDIT_MODE, false) }
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(theme: Int) {
        Log.d(javaClass.name, "updateTheme")
        delegate.setLocalNightMode(theme)
    }

    private fun updateUI(profile: Profile) {
        val profileDataMap = profile.toMap()

        for ((k, v) in viewFields) {
            v.text = profileDataMap[k].toString()
        }
        updateAvatar(profile)
    }

    private fun updateAvatar(profile: Profile) {
        val typedValue = TypedValue().also {
            theme.resolveAttribute(R.attr.colorAccent, it, true)
        }

        Utils.toInitials(profile.firstName, profile.lastName)
            ?.let {
                iv_avatar.setImageBitmap(
                    InitialsBitmap(
                        iv_avatar.layoutParams.width,
                        iv_avatar.layoutParams.height
                    )
                        .setInitials(Utils.toInitials(profile.firstName, profile.lastName))
                        .setTextColor(Color.WHITE)
                        .setTextSize(Utils.convertSpTpPx(this, 30F))
                        .setBackgroundColor(typedValue.data)
                        .buildBitmap()
                )
            }
    }

    private fun showCurrentMode(editMode: Boolean) {
        val info = viewFields
            .filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = editMode
            v.isFocusableInTouchMode = editMode
            v.isEnabled = editMode
            v.background.alpha = if (editMode) 255 else 0
        }

        ic_eye.visibility = if (editMode) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = editMode

        with(btn_edit) {
            val filter: ColorFilter? = if (isEditMode) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else null

            val icon = if (isEditMode) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }
}
