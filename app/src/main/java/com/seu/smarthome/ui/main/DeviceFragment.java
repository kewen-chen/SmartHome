package com.seu.smarthome.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seu.smarthome.R;
import com.seu.smarthome.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-04-21.
 */
public class DeviceFragment extends Fragment {

    private RecyclerView deviceList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fgt_device,null);
        deviceList=(RecyclerView)view.findViewById(R.id.fgt_device_list);
        deviceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceList.setHasFixedSize(true);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.fgt_device_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        List<Device> list=new ArrayList<>();

        for(int i=0;i<3;i++)
        {
            Device item=new Device();
            item.deviceType = Device.DEVICE_TYPE_LIGHT;
            item.deviceName = "智能照明";
            list.add(item);
            item = new Device();
            item.deviceType = Device.DEVICE_TYPE_WATER;
            item.deviceName = "智能浇花";
            list.add(item);
            item = new Device();
            item.deviceType = Device.DEVICE_TYPE_FEED;
            item.deviceName = "智能喂食";
            list.add(item);
        }
        DeviceListAdapter adapter=new DeviceListAdapter(list);
        deviceList.setAdapter(adapter);
        return view;
    }

    class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Device> list;

        public DeviceListAdapter(List<Device> itemList){
            this.list=itemList;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;
            View view =LayoutInflater.from(getActivity()).inflate(R.layout.device_list_item, parent, false);
            viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Device item = list.get(position);
            if(item != null){
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                switch (item.deviceType)
                {
                    case Device.DEVICE_TYPE_LIGHT:
                        itemViewHolder.deviceTypeImage.setImageResource(R.mipmap.light);
                        break;
                    case Device.DEVICE_TYPE_WATER:
                        itemViewHolder.deviceTypeImage.setImageResource(R.mipmap.water);
                        break;
                    case Device.DEVICE_TYPE_FEED:
                        itemViewHolder.deviceTypeImage.setImageResource(R.mipmap.pet);
                        break;
                }
                itemViewHolder.deviceName.setText(item.deviceName);
            }
        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView deviceTypeImage;
            TextView deviceName;

            public ItemViewHolder(View view){
                super(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView textView = (TextView)view.findViewById(R.id.device_name);
                        CharSequence deviceName = textView.getText();
                        Intent intent = new Intent();
                        intent.putExtra("deviceName", deviceName);
                        intent.setClass(getActivity(), DeviceControlActivity.class);
                        startActivity(intent);
                    }
                });

                deviceTypeImage =(ImageView)view.findViewById(R.id.device_type_image);
                deviceName = (TextView)view.findViewById(R.id.device_name);
            }
        }


    }

}
