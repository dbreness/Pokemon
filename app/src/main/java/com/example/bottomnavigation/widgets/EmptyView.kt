package com.example.bottomnavigation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.EmptyViewBinding
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.squareup.picasso.Picasso

class EmptyView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var buttonTapped: (() -> Unit)? = null
    private lateinit var binding: EmptyViewBinding


    init {
        binding = EmptyViewBinding.inflate(LayoutInflater.from(context), this,true)
        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.EmptyView,
                defStyleAttr,
                defStyleRes
        )

        binding.textView.text = typedArray.getString(R.styleable.EmptyView_textInformation)
//        Picasso.get().load(typedArray.getString(R.styleable.EmptyView_imageInformationUrl)).into(binding.imageView)

        typedArray.recycle()
    }

}