package com.falkenapps.macgyver.main;

import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.common.UserSession;

/**
 * Created by FalkenApps:falken on 4/6/17.
 */

public interface MainInteractor {
    void registerSession(UserSession session);
    void registerSearch(Search search);
}
