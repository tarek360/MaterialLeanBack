package com.github.florent37.materialleanback.cell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import com.github.florent37.materialleanback.MaterialLeanBack;
import com.github.florent37.materialleanback.MaterialLeanBackSettings;
import com.github.florent37.materialleanback.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class CellViewHolder extends RecyclerView.ViewHolder {

  final static float scaleEnlarged = 1.2f;
  final static float scaleReduced = 1.0f;

  protected FrameLayout cell;
  protected boolean enlarged = false;

  protected final MaterialLeanBack.Adapter adapter;
  protected final MaterialLeanBack.ViewHolder viewHolder;
  protected final MaterialLeanBackSettings settings;
  public final int row;

  Animator currentAnimator;

  public CellViewHolder(View itemView, int row, MaterialLeanBack.Adapter adapter,
      MaterialLeanBackSettings settings) {
    super(itemView);
    this.row = row;
    this.adapter = adapter;
    this.settings = settings;

    cell = (FrameLayout) itemView.findViewById(R.id.cell);
    this.viewHolder = adapter.onCreateViewHolder(cell, row);
    this.viewHolder.row = row;
    cell.addView(viewHolder.itemView);
  }

  public void enlarge(boolean withAnimation) {
    if (!enlarged && settings.animateCards) {

      if (currentAnimator != null) {
        currentAnimator.cancel();
        currentAnimator = null;
      }

      int duration = withAnimation ? 300 : 0;

      AnimatorSet animatorSet = new AnimatorSet();
      animatorSet.setDuration(duration);

      List<Animator> animatorList = new ArrayList<>();
      animatorList.add(ObjectAnimator.ofFloat(cell, "scaleX", scaleEnlarged));
      animatorList.add(ObjectAnimator.ofFloat(cell, "scaleY", scaleEnlarged));

      animatorSet.playTogether(animatorList);
      currentAnimator = animatorSet;
      animatorSet.start();

      enlarged = true;
    }
  }

  public void reduce(boolean withAnimation) {
    if (enlarged && settings.animateCards) {
      if (currentAnimator != null) {
        currentAnimator.cancel();
        currentAnimator = null;
      }

      int duration = withAnimation ? 300 : 0;

      AnimatorSet animatorSet = new AnimatorSet();
      animatorSet.setDuration(duration);

      List<Animator> animatorList = new ArrayList<>();
      animatorList.add(ObjectAnimator.ofFloat(cell, "scaleX", scaleReduced));
      animatorList.add(ObjectAnimator.ofFloat(cell, "scaleY", scaleReduced));

      animatorSet.playTogether(animatorList);
      currentAnimator = animatorSet;
      animatorSet.start();

      enlarged = false;
    }
  }

  public void newPosition(int position) {
    if (position == 1) {
      enlarge(true);
    } else {
      reduce(true);
    }
  }

  public void onBind() {
    int cell = getAdapterPosition();
    viewHolder.cell = cell;
    adapter.onBindViewHolder(viewHolder, cell);
  }

  public void setEnlarged(boolean enlarged) {
    this.enlarged = enlarged;
  }
}
