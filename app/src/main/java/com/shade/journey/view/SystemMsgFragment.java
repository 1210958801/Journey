package com.shade.journey.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shade.journey.R;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_4 picture_4:36 PM
 * @Description:
 */
public class SystemMsgFragment extends Fragment {

    private TextView systemMsg;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_msg_fragment, null);
        systemMsg = (TextView) view.findViewById(R.id.systemMsg);
        systemMsg.setText("第一页");
        return systemMsg;
    }
}
