package com.desmond.citypicker.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.dao.AddressDBHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/18
 * @Pacakge com.desmond.citypicker.presenter
 */

public class CityPickerPresenter
{
    private Context context;
    private BriteDatabase db;

    public static final String LISHI_REMEN = "#";

    public String name = "city.sqlite";
    public int version = 1;
    public static final int MAX_HEADER_CITY_SIZE = 12;

    public CityPickerPresenter(Context context,String dbName)
    {
        this.context = context;
        if (!TextUtils.isEmpty(dbName))
            this.name = dbName;
    }


    /**
     * 覆盖基础城市表，同时会清空历史城市表
     *
     * @param datas
     */
    @Deprecated
    public void overrideBaseDb(List<BaseCity> datas)
    {
        getDatabase().delete("tb_city", null, new String[0]);
        getDatabase().delete("tb_history", null, new String[0]);

        StringBuffer sb = new StringBuffer();
        sb.append("intsert into tb_city (city_name,city_py,city_py_first,city_code,is_hot)");
        for (int i = 0; i < datas.size(); i++)
        {
            BaseCity city = datas.get(i);
            sb.append("('").append(city.getCityName()).append("',");
            sb.append("'").append(city.getCityPinYin()).append("',");
            sb.append("'").append(city.getCityPYFirst()).append("',");
            sb.append("'").append(city.getCode()).append("'),");
            sb.append("'").append(city.isHot() ? "T" : "F").append("')");
            if (i + 1 < datas.size())
                sb.append(",");
        }
        getDatabase().execute(sb.toString());
    }


    /**
     * 获取城市列表，并按照首字母排序
     *
     * @return
     */
    public List<BaseCity> getCitysSort()
    {
        Cursor c = getDatabase().query("select * from tb_city order by city_py_first");
        List<BaseCity> datas = getCityFromDb(c);
        c.close();
        return datas;
    }

    /**
     * 获取索引列表
     *
     * @return
     */
    public List<String> getIndex()
    {
        Cursor c = getDatabase().query("select distinct city_py_first  from tb_city order by city_py_first");
        List<String> datas = new ArrayList<>(c.getCount() + 1);
        datas.add(LISHI_REMEN);
        while (c.moveToNext())
        {
            String pyFirst = c.getString(c.getColumnIndex("city_py_first"));
            datas.add(pyFirst);
        }
        c.close();
        return datas;
    }

    /**
     * 获取数据库
     *
     * @return
     */
    private BriteDatabase getDatabase()
    {
        if (db != null) return db;
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        AddressDBHelper dbHelper = new AddressDBHelper(context,this.name, this.version);
        db = sqlBrite.wrapDatabaseHelper(dbHelper, AndroidSchedulers.mainThread());
        return db;
    }


    /**
     * 获取历史城市
     *
     * @param max 返回数据条数
     * @return
     */
    public List<BaseCity> getHistoryCity(int max)
    {
        Cursor c = getDatabase().query("select * from tb_history order by time desc  limit ?", max + "");

        List<BaseCity> datas = new ArrayList<>(c.getCount());
        while (c.moveToNext())
        {
            String name = c.getString(c.getColumnIndex("city_name"));
            String code = c.getString(c.getColumnIndex("city_code"));

            BaseCity baseCity = new BaseCity();
            baseCity.setCityName(name);
            baseCity.setCode(code);
            datas.add(baseCity);
        }
        c.close();
        return datas;
    }

    /**
     * 记录历史城市
     *
     * @param city
     */
    public void saveHistoryCity(BaseCity city)
    {
        ContentValues cv = new ContentValues(3);
        cv.put("time", System.currentTimeMillis() + "");

        //如果之前没有记录过就会插入数据，否则修改时间戳为当前时间
        boolean has = getDatabase().update("tb_history", cv, "city_name=?", city.getCityName()) > 0;
        if (!has)
        {
            cv.put("city_code", city.getCode());
            cv.put("city_name", city.getCityName());
            getDatabase().insert("tb_history", cv);
        }
    }


    /**
     * 获取热门城市
     *
     * @param max 最大条数
     * @return
     */
    public List<BaseCity> getHotCity(int max)
    {
        Cursor c = getDatabase().query("select * from tb_city where is_hot='T' limit ?", max + "");
        List<BaseCity> datas = getCityFromDb(c);
        c.close();
        return datas;
    }


    /**
     * 获取热门城市
     *
     * @param ids
     * @return
     */
    public List<BaseCity> getHotCityById(String... ids)
    {
        int max = ids.length <= CityPickerPresenter.MAX_HEADER_CITY_SIZE ? ids.length : CityPickerPresenter.MAX_HEADER_CITY_SIZE;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < max; i++)
            sb.append(",?");

        Cursor c = getDatabase().query("select * from tb_city where _id in(" + sb.substring(1, sb.length()) + ")", ids);
        List<BaseCity> datas = getCityFromDb(c);
        c.close();
        return datas;
    }

    /**
     * 搜索城市（模糊查询 like key%）
     *
     * @param key
     * @return
     */
    public List<BaseCity> searchCity(String key)
    {
        key = key + "%";
        Cursor c = getDatabase().query("select * from tb_city where city_name like ? or city_py like ? order by city_py_first", key, key);
        List<BaseCity> datas = getCityFromDb(c);
        c.close();
        return datas;
    }

    /**
     * 查询的结果统一转为对象
     *
     * @param c
     * @return
     */
    private List<BaseCity> getCityFromDb(Cursor c)
    {
        List<BaseCity> datas = new ArrayList<>(c.getCount());
        while (c.moveToNext())
        {
            String name = c.getString(c.getColumnIndex("city_name"));
            String py = c.getString(c.getColumnIndex("city_py"));
            String pyFrist = c.getString(c.getColumnIndex("city_py_first"));
            String code = c.getString(c.getColumnIndex("city_code"));
            String id = c.getString(c.getColumnIndex("_id"));
            String isHot = c.getString(c.getColumnIndex("is_hot"));

            BaseCity baseCity = new BaseCity();
            baseCity.setCityName(name);
            baseCity.setCityPinYin(py);
            baseCity.setCityPYFirst(pyFrist);
            baseCity.setCode(code);
            baseCity.setId(id);
            baseCity.setHot("T".equals(isHot));
            datas.add(baseCity);
        }
        return datas;
    }

}
