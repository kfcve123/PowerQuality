package com.cem.powerqualityanalyser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.activity.DeviceParametersActivity;
import com.cem.powerqualityanalyser.activity.FlickerActivity;
import com.cem.powerqualityanalyser.activity.HarmonicsActivity;
import com.cem.powerqualityanalyser.activity.IRActivity;
import com.cem.powerqualityanalyser.activity.PhotoActivity;
import com.cem.powerqualityanalyser.activity.PowerEnergyActivity;
import com.cem.powerqualityanalyser.activity.ScopeActivity;
import com.cem.powerqualityanalyser.activity.SetupActivityTest;
import com.cem.powerqualityanalyser.activity.TransientActivityTest;
import com.cem.powerqualityanalyser.activity.TrendChartRecordActivity;
import com.cem.powerqualityanalyser.activity.UnbalanceActivity;
import com.cem.powerqualityanalyser.activity.VoltsAmpsHertzActivity;
import com.cem.powerqualityanalyser.activity.WarningActivity;
import com.cem.powerqualityanalyser.activity.WaveFormCaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.MenuHolder> {

    private  String[] data;
    private  int[] imgid ;
    private  int[] imgid_down ;
    private  Context context;
    private  List<Class> activityClass;
    private  int selectIndex;
    private  long clickTime;

    public SetAdapter(Context context){
        this.context = context;
        setData();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_set_default_item, parent, false);
        final MenuHolder holder=   new MenuHolder(view);
          /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "item"+position+"-" + title + " 被点击了", Toast.LENGTH_SHORT).show();
                intentActivity( holder.getAdapterPosition());

            }
        });
      view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case  MotionEvent.ACTION_UP:

 //                       intentActivity( holder.getAdapterPosition());
                        view.requestFocus();
                        break;
                }
                return false;
            }
        });*/


        return holder;
    }
    public void intentActivity(int selectIndex){
        if (System.currentTimeMillis()- clickTime>1000) {
            clickTime=System.currentTimeMillis();
            this.selectIndex = selectIndex;
            Intent firstIntent = new Intent(context, activityClass.get(selectIndex));
            firstIntent.putExtra(AppConfig.MainPutActivityNameKey, data[selectIndex]);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            /* ((Activity)context).startActivityForResult(firstIntent,101);*/
            switch (selectIndex){
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                case 7:

                break;
            }
            context.startActivity(firstIntent);

            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    private void setData(){
        // int size=13;
        data = context.getResources().getStringArray(R.array.set_content_array);
        TypedArray ar = context.getResources().obtainTypedArray(R.array.set_icon_array);
        final int len = ar.length();
        imgid = new int[len];
        for (int i = 0; i < len; i++){
            imgid[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        TypedArray ar_d = context.getResources().obtainTypedArray(R.array.set_icon_array_down);
         int len_d = ar_d.length();
        imgid_down = new int[len_d];
        for (int i = 0; i < len_d; i++){
            imgid_down[i] = ar_d.getResourceId(i, 0);
        }
        ar_d.recycle();
        initClass();
    }


    private void  initClass(){
        activityClass = new ArrayList<>();
        activityClass.add(ScopeActivity.class);
        activityClass.add(VoltsAmpsHertzActivity.class);
        activityClass.add(PowerEnergyActivity.class);
        activityClass.add(HarmonicsActivity.class);
        activityClass.add(UnbalanceActivity.class);

        activityClass.add(WarningActivity.class);
        activityClass.add(FlickerActivity.class);
        activityClass.add(TransientActivityTest.class);
        activityClass.add(WaveFormCaptureActivity.class);
        activityClass.add(TrendChartRecordActivity.class);

        activityClass.add(DeviceParametersActivity.class);
        activityClass.add(PhotoActivity.class);
        activityClass.add(IRActivity.class);
        activityClass.add(SetupActivityTest.class);

    }
    private boolean wifiEnable,blueEnable,gpsEnable;
    public void setWifiBtGpsEnable(boolean wifiEnabled, boolean blueEnable, boolean gpsEnable) {
        this.wifiEnable = wifiEnabled;
        this.blueEnable = blueEnable;
        this.gpsEnable = gpsEnable;
    }

    private boolean isWifiEnable(){
        return wifiEnable;
    }

    private boolean isBlueEnable(){
        return blueEnable;
    }

    private boolean isGpsEnable(){
        return gpsEnable;
    }



    public interface SetOnItemClickListner{
        void setItemIndex(int index);
        void setItemIndex(int index,boolean toggle);
    }

    private SetOnItemClickListner setOnItemClickListner;

    public void setSetOnItemClickListner(SetOnItemClickListner listner){
        this.setOnItemClickListner = listner;
    }

    private int mLastResourceId = R.mipmap.set_uncheck;

    @Override
    public void onBindViewHolder(@NonNull final MenuHolder holder, final int position) {

        final String title = data[position];
        int icoID=imgid[position];
        holder.set_content.setText(title);
        holder.set_icon.setImageResource(icoID);
        if(holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 2 ||holder.getAdapterPosition() == 4) {
            holder.set_check_button.setVisibility(View.VISIBLE);
            holder.itemView.setFocusable(false);
            switch (holder.getAdapterPosition()){
                case 0:
                    holder.set_check_button.setChecked(wifiEnable);
                    break;
                case 2:
                    holder.set_check_button.setChecked(blueEnable);
                    break;
                case 4:
                    holder.set_check_button.setChecked(gpsEnable);
                    break;
            }

        }else {
            holder.set_check_button.setVisibility(View.INVISIBLE);
            holder.itemView.setFocusable(true);
        }
        holder.set_check_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setOnItemClickListner.setItemIndex(holder.getAdapterPosition(),isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               intentActivity( holder.getAdapterPosition());
                if(setOnItemClickListner!=null)
                    setOnItemClickListner.setItemIndex(holder.getAdapterPosition());

            }
        });

        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.set_icon.setImageResource(imgid_down[position]);
   //                 holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.tableSelectColor));
                    holder.set_item.setBackground(context.getDrawable(R.mipmap.set_check_bg));
                    holder.set_content.setTextColor(context.getResources().getColor(R.color.set_text_color_choose));
                    holder.set_line.setVisibility(View.INVISIBLE);

                } else {
                    holder.set_icon.setImageResource(imgid[position]);
   //                 holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.listviewitemcolor));
                    holder.set_item.setBackground(null);
                    holder.set_content.setTextColor(context.getResources().getColor(R.color.set_text_color));
                    holder.set_line.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (data!=null)
        return data.length;
        else
            return 0;
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        RelativeLayout set_item;
        TextView set_content;
        ImageView set_icon;
        ToggleButton set_check_button;
        View set_line;

        public MenuHolder(View itemView) {
            super(itemView);
            set_item = itemView.findViewById(R.id.set_item);
            set_content = itemView.findViewById(R.id.set_content);
            set_icon = itemView.findViewById(R.id.set_icon);
            set_check_button = itemView.findViewById(R.id.set_check_button);
            set_line = itemView.findViewById(R.id.set_line);
        }
    }

    public void clearData(){
        notifyDataSetChanged();
    }
}
