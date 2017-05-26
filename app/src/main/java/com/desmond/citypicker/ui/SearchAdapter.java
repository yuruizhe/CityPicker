package com.desmond.citypicker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.desmond.citypicker.R;
import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.presenter.CityPickerPresenter;
import com.desmond.citypicker.tools.Res;

import java.util.List;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/19
 * @Pacakge com.desmond.citypicker.ui
 */

public class SearchAdapter extends BaseAdapter implements Filterable
{
    Context context;
    List<BaseCity> list;
    CityPickerPresenter cityPickerPresenter;

    public SearchAdapter(Context context, List<BaseCity> list, CityPickerPresenter cityPickerPresenter)
    {
        super();
        this.context = context;
        this.list = list;
        this.cityPickerPresenter = cityPickerPresenter;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
            viewHolder.value = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.value.setTextColor(Res.color(context, R.color.black));
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        BaseCity baseCity = list.get(position);
        viewHolder.value.setText(baseCity.getCityName());
        return convertView;
    }

    class ViewHolder
    {
        TextView value;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                String s = constraint.toString().trim();
                FilterResults results = new FilterResults();
                if (s.length() == 0)
                    return results;

                List<BaseCity> citys = cityPickerPresenter.searchCity(constraint.toString().trim());
                results.count = citys.size();
                results.values = citys;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                SearchAdapter.this.list = (List<BaseCity>) results.values;
                if (results.count > 0)
                    notifyDataSetChanged();
                else
                    notifyDataSetInvalidated();
            }
        };
    }


}
