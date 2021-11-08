package com.cem.powerqualityanalyser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.activity.DipsSwellsActivity;
import com.cem.powerqualityanalyser.activity.EneryCalculatorActivity;
import com.cem.powerqualityanalyser.activity.FlickerActivity;
import com.cem.powerqualityanalyser.activity.HarmonicsActivity;
import com.cem.powerqualityanalyser.activity.PhotoActivity;
import com.cem.powerqualityanalyser.activity.PowerEnergyActivity;
import com.cem.powerqualityanalyser.activity.PowerMonitorActivity;
import com.cem.powerqualityanalyser.activity.ScopeActivity;
import com.cem.powerqualityanalyser.activity.SetupActivity;
import com.cem.powerqualityanalyser.activity.InrushActivity;
import com.cem.powerqualityanalyser.activity.SetupActivityTest;
import com.cem.powerqualityanalyser.activity.TransientActivity;
import com.cem.powerqualityanalyser.activity.TrendChartRecordActivity;
import com.cem.powerqualityanalyser.activity.UnbalanceActivity;
import com.cem.powerqualityanalyser.activity.VoltsAmpsHertzActivity;
import com.cem.powerqualityanalyser.activity.WarningActivity;


import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private  String[] data;
    private  int[] imgid ;
    private  int[] imgid_down ;
    private  Context context;
    private  List<Class> activityClass;
    private  int selectIndex;
    private  long clickTime;

    public MenuAdapter(Context context){
        this.context = context;
        setData();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item, parent, false);
        final MenuHolder holder=   new MenuHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "item"+position+"-" + title + " 被点击了", Toast.LENGTH_SHORT).show();
                intentActivity( holder.getAdapterPosition());

            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case  MotionEvent.ACTION_UP:

                        intentActivity( holder.getAdapterPosition());
                        view.requestFocus();
                        break;
                }
                return false;
            }
        });


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
                    firstIntent.setClass(context, VoltsAmpsHertzActivity.class);
                    firstIntent.putExtra("metertypevalue",2);
                    break;
                case 2:
                    firstIntent.setClass(context, PowerEnergyActivity.class);
                    firstIntent.putExtra("metertypevalue",3);
                    break;
                case 3:
  //                  firstIntent.setClass(context, VoltsAmpsHertzActivity.class);
                    firstIntent.putExtra("metertypevalue",4);
                    break;
                case 4:
  //                  firstIntent.setClass(context, VoltsAmpsHertzActivity.class);
                    firstIntent.putExtra("metertypevalue",5);
                    break;
                case 6:
  //                  firstIntent.setClass(context, VoltsAmpsHertzActivity.class);
                    firstIntent.putExtra("metertypevalue",7);
                    break;

                case 7:
 //                   firstIntent.setClass(context, AllMeterActivity.class);
 //                   log.e("==========" + selectIndex);
                    firstIntent.putExtra("metertypevalue",8);
                break;
            }
 //           if(selectIndex!=11 && selectIndex!=10)
                context.startActivity(firstIntent);

            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    private void setData(){
        // int size=13;
        data = context.getResources().getStringArray(R.array.Subfunction_array);
        TypedArray ar = context.getResources().obtainTypedArray(R.array.Subfunction_arrayRes);
        final int len = ar.length();
        imgid = new int[len];
        for (int i = 0; i < len; i++){
            imgid[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        TypedArray ar_d = context.getResources().obtainTypedArray(R.array.Subfunction_arrayRes_down);
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
        activityClass.add(InrushActivity.class);

        activityClass.add(FlickerActivity.class);
        activityClass.add(TransientActivity.class);
//        activityClass.add(WaveFormCaptureActivity.class);
        activityClass.add(DipsSwellsActivity.class);
        //暂时隐藏
//        activityClass.add(PowerMonitorActivity.class);


//        activityClass.add(DeviceParametersActivity.class);
        activityClass.add(PhotoActivity.class);
//        activityClass.add(IRActivity.class);
        activityClass.add(WarningActivity.class);
        activityClass.add(TrendChartRecordActivity.class);
        //暂时隐藏
//        activityClass.add(EneryCalculatorActivity.class);
        activityClass.add(SetupActivity.class);
//        activityClass.add(SetupActivityTest.class);

    }

    @Override
    public void onBindViewHolder(@NonNull final MenuHolder holder, final int position) {

        final String title = data[position];
        int icoID=imgid[position];
        holder.tv_title.setText(title);
        holder.icon.setImageResource(icoID);
       // holder.itemView.setFocusable(true);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    holder.icon.setImageResource(imgid_down[position]);
   //                 holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.tableSelectColor));
                    holder.content.setBackground(context.getDrawable(R.mipmap.main_item_check_bg));
                    holder.tv_title.setTextColor(context.getResources().getColor(R.color.colorblack));

                } else {
  //                  holder.icon.setImageResource(imgid[position]);
   //                 holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.listviewitemcolor));
                    holder.content.setBackground(null);
                    holder.tv_title.setTextColor(Color.GRAY);

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
        TextView tv_title;
        ImageView icon;
        RelativeLayout content;

        public MenuHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            icon = (ImageView) itemView.findViewById(R.id.icon);

        }
    }

    public void clearData(){
        notifyDataSetChanged();
    }
}
