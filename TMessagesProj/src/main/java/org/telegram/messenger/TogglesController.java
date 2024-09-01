package org.telegram.messenger;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.Map;

public class TogglesController extends BaseController {

    private SharedPreferences togglesPreferences;
    public boolean newsfeedEnabled;
    public boolean previewCommentsEnabled;

    private static final TogglesController[] instances = new TogglesController[UserConfig.MAX_ACCOUNT_COUNT];

    public static TogglesController getInstance(int accountNum) {
        TogglesController local = instances[accountNum];
        if (local == null) {
            synchronized (TogglesController.class) {
                local = instances[accountNum];
                if (local == null) {
                    local = new TogglesController(accountNum);
                    instances[accountNum] = local;
                }
            }
        }
        return local;
    }

    public TogglesController(int num) {
        super(num);

        togglesPreferences = ApplicationLoader.applicationContext.getSharedPreferences("toggles" + currentAccount, Activity.MODE_PRIVATE);
        previewCommentsEnabled = togglesPreferences.getBoolean("preview_comments_enabled", false);
        newsfeedEnabled = togglesPreferences.getBoolean("newsfeed_enabled", false);
    }

    public void updateToggles(Map<String, FirebaseRemoteConfigValue> remoteConfigValues) {
        AndroidUtilities.runOnUIThread(() -> {
            SharedPreferences.Editor editor = togglesPreferences.edit();
            for (Map.Entry<String, FirebaseRemoteConfigValue> value : remoteConfigValues.entrySet()) {
                switch (value.getKey()) {
                    case "preview_comments_enabled":
                        previewCommentsEnabled = value.getValue().asBoolean();
                        editor.putBoolean("preview_comments_enabled", previewCommentsEnabled);
                        break;
                    case "newsfeed_enabled":
                        newsfeedEnabled = value.getValue().asBoolean();
                        editor.putBoolean("newsfeed_enabled", newsfeedEnabled);
                        break;
                }
            }

            editor.commit();
        });
    }

    public void cleanup() {
    }
}
