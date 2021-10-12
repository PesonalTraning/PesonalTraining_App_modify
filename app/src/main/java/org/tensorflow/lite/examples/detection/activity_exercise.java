package org.tensorflow.lite.examples.detection;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class activity_exercise extends Fragment {
    private View view;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       return inflater.inflate(R.layout.activity_exercise, container, false);
   }
}
