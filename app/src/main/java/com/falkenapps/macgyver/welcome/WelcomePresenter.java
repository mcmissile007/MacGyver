package com.falkenapps.macgyver.welcome;

import com.falkenapps.macgyver.common.User;

/**
 * Created by FalkenApps:falken on 4/4/17.
 */

public interface WelcomePresenter {
    void isUserSignedIn();
    void loadSignedUserInfo();
    void onStart();
    void onStop();
}
