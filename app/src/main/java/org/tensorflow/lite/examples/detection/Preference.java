package org.tensorflow.lite.examples.detection;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Preference extends PreferenceActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
    }
}
