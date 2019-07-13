package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var benderObj = Bender()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            val status = it.getString("STATUS") ?: Bender.Status.NORMAL.name
            val question = it.getString("QUESTION") ?: Bender.Question.NAME.name
            benderObj.question = Bender.Question.valueOf(question)
            benderObj.status = Bender.Status.valueOf(status)
            val color = benderObj.status.color
            iv_bender.setColorFilter(
                    Color.rgb(color.first, color.second, color.third),
                    PorterDuff.Mode.MULTIPLY)
        }
        tv_text.text = benderObj.askQuestion()
        iv_send.setOnClickListener(this)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d(javaClass.name, "onSaveInstanceState")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val (phrase, color) = benderObj.listenAnswer(et_message.text.toString())
            et_message.text.clear()
            iv_bender.setColorFilter(
                    Color.rgb(color.first, color.second, color.third),
                    PorterDuff.Mode.MULTIPLY)
            tv_text.text = phrase
        }
    }
}
