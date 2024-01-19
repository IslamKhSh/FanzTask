package com.fanz.task.main;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fanz.task.utils.Utils;

public class PlayerItemDecoration extends RecyclerView.ItemDecoration {

    private int[] plan;

    PlayerItemDecoration(int[] plan){
        this.plan = plan;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (
                (position > 1 && position < plan[0])
                || (position > plan[0] + 1 && position < plan[0] + plan[1])
                || (position > plan[0] + plan[1] + 1 && position < plan[0] + plan[1] + plan[2])
        ) {
            outRect.top = Utils.dpToPx(view.getContext(), 16);
        }
    }
}
