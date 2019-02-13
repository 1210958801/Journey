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
 * @Date:Create in 2018/12/picture_5 11:30 AM
 * @Description:
 */
public class WaitingTripFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiting_trip_fragment, null);
        TextView waitingTrip = (TextView) view.findViewById(R.id.waitingTrip);
        waitingTrip.setText("待出行");
        return waitingTrip;
    }
}
