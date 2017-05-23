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

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        Toast.makeText(MainActivity.this, ((BaseCity) data.getSerializableExtra(KEYS.SELECTED_RESULT)).getCityName(), Toast.LENGTH_SHORT).show();
    }
}
