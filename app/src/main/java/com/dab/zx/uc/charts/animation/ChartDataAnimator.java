package com.dab.zx.uc.charts.animation;

public interface ChartDataAnimator {

    long DEFAULT_ANIMATION_DURATION = 500;

    void startAnimation(long duration);

    void cancelAnimation();

    boolean isAnimationStarted();

    void setChartAnimationListener(ChartAnimationListener animationListener);

}
