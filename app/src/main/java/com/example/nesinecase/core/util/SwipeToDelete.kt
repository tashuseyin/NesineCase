package com.example.nesinecase.core.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nesinecase.R


abstract class SwipeToDelete(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val background: ColorDrawable = ColorDrawable(Color.RED)
    private val deleteIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_delete)


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        if (dX < 0) {
            // Arka plan rengi
            background.setBounds(
                itemView.right + dX.toInt() - 20,
                itemView.top, itemView.right, itemView.bottom
            )

            deleteIcon?.setBounds(
                itemView.right - 20 - deleteIcon.intrinsicWidth,
                itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2,
                itemView.right - 20,
                itemView.bottom - (itemView.height - deleteIcon.intrinsicHeight) / 2
            )
        } else {
            background.setBounds(0, 0, 0, 0)
            deleteIcon?.setBounds(0,0,0,0)
        }
        background.draw(c)
        deleteIcon?.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}