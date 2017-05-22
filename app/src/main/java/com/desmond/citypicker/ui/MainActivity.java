package com.desmond.citypicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.desmond.citypicker.R;
import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.bean.Options;
import com.desmond.citypicker.finals.KEYS;
import com.desmond.citypicker.tools.Permissions;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Permissions(this).getSDCard().setOnPermissionsListener(new Permissions.IOnPermissionsListener()
        {
            @Override
            public void callback(boolean arg0)
            {

            }
        }).get();

      /*  try
        {
            InputStream is = getAssets().open("BaiduMap_cityCode_1102.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String valueString = null;

//            sb.append("insert into tb_city (city_name, city_py, city_py_first, city_code, is_hot) values ");

            while ((valueString = br.readLine()) != null)
            {
                String[] s = valueString.split(",");
                String py = converterToFirstSpell(s[1]);
                StringBuffer sb = new StringBuffer();
                sb.append("(");
                sb.append("'").append(s[1]).append("',");
                sb.append("'").append(py).append("',");
                sb.append("'").append(py.substring(0,1)).append("',");
                sb.append("'").append(s[0]).append("',");
                sb.append("'").append("F").append("'");
                sb.append("),");
                Log.d("!!!!",sb.toString());
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
*/

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CityPickerActivity.class);

                Options options = new Options();
//                options.setHotCitiesId(new String[]{"2", "9", "18", "11", "66", "1", "80", "49", "100"});
                BaseCity baseCity = new BaseCity();
                baseCity.setCityName("南京市");
                options.setGpsCity(baseCity);
                options.setMaxHistory(3);

                intent.putExtra(KEYS.OPTIONS, options);
                startActivityForResult(intent, 20009);
            }
        });
    }

    public static String converterToFirstSpell(String chines)
    {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++)
        {
            try
            {
                pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        }
        return pinyinName.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        Toast.makeText(MainActivity.this, ((BaseCity) data.getSerializableExtra(KEYS.SELECTED_RESULT)).getCityName(), Toast.LENGTH_SHORT).show();
    }
}
