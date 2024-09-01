package com.ahmedelfeky.backingapp.View;

public interface Task {
     void hideActionBar();
    int checkScreenWidth();
    void setFrameLayoutViability(boolean Viability);
    void onBackClick(boolean enable);
    void fullScreen();
}
