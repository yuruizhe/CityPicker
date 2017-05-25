package com.desmond.citypicker.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desmond.citypicker.R;
import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.bean.Options;
import com.desmond.citypicker.finals.KEYS;
import com.desmond.citypicker.presenter.CityPickerPresenter;
import com.desmond.citypicker.tools.PxConvertUtil;
import com.desmond.citypicker.tools.Res;
import com.desmond.citypicker.tools.SysUtil;
import com.desmond.citypicker.views.pull2refresh.RefreshRecyclerView;
import com.desmond.citypicker.views.pull2refresh.callback.IOnItemClickListener;
import com.gjiazhe.wavesidebar.WaveSideBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.desmond.citypicker.presenter.CityPickerPresenter.LISHI_REMEN;

/**
 *
 */
public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener, IOnItemClickListener, WaveSideBar.OnSelectIndexItemListener, AdapterView.OnItemClickListener
{
    protected View title;
    /**
     * 返回按钮
     */
    protected ImageButton titleBackIb;
    /**
     * 搜索框
     */
    protected AutoCompleteTextView titleSearchEt;
    /**
     * 列表
     */
    protected RefreshRecyclerView contentRrv;
    /**
     * 检索栏
     */
    protected WaveSideBar contentWsb;

    /**
     * 定位城市+历史城市+热门城市布局
     */
    protected View headerView;
    /**
     * 历史城市标题
     */
    protected TextView historyTitleTv;
    /**
     * 历史城市容器
     */
    protected GridLayout historyGroupGl;
    /**
     * 热门城市标题
     */
    protected TextView hotTitleTv;
    /**
     * 热门城市容器
     */
    protected GridLayout hotGroupGl;
    /**
     * 自动定位view
     */
    protected TextView gpsTv;


    protected CityPickerAdapter adapter;
    protected CityPickerPresenter cityPickerPresenter;
    protected SearchAdapter searchAdapter;

    /**
     * 自定义城市列表数据源
     */
    protected List<BaseCity> datas;

    /**
     * 右边拼音首字母检索列表
     */
    protected List<String> pyIndex;

    /**
     * 定位城市
     */
    protected BaseCity gpsCity;

    /**
     * 热门城市列表
     */
    protected List<BaseCity> hotCities;

    /**
     * 自定义热门城市ids
     */
    protected String[] hotCitiesId;


    protected List<BaseCity> historyCitys;

    /**
     * 最大历史城市数量
     */
    protected int maxHistory;


    protected int headerCityWidth;

    protected Options options;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_picker);
        init(savedInstanceState);
    }

    /**
     * 获取传入的参数
     */
    protected void receiveDatas()
    {
        options =  getIntent().getParcelableExtra(KEYS.OPTIONS);
        if (options == null)
            options = new Options(this.getApplicationContext());
        options.setContext(this.getApplicationContext());
        gpsCity = options.getGpsCity();
        hotCitiesId = options.getHotCitiesId();
        maxHistory = options.getMaxHistory();

    }

    protected void init(Bundle savedInstanceState)
    {
        receiveDatas();

        registerViews();

        setViewStyle();

        cityPickerPresenter = new CityPickerPresenter(this.getApplicationContext(),options.getCustomDBName());

        datas = cityPickerPresenter.getCitysSort();
        pyIndex = cityPickerPresenter.getIndex();


        //城市列表适配
        adapter = new CityPickerAdapter(this.getApplicationContext(), options.getIndexBarTextColor());
        contentRrv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        contentRrv.setAdapter(adapter);
        contentRrv.addHeaderView(getHeaderView());
        contentRrv.setOnItemClickListener(CityPickerActivity.this);
        contentRrv.disablePullLable();
        adapter.setData(datas);
        adapter.notifyDataSetChanged();

        //设置索引
        contentWsb.setIndexItems(pyIndex.toArray(new String[pyIndex.size()]));
        contentWsb.setOnSelectIndexItemListener(this);

        //搜索结果适配
        searchAdapter = new SearchAdapter(this.getApplicationContext(), datas, cityPickerPresenter);
        titleSearchEt.setAdapter(searchAdapter);
        titleSearchEt.setOnItemClickListener(this);
    }


    /**
     * 修改页面样式
     */
    protected void setViewStyle()
    {
        title.setBackgroundDrawable(options.getTitleBarDrawable());

        titleSearchEt.setTextSize(options.getSearchViewTextSize());
        titleSearchEt.setTextColor(options.getSearchViewTextColor());
        titleSearchEt.setBackgroundDrawable(options.getSearchViewDrawable());

        titleBackIb.setBackgroundDrawable(options.getTitleBarBackBtnDrawable());

        contentWsb.setTextColor(options.getIndexBarTextColor());
        contentWsb.setTextSize(options.getIndexBarTextSize());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && options.isUseImmerseBar())
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = SysUtil.getStatusBarHeight(this.getApplicationContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight + Res.dimenPx(this.getApplicationContext(),R.dimen.title_bar_height));
            title.setPadding(0, statusBarHeight + title.getPaddingTop(), 0, 0);
            title.setLayoutParams(params);
        }

    }


    /**
     * 设置列表的头部view
     *
     * @return
     */
    protected View getHeaderView()
    {
        if (headerView != null) return headerView;

        headerView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.city_picker_header, contentRrv.getRecyclerView(), false);
        gpsTv = findById(headerView, R.id.c_p_header_gps_tv);
        historyTitleTv = findById(headerView, R.id.c_p_header_historytitle_tv);
        historyGroupGl = findById(headerView, R.id.c_p_header_historygroup_gl);
        hotTitleTv = findById(headerView, R.id.c_p_header_hottitle_tv);
        hotGroupGl = findById(headerView, R.id.c_p_header_hotgroup_gl);

        //动态计算每个按钮的宽度：（屏幕宽度-右边距-左边距）/每行按钮个数-单个按钮的右边距
        headerCityWidth = (SysUtil.getScreenWidth(this.getApplicationContext()) - historyGroupGl.getPaddingRight() - historyGroupGl.getPaddingLeft()) / historyGroupGl.getColumnCount() - PxConvertUtil.dip2px(this,10);

        setHeaderViewValue();
        return headerView;
    }

    /**
     * 为头部布局填充内容及值
     */
    protected void setHeaderViewValue()
    {
        //设置自动定位城市
        if (gpsCity != null)
        {
            gpsTv.setVisibility(View.VISIBLE);
            gpsTv.setText(gpsCity.getCityName());
            gpsTv.setOnClickListener(this);
        } else
            gpsTv.setVisibility(View.GONE);

        // 填充历史城市
        maxHistory = maxHistory > CityPickerPresenter.MAX_HEADER_CITY_SIZE ? CityPickerPresenter.MAX_HEADER_CITY_SIZE : maxHistory;
        historyCitys = cityPickerPresenter.getHistoryCity(maxHistory);
        if (historyCitys != null && historyCitys.size() > 0)
        {
            historyTitleTv.setVisibility(View.VISIBLE);
            historyGroupGl.setVisibility(View.VISIBLE);

            //动态创建button填充到布局中
            historyGroupGl.removeAllViews();
            for (int i = 0; i < historyCitys.size(); i++)
            {
                BaseCity city = historyCitys.get(i);
                Button btn = getNewButton();
                btn.setText(city.getCityName());
                btn.setOnClickListener(this);
                btn.setTag(city);
                btn.setId(R.id.header_city_button);
                historyGroupGl.addView(btn);
            }
        } else
        {
            historyTitleTv.setVisibility(View.GONE);
            historyGroupGl.setVisibility(View.GONE);
        }

        // 填充热门城市
        //如果用户没有输入自定义热门城市就从本地数据库中获取默认的热门城市
        if (hotCitiesId == null || hotCitiesId.length == 0)
            hotCities = cityPickerPresenter.getHotCity(CityPickerPresenter.MAX_HEADER_CITY_SIZE);
        else
            hotCities = cityPickerPresenter.getHotCityById(hotCitiesId);

        if (hotCities != null && !hotCities.isEmpty())
        {
            hotTitleTv.setVisibility(View.VISIBLE);
            hotGroupGl.setVisibility(View.VISIBLE);

            //动态创建button填充到布局中
            hotGroupGl.removeAllViews();
            for (int i = 0; i < hotCities.size(); i++)
            {
                BaseCity city = hotCities.get(i);
                Button btn = getNewButton();
                btn.setText(city.getCityName());
                btn.setOnClickListener(this);
                btn.setTag(city);
                btn.setId(R.id.header_city_button);
                hotGroupGl.addView(btn);
            }
        } else
        {
            hotTitleTv.setVisibility(View.GONE);
            hotGroupGl.setVisibility(View.GONE);
        }
    }

    /**
     * 动态创建一个空的Button
     *
     * @return
     */
    protected Button getNewButton()
    {
        int dp10 = PxConvertUtil.dip2px(this.getApplicationContext(),10);
        int dp3 = PxConvertUtil.dip2px(this.getApplicationContext(),3);
        Button btn = new Button(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.height = PxConvertUtil.dip2px(this.getApplicationContext(),40);
        //没有使用权重的原因是当只有一个button的时候宽度会充满全屏
        //这里根据屏幕宽度动态计算button的宽度
        params.width = headerCityWidth;
        params.bottomMargin = dp10;
        params.rightMargin = dp10;
        btn.setLayoutParams(params);

        btn.setPadding(dp3, dp3, dp3, dp3);
        btn.setGravity(Gravity.CENTER);
        btn.setBackgroundResource(R.drawable.button_selector);
        btn.setEllipsize(TextUtils.TruncateAt.END);
        btn.setMaxLines(1);
        btn.setTextColor(getResources().getColor(R.color.black));
        btn.setTextSize(14);
        return btn;
    }


    protected void registerViews()
    {
        title = findById(R.id.title_root_rl);
        titleBackIb = findById(R.id.title_back_ib);
        contentRrv = findById(R.id.c_p_content_rrv);
        contentWsb = findById(R.id.c_p_content_wsb);
        titleSearchEt = findById(R.id.title_txt_et);
        titleBackIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.title_back_ib)// 返回按钮
        {
            onBackPressed();

        } else if (i == R.id.c_p_header_gps_tv)// 点击gps定位
        {
            whenCitySelected(gpsCity);

        } else if (i == R.id.header_city_button)// 点击热门城市或历史城市
        {
            whenCitySelected((BaseCity) v.getTag());

        }

    }

    protected <T extends View> T findById(int id)
    {
        return (T) findViewById(id);
    }

    protected <T extends View> T findById(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    @Override
    public void onItemClick(Object obj, int position)
    {
        whenCitySelected((BaseCity) obj);
    }

    /**
     * 城市选择完成后执行的操作
     *
     * @param city
     */
    protected void whenCitySelected(BaseCity city)
    {
        cityPickerPresenter.saveHistoryCity(city);
        EventBus.getDefault().post(city);
        Intent intent = new Intent();
        intent.putExtra(KEYS.SELECTED_RESULT, city);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 手指在右边索引上滑动的回调
     *
     * @param index
     */
    @Override
    public void onSelectIndexItem(String index)
    {
        if (LISHI_REMEN.equals(index))
        {
            ((LinearLayoutManager) contentRrv.getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(0, 0);
            return;
        }
        for (int i = 0; i < datas.size(); i++)
        {
            if (datas.get(i).getCityPYFirst().equals(index))
            {
                ((LinearLayoutManager) contentRrv.getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(i + adapter.getHeaderSize(), 0);
                return;
            }
        }
    }

    /**
     * 搜索结果的listview点击
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        BaseCity baseCity = (BaseCity) parent.getAdapter().getItem(position);
        titleSearchEt.setText(baseCity.getCityName());
        whenCitySelected(baseCity);
    }

}
