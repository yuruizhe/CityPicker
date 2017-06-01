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

    public String name;
    public final int VERSION = 2;
    public static final int MAX_HEADER_CITY_SIZE = 12;

    public CityPickerPresenter(Context context, String dbName)
    {
        this.context = context;
        if (!TextUtils.isEmpty(dbName))
            this.name = dbName;
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
        Cursor c = getDatabase().query("select distinct city_py_first  from tb_city c order by city_py_first");
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
        AddressDBHelper dbHelper = new AddressDBHelper(context, this.name, this.VERSION);
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
        Cursor c = getDatabase().query("select c._id,c.city_name,c.city_py,c.city_py_first,c.city_code_baidu,c.city_code_amap,c.is_hot from tb_history h left join tb_city c on h.city_id=c._id order by time desc  limit ?", max + "");
        List<BaseCity> datas = getCityFromDb(c);
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
        String cityId = getCityId(city);
        if(cityId == null)
            return;

        ContentValues cv = new ContentValues(2);
        cv.put("time", System.currentTimeMillis() + "");

        //如果之前没有记录过就会插入数据，否则修改时间戳为当前时间
        boolean has = getDatabase().update("tb_history", cv, "city_id=?", cityId) > 0;
        if (!has)
        {
            cv.put("city_id", cityId);
            getDatabase().insert("tb_history", cv);
        }
    }


    /**
     * 获取城市表的主键id
     * @param city
     * @return
     */
    protected String getCityId(BaseCity city)
    {
        String cityId = null;

        // 如果bean中就带有id就直接返回
        // 除了点击定位城市应该都进入这个分支
        if (!TextUtils.isEmpty(city.getId()))
            return city.getId();


        // 根据城市名称查询id
        Cursor c = getDatabase().query("select _id from tb_city where city_name=?", city.getCityName());
        if (c.moveToNext())
        {
            cityId = c.getString(c.getColumnIndex("_id"));
            c.close();
            return cityId;
        }

        // 考虑到城市名称叫法不同。如“北京市”和“北京” 表达的是同一个意思，但是数据库会匹配不到
        // 在这里还会根据百度code或高德code查询一次。
        // 这里之所以没有在一条语句中用or查询，一方面是有可能输入的code为null会报错，另一方面是出于效率的考虑
        if(!TextUtils.isEmpty(city.getCodeByBaidu()))
        {
            c = getDatabase().query("select _id from tb_city where city_code_baidu=?", city.getCodeByBaidu());
            if (c.moveToNext())
            {
                cityId = c.getString(c.getColumnIndex("_id"));
                c.close();
                return cityId;
            }
        }else
        {
            c = getDatabase().query("select _id from tb_city where city_code_amap=?", city.getCodeByAMap());
            if (c.moveToNext())
            {
                cityId = c.getString(c.getColumnIndex("_id"));
                c.close();
                return cityId;
            }
        }

        return cityId;
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
            String codeBD = c.getString(c.getColumnIndex("city_code_baidu"));
            String codeAMap = c.getString(c.getColumnIndex("city_code_amap"));
            String id = c.getString(c.getColumnIndex("_id"));
            String isHot = c.getString(c.getColumnIndex("is_hot"));

            BaseCity baseCity = new BaseCity();
            baseCity.setCityName(name);
            baseCity.setCityPinYin(py);
            baseCity.setCityPYFirst(pyFrist);
            baseCity.setCodeByBaidu(codeBD);
            baseCity.setCodeByAMap(codeAMap);
            baseCity.setId(id);
            baseCity.setHot("T".equals(isHot));
            datas.add(baseCity);
        }
        return datas;
    }

}
