package com.falkenapps.macgyver.search.interfaces;

import android.view.View;

/**
 * Created by FalkenApps:falken on 5/5/17.
 */

public interface NavigateButtonClick {
    void nextButtonClicked(int currentPosition);
    void prevButtonClicked(int currentPosition);
    void saveButtonClicked();
}
