package com.shade.journey.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shade.journey.R;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_5 11:31 AM
 * @Description:
 */
public class DoneTripFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.done_trip_fragment, null);
        TextView doneTrip = (TextView) view.findViewById(R.id.doneTrip);
        doneTrip.setText("已完成的行程");
        return doneTrip;
    }
}
