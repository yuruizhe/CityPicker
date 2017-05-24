package com.desmond.citypicker.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desmond.citypicker.R;
import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.views.pull2refresh.BaseViewHolder;
import com.desmond.citypicker.views.pull2refresh.SimpleBaseAdapter;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/17
 * @Pacakge com.desmond.citypicker
 */

public class CityPickerAdapter extends SimpleBaseAdapter<BaseCity>
{
    protected  int pyTextColor ;
    public CityPickerAdapter(Context context,int pyTextColor)
    {
        super(context);
        this.pyTextColor = pyTextColor;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType)
    {
        return new BaseViewHolder<BaseCity>(parent, R.layout.city_item)
        {
            private TextView pyTv, nameTv;

            @Override
            public void onInitializeView()
            {
                super.onInitializeView();
                pyTv = findViewById(R.id.c_item_py_tv);
                nameTv = findViewById(R.id.c_item_name_tv);
            }

            @Override
            public void setData(BaseCity object, int pos)
            {
                super.setData(object, pos);
                nameTv.setText(object.getCityName());

                boolean isFirst;
                if (pos > 0)
                    isFirst = !TextUtils.equals(getData().get(pos - 1).getCityPYFirst(), object.getCityPYFirst());
                else
                    isFirst = true;

                if (isFirst)
                {
                    pyTv.setTextColor(pyTextColor);
                    pyTv.setVisibility(View.VISIBLE);
                    pyTv.setText(object.getCityPYFirst());
                } else
                    pyTv.setVisibility(View.GONE);

            }

        };
    }

}
