package com.zcorp.opensportmanagement.data.pref;

import com.zcorp.opensportmanagement.data.IDataManager;

/**
 * Created by janisharali on 27/01/17.
 */

public interface IPreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(IDataManager.LoggedInMode mode);

    Long getCurrentUserId();

    void setCurrentUserId(Long userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getAccessToken();

    void setAccessToken(String accessToken);

}
