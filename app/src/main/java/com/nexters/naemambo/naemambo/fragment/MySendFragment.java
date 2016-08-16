package com.nexters.naemambo.naemambo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nexters.naemambo.naemambo.MyboxDetailDoneActivity;
import com.nexters.naemambo.naemambo.MyboxDetailGeneralActivity;
import com.nexters.naemambo.naemambo.MyboxDetailShareActivity;
import com.nexters.naemambo.naemambo.R;
import com.nexters.naemambo.naemambo.adapter.MessageAdapter;
import com.nexters.naemambo.naemambo.util.BaseFragment;
import com.nexters.naemambo.naemambo.util.Const;


public class MySendFragment extends BaseFragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MessageAdapter adapter;
    private ListView mySendListView;
    private Dialog dialog_hope_msg;
    private TextView btn_hope_msg;

    public MySendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MySendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MySendFragment newInstance(String param1, String param2) {
        MySendFragment fragment = new MySendFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_send, container, false);
        adapter = new MessageAdapter(getContext(), R.layout.message_list_item);
        mySendListView = (ListView) view.findViewById(R.id.my_send_listview);
        mySendListView.setAdapter(adapter);
        addItem(adapter, 0, "테스트제목1", "날 으아으아 하게 만들어줘", "2016.08.01");
        addItem(adapter, 1, "테스트제목2", "내 마음이 보이니 테스트 넥스터즈 전한경 최봉재 임주현 ", "2016.08.02");
        addItem(adapter, 2, "테스트제목3", "너에게 하고싶은 이야기를 여기에 쓴다 내 이야기를 듣고 같이 해결했으면 좋겠어...", "2016.08.01");
        adapter.notifyDataSetChanged();
        //서버에서 데이터 가져와서 리스트에 넣으세요.
        mySendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int boxType = adapter.getItem(position).boxType;
                if (boxType == Const.GENERAL_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailGeneralActivity.class).putExtra(Const.BOX_DETAIL_GENERAL, adapter.getItem(position)));
                } else if (boxType == Const.LOCK_BOX) {
                    dialogInit();
                } else if (boxType == Const.DONE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailDoneActivity.class).putExtra(Const.BOX_DETAIL_DONE, adapter.getItem(position)));
                } else if (boxType == Const.SHARE_BOX) {
                    startActivity(new Intent(getContext(), MyboxDetailShareActivity.class).putExtra(Const.BOX_DETAIL_SHARE, adapter.getItem(position)));
                }
            }
        });
        LoadFromServer();
        return view;
    }

    private void LoadFromServer() {
//        getReq();

    }



}
