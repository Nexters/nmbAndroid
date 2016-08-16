package com.nexters.naemambo.naemambo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nexters.naemambo.naemambo.MyboxDetailDoneActivity;
import com.nexters.naemambo.naemambo.MyboxDetailGeneralActivity;
import com.nexters.naemambo.naemambo.MyboxDetailShareActivity;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.adapter.MessageAdapter;
import com.nexters.naemambo.naemambo.util.BaseFragment;
import com.nexters.naemambo.naemambo.util.Const;

public class ReceiveFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private MessageAdapter adapter;
    private ListView receiveFragment;


    public ReceiveFragment() {
        // Required empty public constructor
    }

    public static ReceiveFragment newInstance(String param1, String param2) {
        ReceiveFragment fragment = new ReceiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        adapter = new MessageAdapter(getContext(), R.layout.message_list_item);
        receiveFragment = (ListView) view.findViewById(R.id.receive_listview);
        receiveFragment.setAdapter(adapter);
        addItem(adapter, 0, "테스트제목1", "날 으아으아 하게 만들어줘", "2016.08.01");
        addItem(adapter, 1, "테스트제목2", "내 마음이 보이니 테스트 넥스터즈 전한경 최봉재 임주현 ", "2016.08.02");
        addItem(adapter, 2, "테스트제목3", "날 으아으아 하게 만들어줘", "2016.08.01");
        adapter.notifyDataSetChanged();
        receiveFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int boxType = adapter.getItem(position).boxType;
                if (boxType == Const.GENERAL_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailGeneralActivity.class).putExtra(Const.BOX_DETAIL_GENERAL, adapter.getItem(position)));
                } else if (boxType == Const.LOCK_BOX) {
                    dialogInit();
                    dialog_hope_msg_show();
                } else if (boxType == Const.DONE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailDoneActivity.class).putExtra(Const.BOX_DETAIL_DONE, adapter.getItem(position)));
                } else if (boxType == Const.SHARE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailShareActivity.class).putExtra(Const.BOX_DETAIL_SHARE, adapter.getItem(position)));
                }
            }
        });
        return view;
    }

}
