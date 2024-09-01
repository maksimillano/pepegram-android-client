package org.telegram.messenger;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class AnalyticsEventsTracker {
    public static void updateCurrentAccount(Integer account) {
        if (BuildVars.DEBUG_VERSION) {
            return;
        }
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(ApplicationLoader.applicationContext);
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        if (account == null) {
            analytics.setUserId(null);
            crashlytics.setUserId("");
        } else {
            analytics.setUserId(String.valueOf(account));
            crashlytics.setUserId(String.valueOf(account));
        }
    }

    public static void trackPreviewCommentsOpen() {
        if (BuildVars.DEBUG_VERSION) {
            return;
        }
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(ApplicationLoader.applicationContext);
        long clientUserId = UserConfig.getInstance(UserConfig.selectedAccount).clientUserId;
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", clientUserId);
        instance.logEvent("pepe_preview_comments", bundle);
    }

    public static void trackNewsfeedOpen() {
        if (BuildVars.DEBUG_VERSION) {
            return;
        }
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(ApplicationLoader.applicationContext);
        long clientUserId = UserConfig.getInstance(UserConfig.selectedAccount).clientUserId;
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", clientUserId);
        instance.logEvent("pepe_newsfeed_open", bundle);
    }
}
