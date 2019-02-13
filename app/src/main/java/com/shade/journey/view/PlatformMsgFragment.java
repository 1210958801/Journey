package com.shade.journey.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shade.journey.R;
import com.shade.journey.activities.MsgCenterActivity;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_4 picture_4:38 PM
 * @Description:
 */
public class PlatformMsgFragment extends Fragment {

    private TextView platListView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.platform_msg_fragment, null);
        platListView = (TextView) view.findViewById(R.id.platformMsg);
        platListView.setText("第二页");
        return platListView;
    }
}
