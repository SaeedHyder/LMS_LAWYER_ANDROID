package com.ingic.lmslawyer.fragments.messages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.fragments.Case.ChatMessagesFragment;
import com.ingic.lmslawyer.fragments.abstracts.BaseFragment;
import com.ingic.lmslawyer.helpers.SimpleDividerItemDecoration;
import com.ingic.lmslawyer.interfaces.OnViewHolderClick;
import com.ingic.lmslawyer.ui.adapters.abstracts.RecyclerViewListAdapter;
import com.ingic.lmslawyer.ui.adapters.myadapters.MessagesListAdapter;
import com.ingic.lmslawyer.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesListFragment extends BaseFragment implements OnViewHolderClick {


    @BindView(R.id.rvMsgList)
    RecyclerView rvMsgList;
    Unbinder unbinder;
    private RecyclerViewListAdapter adapter;
    private ArrayList<Messages> list;

    public MessagesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showWhiteBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.messages)); /*todo Add Search icon */
    }

    private void initAdapter() {
        adapter = new MessagesListAdapter(getDockActivity(), this);
        rvMsgList.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvMsgList.addItemDecoration(new SimpleDividerItemDecoration(getDockActivity()));
        list = new ArrayList();
        list.add(new Messages(R.drawable.man, "John", getResources().getString(R.string.lorem_ipsum_small)));
        list.add(new Messages(R.drawable.iconcircle, "John, William, David ...", getResources().getString(R.string.lorem_ipsum_small)));
        list.add(new Messages(R.drawable.dummy_image, "John", getResources().getString(R.string.lorem_ipsum_small)));

        rvMsgList.setAdapter(adapter);
        adapter.addAll(list);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, int position) {
        getDockActivity().replaceDockableFragment(ChatMessagesFragment.newInstance(false));
    }

    public class Messages {
        int img;
        String name, msgDesc;

        public Messages(int img, String name, String msgDesc) {
            this.img = img;
            this.name = name;
            this.msgDesc = msgDesc;
        }

        public int getImg() {
            return img;
        }

        public String getName() {
            return name;
        }

        public String getMsgDesc() {
            return msgDesc;
        }
    }
}
