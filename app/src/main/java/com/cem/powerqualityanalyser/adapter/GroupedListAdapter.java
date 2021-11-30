package com.cem.powerqualityanalyser.adapter;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.datalist.BaseViewHolder;
import com.cem.powerqualityanalyser.view.datalist.GroupedRecyclerViewAdapter;


import java.util.ArrayList;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter implements RecyclerView.ChildDrawingOrderCallback {

    private ArrayList<MeterGroupListObj> mGroups;
    private int[] colorHeadList;
    private int showDividerCount = 1;

    public GroupedListAdapter(Context context) {
        super(context);
        mGroups = new ArrayList<>();
    }

    public void setmGroups(ArrayList<MeterGroupListObj> mGroups) {
        this.mGroups = mGroups;
    }

    public void setHeadColorList(int[] colorHeadList) {
        this.colorHeadList = colorHeadList;
    }

    public MeterGroupListObj getGroupItem(int index) {
        if (mGroups.size() > index)
            return mGroups.get(index);
        else
            return null;
    }

    public MeterGroupChildObj getGroupChild(int index, int childIndex) {
        if (mGroups.size() > index)
            return mGroups.get(index).getChild(childIndex);
        else
            return null;
    }

    public void addItem(MeterGroupListObj obj) {
        mGroups.add(obj);
    }

    public void removeItem(MeterGroupListObj obj) {
        mGroups.remove(obj);
    }

    public void clearData() {
        mGroups.clear();
    }

    public int getShowDividerCount() {
        return showDividerCount;
    }

    public void setShowDividerCount(int showDividerCount) {
        this.showDividerCount = showDividerCount;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return mGroups.get(groupPosition).getChildSize();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.grid_adapter_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.grid_adapter_child;
    }

    /*@Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        MeterGroupListObj entity = mGroups.get(groupPosition);
        if (entity.getHeaderSize() > 4) {
            holder.setText(R.id.tv_headerLN, entity.getHeaderList().get(4));
            holder.setVisible(R.id.tv_headerL1, View.VISIBLE);
            holder.setVisible(R.id.tv_headerL2, View.VISIBLE);
            holder.setVisible(R.id.tv_headerL3, View.VISIBLE);
            holder.setVisible(R.id.tv_headerLN, View.VISIBLE);
        } else {
            holder.setVisible(R.id.tv_headerLN, View.GONE);
            holder.setText(R.id.tv_headerLN, "");
            if (entity.getHeaderSize() > 3) {
                holder.setText(R.id.tv_headerL3, entity.getHeaderList().get(3));
                holder.setVisible(R.id.tv_headerL1, View.VISIBLE);
                holder.setVisible(R.id.tv_headerL2, View.VISIBLE);
                holder.setVisible(R.id.tv_headerL3, View.VISIBLE);
            } else {
                holder.setVisible(R.id.tv_headerL3, View.GONE);
                holder.setText(R.id.tv_headerL3, "");
                if (entity.getHeaderSize() > 2) {
                    holder.setText(R.id.tv_headerL2, entity.getHeaderList().get(2));
                    holder.setVisible(R.id.tv_headerL1, View.VISIBLE);
                    holder.setVisible(R.id.tv_headerL2, View.VISIBLE);
                } else {
                    holder.setText(R.id.tv_headerL2, "");
                    holder.setVisible(R.id.tv_headerL2, View.GONE);
                    if (entity.getHeaderSize() > 1) {
                        holder.setText(R.id.tv_headerL1, entity.getHeaderList().get(1));
                        holder.setVisible(R.id.tv_headerL1, View.VISIBLE);
                    } else {
                        holder.setText(R.id.tv_headerL1, "");
                        holder.setVisible(R.id.tv_headerL1, View.GONE);
                        if (entity.getHeaderSize() > 0) {
                            holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
                        } else {
                            holder.setText(R.id.tv_header, "");
                        }
                    }
                }
            }
        }


        if (this.colorHeadList == null) {
            holder.setBackgroundColor(R.id.tv_header, ColorList.ALL_METER_TITLE_COLOR[0]);
            holder.setBackgroundColor(R.id.tv_headerL1, ColorList.ALL_METER_TITLE_COLOR[1]);
            holder.setBackgroundColor(R.id.tv_headerL2, ColorList.ALL_METER_TITLE_COLOR[2]);
            holder.setBackgroundColor(R.id.tv_headerL3, ColorList.ALL_METER_TITLE_COLOR[3]);
            holder.setBackgroundColor(R.id.tv_headerLN, ColorList.ALL_METER_TITLE_COLOR[4]);

        } else {
            holder.setTextColor(R.id.tv_header, colorHeadList[0]);
            holder.setTextColor(R.id.tv_headerL1, colorHeadList[1]);
            holder.setTextColor(R.id.tv_headerL2, colorHeadList[2]);
            holder.setTextColor(R.id.tv_headerL3, colorHeadList[3]);
            holder.setTextColor(R.id.tv_headerLN, colorHeadList[4]);
        }
    }*/


    @Override
    public void onBindHeaderViewHolder(final BaseViewHolder holder, int groupPosition) {
        MeterGroupListObj entity = mGroups.get(groupPosition);
        if (entity.getHeaderSize() == 5) {
            holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
            holder.setText(R.id.tv_headerL1, entity.getHeaderList().get(1));
            holder.setText(R.id.tv_headerL2, entity.getHeaderList().get(2));
            holder.setText(R.id.tv_headerL3, entity.getHeaderList().get(3));
            holder.setText(R.id.tv_headerLN, entity.getHeaderList().get(4));
            holder.setVisible(R.id.tv_header, View.VISIBLE, 1);
            holder.setVisible(R.id.tv_headerL1, View.VISIBLE, 1);
            holder.setVisible(R.id.tv_headerL2, View.VISIBLE, 1);
            holder.setVisible(R.id.tv_headerL3, View.VISIBLE, 1);
            holder.setVisible(R.id.tv_headerLN, View.VISIBLE, 1);

            holder.setVisible(R.id.line_headerL1, View.VISIBLE);
            holder.setVisible(R.id.line_headerL2, View.VISIBLE);
            holder.setVisible(R.id.line_headerL3, View.VISIBLE);
            holder.setVisible(R.id.line_headerLN, View.VISIBLE);

        } else {
            holder.setVisible(R.id.tv_headerLN, View.GONE);
            holder.setVisible(R.id.line_headerLN, View.GONE);
            holder.setText(R.id.tv_headerLN, "");
            if (entity.getHeaderSize() == 4) {
                holder.setVisible(R.id.tv_header, View.VISIBLE, 1.09f);
                holder.setVisible(R.id.tv_headerL1, View.VISIBLE, 1);
                holder.setVisible(R.id.tv_headerL2, View.VISIBLE, 1);
                holder.setVisible(R.id.tv_headerL3, View.VISIBLE, 1);
                holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
                holder.setText(R.id.tv_headerL1, entity.getHeaderList().get(1));
                holder.setText(R.id.tv_headerL2, entity.getHeaderList().get(2));
                holder.setText(R.id.tv_headerL3, entity.getHeaderList().get(3));

                holder.setVisible(R.id.line_headerL1, View.VISIBLE);
                holder.setVisible(R.id.line_headerL2, View.VISIBLE);
                holder.setVisible(R.id.line_headerL3, View.VISIBLE);

            } else {
                holder.setVisible(R.id.tv_headerL3, View.GONE);
                holder.setVisible(R.id.line_headerL3, View.GONE);
                holder.setText(R.id.tv_headerL3, "");
                if (entity.getHeaderSize() == 3) {
                    holder.setVisible(R.id.tv_header, View.VISIBLE, 1.333f);
                    holder.setVisible(R.id.tv_headerL1, View.VISIBLE, 1);
                    holder.setVisible(R.id.tv_headerL2, View.VISIBLE, 1);
                    holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
                    holder.setText(R.id.tv_headerL1, entity.getHeaderList().get(1));
                    holder.setText(R.id.tv_headerL2, entity.getHeaderList().get(2));
                    holder.setVisible(R.id.line_headerL1, View.VISIBLE);
                    holder.setVisible(R.id.line_headerL2, View.VISIBLE);

                } else {
                    holder.setText(R.id.tv_headerL2, "");
                    holder.setVisible(R.id.tv_headerL2, View.GONE);
                    holder.setVisible(R.id.line_headerL2, View.GONE);
                    if (entity.getHeaderSize() == 2) {
                        holder.setVisible(R.id.tv_header, View.VISIBLE, 4);
                        holder.setVisible(R.id.tv_headerL1, View.VISIBLE, 1);
                        holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
                        holder.setText(R.id.tv_headerL1, entity.getHeaderList().get(1));
                        holder.setVisible(R.id.line_headerL1, View.VISIBLE);
                    } else {
                        holder.setText(R.id.tv_headerL1, "");
                        holder.setVisible(R.id.tv_headerL1, View.GONE);
                        holder.setVisible(R.id.line_headerL1, View.GONE);
                        if (entity.getHeaderSize() == 1) {
                            holder.setVisible(R.id.tv_header, View.VISIBLE, 1f);
                            holder.setText(R.id.tv_header, entity.getHeaderList().get(0));
                        } else {
                            holder.setVisible(R.id.tv_header, View.GONE);
                        }
                    }
                }
            }
        }
        if (this.colorHeadList == null) {
            holder.setBackgroundColor(R.id.tv_header, ColorList.ALL_METER_TITLE_COLOR[0]);
            holder.setBackgroundColor(R.id.tv_headerL1, ColorList.ALL_METER_TITLE_COLOR[1]);
            holder.setBackgroundColor(R.id.tv_headerL2, ColorList.ALL_METER_TITLE_COLOR[2]);
            holder.setBackgroundColor(R.id.tv_headerL3, ColorList.ALL_METER_TITLE_COLOR[3]);
            holder.setBackgroundColor(R.id.tv_headerLN, ColorList.ALL_METER_TITLE_COLOR[4]);
        } else {
            holder.setTextColor(R.id.tv_header, colorHeadList[0]);
            holder.setTextColor(R.id.tv_headerL1, colorHeadList[1]);
            holder.setTextColor(R.id.tv_headerL2, colorHeadList[2]);
            holder.setTextColor(R.id.tv_headerL3, colorHeadList[3]);
            holder.setTextColor(R.id.tv_headerLN, colorHeadList[4]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "item" + " 头被点击了", Toast.LENGTH_SHORT).show();

            }
        });

        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(v.getContext(), "item" + " 被Focus了", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(v.getContext(), "item" + " 被点击了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    private void setHolderImage(BaseViewHolder holder, MeterGroupChildObj entity, int index, int viewId) {
        if (entity.getItem(index).isrElectricity()) {
            holder.setVisible(viewId, true);
            holder.setImageResource(viewId, R.mipmap.amount_up);
        } else if (entity.getItem(index).isgElectricity()) {
            holder.setVisible(viewId, true);
            holder.setImageResource(viewId, R.mipmap.amount_down);
        } else if (!entity.getItem(index).isgElectricity() && !entity.getItem(index).isrElectricity()) {
            holder.setVisible(viewId, false);
            holder.setImageBitmap(viewId, null);
        }
    }

    /**
     * 容
     *
     * @param holder
     * @param entity
     * @param index
     * @param viewId
     */
    private void setKvarImage(BaseViewHolder holder, MeterGroupChildObj entity, int index, int viewId) {
//        log.e("-------setKvarImage-------" + index +"------" + entity.getItem(index).isrElectricity());
        if (entity.getItem(index).isrElectricity()) {
            holder.setVisible(viewId, true);
            holder.setImageResource(viewId, R.mipmap.rongkang);
        } else if (entity.getItem(index).isgElectricity()) {
            holder.setVisible(viewId, true);
            holder.setImageResource(viewId, R.mipmap.gankang);
        } else if (!entity.getItem(index).isgElectricity() && !entity.getItem(index).isrElectricity()) {
            holder.setVisible(viewId, false);
            holder.setImageBitmap(viewId, null);
        }
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        MeterGroupChildObj entity = mGroups.get(groupPosition).getChildObjList().get(childPosition);
        //       log.e("-----entity.getItemSize()----" + entity.getItemSize());
        if (entity.getItemSize() > 4) {
            if (showDividerCount >= 5) {
                holder.setVisible(R.id.childDivider5, View.VISIBLE);
            } else {
                holder.setVisible(R.id.childDivider5, View.GONE);
            }
            holder.setVisible(R.id.rl_child, View.VISIBLE, 1);
            holder.setVisible(R.id.rl_child1, View.VISIBLE, 1);
            holder.setVisible(R.id.rl_child2, View.VISIBLE, 1);
            holder.setVisible(R.id.rl_child3, View.VISIBLE, 1);
            holder.setVisible(R.id.rl_child4, View.VISIBLE, 1);
            holder.setText(R.id.tv_childLN, entity.getItem(4).getChildStr());
            holder.setText(R.id.tv_childL3, entity.getItem(3).getChildStr());
            holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
            holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());

            holder.setText(R.id.tv_child, entity.getItem(0).getChildSpannStr());
            holder.setText(R.id.tv_child_unitLN, entity.getItem(4).getChildUnit());
            holder.setText(R.id.tv_child_unitL3, entity.getItem(3).getChildUnit());
            holder.setText(R.id.tv_child_unitL2, entity.getItem(2).getChildUnit());
            holder.setText(R.id.tv_child_unitL1, entity.getItem(1).getChildUnit());

            holder.setImageResource(R.id.image_child, entity.getItem(0).getResImagID());
            holder.setImageResource(R.id.image_child1, entity.getItem(0).getResImagID());
            holder.setImageResource(R.id.image_child2, entity.getItem(0).getResImagID());
            holder.setImageResource(R.id.image_child3, entity.getItem(0).getResImagID());
            holder.setImageResource(R.id.image_child4, entity.getItem(0).getResImagID());

            holder.setVisible(R.id.childDivider1, View.VISIBLE);
            holder.setVisible(R.id.childDivider2, View.VISIBLE);
            holder.setVisible(R.id.childDivider3, View.VISIBLE);
            holder.setVisible(R.id.childDivider4, View.VISIBLE);

            setKvarImage(holder, entity, 1, R.id.image_child1);
            setKvarImage(holder, entity, 2, R.id.image_child2);
            setKvarImage(holder, entity, 3, R.id.image_child3);
            setKvarImage(holder, entity, 4, R.id.image_child4);

        } else {
            holder.setVisible(R.id.rl_child4, View.GONE);
            holder.setVisible(R.id.childDivider4, View.GONE);
            if (entity.getItemSize() > 3) {
                holder.setVisible(R.id.rl_child, View.VISIBLE, 1.09f);
                holder.setVisible(R.id.rl_child1, View.VISIBLE, 1);
                holder.setVisible(R.id.rl_child2, View.VISIBLE, 1);
                holder.setVisible(R.id.rl_child3, View.VISIBLE, 1);
                holder.setText(R.id.tv_childL3, entity.getItem(3).getChildStr());
                holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
                holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                holder.setText(R.id.tv_child, entity.getItem(0).getChildSpannStr());

                holder.setText(R.id.tv_child_unitL3, entity.getItem(3).getChildUnit());
                holder.setText(R.id.tv_child_unitL2, entity.getItem(2).getChildUnit());
                holder.setText(R.id.tv_child_unitL1, entity.getItem(1).getChildUnit());
                holder.setImageResource(R.id.image_child, entity.getItem(0).getResImagID());
                holder.setImageResource(R.id.image_child1, entity.getItem(1).getResImagID());
                holder.setImageResource(R.id.image_child2, entity.getItem(2).getResImagID());
                holder.setImageResource(R.id.image_child3, entity.getItem(3).getResImagID());

                holder.setVisible(R.id.childDivider1, View.VISIBLE);
                holder.setVisible(R.id.childDivider2, View.VISIBLE);
                holder.setVisible(R.id.childDivider3, View.VISIBLE);

                setKvarImage(holder, entity, 1, R.id.image_child1);
                setKvarImage(holder, entity, 2, R.id.image_child2);
                setKvarImage(holder, entity, 3, R.id.image_child3);


            } else {
                holder.setVisible(R.id.rl_child3, View.GONE);
                holder.setVisible(R.id.childDivider3, View.GONE);
                if (entity.getItemSize() > 2) {
                    holder.setVisible(R.id.rl_child, View.VISIBLE, 1.333f);
                    holder.setVisible(R.id.rl_child1, View.VISIBLE, 1);
                    holder.setVisible(R.id.rl_child2, View.VISIBLE, 1);
                    holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
                    holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                    holder.setText(R.id.tv_child, entity.getItem(0).getChildSpannStr());
                    holder.setText(R.id.tv_child_unitL2, entity.getItem(2).getChildUnit());
                    holder.setText(R.id.tv_child_unitL1, entity.getItem(1).getChildUnit());
                    holder.setImageResource(R.id.image_child, entity.getItem(0).getResImagID());
                    holder.setImageResource(R.id.image_child1, entity.getItem(1).getResImagID());
                    holder.setImageResource(R.id.image_child2, entity.getItem(2).getResImagID());

                    holder.setVisible(R.id.childDivider1, View.VISIBLE);
                    holder.setVisible(R.id.childDivider2, View.VISIBLE);

                    setKvarImage(holder, entity, 1, R.id.image_child1);
                    setKvarImage(holder, entity, 2, R.id.image_child2);


                } else {
                    holder.setVisible(R.id.rl_child2, View.GONE);
                    holder.setVisible(R.id.childDivider2, View.GONE);
                    if (entity.getItemSize() > 1) {
                        holder.setVisible(R.id.rl_child, View.VISIBLE, 4);
                        holder.setVisible(R.id.rl_child1, View.VISIBLE, 1);
                        holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                        holder.setText(R.id.tv_child, entity.getItem(0).getChildSpannStr());
                        holder.setText(R.id.tv_child_unitL1, entity.getItem(1).getChildUnit());
                        holder.setImageResource(R.id.image_child, entity.getItem(0).getResImagID());
                        holder.setImageResource(R.id.image_child1, entity.getItem(1).getResImagID());
                        holder.setVisible(R.id.childDivider1, View.VISIBLE);

                        setKvarImage(holder, entity, 1, R.id.image_child1);

                    } else {
                        holder.setVisible(R.id.rl_child1, View.GONE);
                        holder.setVisible(R.id.childDivider1, View.GONE);
                        if (entity.getItemSize() == 1) {
                            holder.setVisible(R.id.rl_child, View.VISIBLE);
                            holder.setImageResource(R.id.image_child, entity.getItem(0).getResImagID());
                        }
                        setKvarImage(holder, entity, 0, R.id.image_child1);
                    }
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                }
            }
        });

    }


   /* @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        MeterGroupChildObj entity = mGroups.get(groupPosition).getChildObjList().get(childPosition);
        if (entity.getItemSize() > 4) {
            if (showDividerCount >= 5) {
                holder.setVisible(R.id.childDivider5, View.VISIBLE);
            } else {
                holder.setVisible(R.id.childDivider5, View.GONE);
            }
            holder.setText(R.id.tv_childLN, entity.getItem(4).getChildStr());
            holder.setText(R.id.tv_childL3, entity.getItem(3).getChildStr());
            holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
            holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
            holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());

        } else {
            holder.setText(R.id.tv_childLN, "");
            holder.setVisible(R.id.childDivider5, View.GONE);
            holder.setVisible(R.id.image_child4, false);

            if (entity.getItemSize() > 3) {
                if (showDividerCount >= 4)
                    holder.setVisible(R.id.childDivider4, View.VISIBLE);
                else {
                    holder.setVisible(R.id.childDivider4, View.GONE);
                    holder.setVisible(R.id.rl_child4, View.GONE);
                }
                holder.setText(R.id.tv_childL3, entity.getItem(3).getChildStr());
                holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
                holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());
            } else {
                holder.setText(R.id.tv_childL3, "");
                holder.setVisible(R.id.childDivider4, View.GONE);
                holder.setVisible(R.id.image_child3, View.GONE);
                if (entity.getItemSize() > 2) {
                    if (showDividerCount >= 3)
                        holder.setVisible(R.id.childDivider3, View.VISIBLE);
                    else {
                        holder.setVisible(R.id.childDivider3, View.GONE);
                        holder.setVisible(R.id.rl_child3, View.GONE);
                        holder.setVisible(R.id.rl_child4, View.GONE);
                    }
                    holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
                    holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                    holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());
                } else {
                    holder.setText(R.id.tv_childL2, "");
                    holder.setVisible(R.id.childDivider3, View.GONE);
                    holder.setVisible(R.id.image_child2, false);
                    if (entity.getItemSize() > 1) {
                        if (showDividerCount >= 2)
                            holder.setVisible(R.id.childDivider2, View.VISIBLE);
                        else {
                            holder.setVisible(R.id.childDivider2, View.GONE);
                            holder.setVisible(R.id.rl_child2, View.GONE);
                            holder.setVisible(R.id.rl_child3, View.GONE);
                            holder.setVisible(R.id.rl_child4, View.GONE);
                        }
                        holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
                        holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());
                    } else {
                        holder.setText(R.id.tv_childL1, "");
                        holder.setVisible(R.id.childDivider2, View.GONE);
                        holder.setVisible(R.id.image_child1, false);
                        if (entity.getItemSize() > 0) {
                            if (showDividerCount >= 1)
                                holder.setVisible(R.id.childDivider1, View.VISIBLE);
                            else {
                                holder.setVisible(R.id.childDivider1, View.GONE);
                            }
                            holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());

                        } else {
                            holder.setText(R.id.tv_child, "");
                            holder.setVisible(R.id.childDivider1, View.GONE);
                            holder.setVisible(R.id.image_child, false);
                        }
                    }
                }
            }
        }
    }*/

    /*@Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        MeterGroupChildObj entity = mGroups.get(groupPosition).getChildObjList().get(childPosition);
//        log.e("=====111=====" + entity.getItemSize());
//        log.e("=====222=====" + showDividerCount);
        if (entity.getItemSize() > 0) {
            if (showDividerCount >= 1)
                holder.setVisible(R.id.childDivider1, View.VISIBLE);
            else {
                holder.setVisible(R.id.childDivider1, View.GONE);
            }
            holder.setText(R.id.tv_child, entity.getItem(0).getChildStr());
//            setHolderImage(holder,entity,0,R.id.image_child);
        } else {
            holder.setText(R.id.tv_child, "");
            holder.setVisible(R.id.childDivider1, View.GONE);
            holder.setVisible(R.id.image_child, false);
        }

        if (entity.getItemSize() > 1) {
            if (showDividerCount >= 2)
                holder.setVisible(R.id.childDivider2, View.VISIBLE);
            else {
                holder.setVisible(R.id.childDivider2, View.GONE);
                holder.setVisible(R.id.rl_child2,View.GONE);
                holder.setVisible(R.id.rl_child3,View.GONE);
                holder.setVisible(R.id.rl_child4,View.GONE);
            }
            holder.setText(R.id.tv_childL1, entity.getItem(1).getChildStr());
//            setHolderImage(holder,entity,1,R.id.image_child1);
        } else {
            holder.setText(R.id.tv_childL1, "");
            holder.setVisible(R.id.childDivider2, View.GONE);
            holder.setVisible(R.id.image_child1, false);
        }

        if (entity.getItemSize() > 2) {
            if (showDividerCount >= 3)
                holder.setVisible(R.id.childDivider3, View.VISIBLE);
            else {
                holder.setVisible(R.id.childDivider3, View.GONE);
                holder.setVisible(R.id.rl_child3,View.GONE);
                holder.setVisible(R.id.rl_child4,View.GONE);
            }
            holder.setText(R.id.tv_childL2, entity.getItem(2).getChildStr());
//            setHolderImage(holder,entity,2,R.id.image_child2);
        } else {
            holder.setText(R.id.tv_childL2, "");
            holder.setVisible(R.id.childDivider3, View.GONE);
            holder.setVisible(R.id.image_child2, false);

        }

        if (entity.getItemSize() > 3) {

            if (showDividerCount >= 4)
                holder.setVisible(R.id.childDivider4, View.VISIBLE);
            else {
                holder.setVisible(R.id.childDivider4, View.GONE);
                holder.setVisible(R.id.rl_child4,false);
            }
            holder.setText(R.id.tv_childL3, entity.getItem(3).getChildStr());
//            setHolderImage(holder,entity,3,R.id.image_child3);
        } else {
//            holder.setVisible(R.id.rl_child3,View.GONE);
            holder.setText(R.id.tv_childL3, "");
            holder.setVisible(R.id.childDivider4, View.GONE);
            holder.setVisible(R.id.image_child3, false);

        }

        if (entity.getItemSize() > 4) {

            if (showDividerCount >= 5) {
                holder.setVisible(R.id.childDivider5, View.VISIBLE);
            }else{
                holder.setVisible(R.id.childDivider5, View.GONE);
            }
            holder.setText(R.id.tv_childLN, entity.getItem(4).getChildStr());
//            setHolderImage(holder,entity,4,R.id.image_child4);
        } else {

 //           holder.setVisible(R.id.rl_child4,View.GONE);
            holder.setText(R.id.tv_childLN, "");
            holder.setVisible(R.id.childDivider5, View.GONE);
            holder.setVisible(R.id.image_child4, false);
        }
    }*/

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.table_value_content2_color));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.table_value_content_color));
        }

        holder.itemView.setFocusable(false);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.tableSelectColor));

                } else {

                    if (position % 2 == 1) {
                        holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.table_value_content2_color));


                    } else {
                        holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.table_value_content_color));
                    }

//                    if (holder.itemView.findViewById(R.id.tv_child) != null)
//                        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.listviewitemcolor));
//                    else
//                        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.tableTitlebackground));

                }
            }
        });
    }

    private int itemAccount = 5;

    public void setItemAccount(int i) {
        itemAccount = i;
    }

    @Override
    public int onGetChildDrawingOrder(int i, int i1) {
        return 0;
    }
}