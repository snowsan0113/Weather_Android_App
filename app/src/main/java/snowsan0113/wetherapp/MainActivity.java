package snowsan0113.wetherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import snowsan0113.wetherapp.api.OpenWeatherAPI;
import snowsan0113.wetherapp.listener.ButtonListener;
import snowsan0113.wetherapp.listener.TabClickListener;

public class MainActivity extends AppCompatActivity {

    //private Map<String, View> one_hour_map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View one_button = findViewById(R.id.one_hour_button);
        View two_week_button = findViewById(R.id.two_week_button);
        View today_tomorrow = findViewById(R.id.today_tomorrow_button);
        TabLayout tab = findViewById(R.id.tabLayout);
        ButtonListener listener = new ButtonListener(this);
        one_button.setOnClickListener(listener);
        two_week_button.setOnClickListener(listener);
        today_tomorrow.setOnClickListener(listener);
        tab.getTabAt(0).view.setOnClickListener(new TabClickListener(this));

        one_button.setTag("on");
        one_button.setBackgroundColor(Color.parseColor("#408EE9"));
        findViewById(R.id.onehour_grid).setVisibility(View.VISIBLE);

        ImageView today = findViewById(R.id.today_weather_icon);
        TextView today_text = findViewById(R.id.today_weather_text);
        TextView today_min = findViewById(R.id.today_min_temp);
        TextView today_max = findViewById(R.id.today_max_temp);

        ImageView tomorrow = findViewById(R.id.tomorrow_weather_icon);
        TextView tomorrow_text = findViewById(R.id.tomorrow_weather_text);
        TextView tomorrow_min = findViewById(R.id.tomorrow_min_temp);
        TextView tomorrow_max = findViewById(R.id.tomorrow_max_temp);

        new Thread(() -> {
            OpenWeatherAPI.OpenWeatherAPIData data = OpenWeatherAPI.getData();
            List<OpenWeatherAPI.OpenWeatherAPIData.WeatherList> list = data.getWeatherList();
            OpenWeatherAPI.OpenWeatherAPIData.WeatherList weatherList = list.get(0);
            OpenWeatherAPI.OpenWeatherAPIData.WeatherList.Weather weather_today = weatherList.getWeather().get(0);

            today_text.setText(weather_today.getDescription());

            today_min.setText(weatherList.getMain().getTempMin(false) + "℃");
            today_max.setText(weatherList.getMain().getTempMax(false) + "℃");
            if (weather_today.getDescription().contains("曇り")) {
                today.setImageResource(R.drawable.mark_tenki_kumori);
            }
            else if (weather_today.getDescription().contains("晴れ")) {
                today.setImageResource(R.drawable.mark_tenki_hare);
            }

            Log.d("snowsan01131", String.valueOf(list.size()));
            for (OpenWeatherAPI.OpenWeatherAPIData.WeatherList weatherList1 : list) {
                Log.d("snowsan01133", weatherList1.getLocalDateTime().plusDays(1).getDayOfMonth() + ":" + LocalDateTime.now().plusDays(1).getDayOfMonth());
                Log.d("snowsan01133", String.valueOf(weatherList1.getLocalDateTime().plusDays(1).getDayOfMonth() == LocalDateTime.now().plusDays(1).getDayOfMonth()));
                if (weatherList1.getLocalDateTime().plusDays(1).getDayOfMonth() == LocalDateTime.now().plusDays(1).getDayOfMonth()) {
                    OpenWeatherAPI.OpenWeatherAPIData.WeatherList.Weather weather_tomorrow = weatherList.getWeather().get(0);

                    tomorrow_text.setText(weather_tomorrow.getDescription());

                    tomorrow_min.setText(weatherList1.getMain().getTempMin(false) + "℃");
                    tomorrow_max.setText(weatherList1.getMain().getTempMax(false) + "℃");
                    if (weather_tomorrow.getDescription().contains("曇り")) {
                        tomorrow.setImageResource(R.drawable.mark_tenki_kumori);
                    }
                    else if (weather_tomorrow.getDescription().contains("晴れ")) {
                        tomorrow.setImageResource(R.drawable.mark_tenki_hare);
                    }
                }
            }
        }).start();

        setupLayout();
    }

    public void setupLayout() {
        GridLayout one_hour = findViewById(R.id.onehour_grid);
        one_hour.removeAllViews();

        LocalDateTime local_time_now = LocalDateTime.now();
        int hour = local_time_now.getHour();
        for (int n = 0; n < 24; n++) {
            if (hour > 23) hour = 0;

            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.VERTICAL);
            linear.setGravity(Gravity.CENTER);

            TextView hour_text = new TextView(this);
            hour_text.setGravity(Gravity.CENTER);
            hour_text.setText(String.valueOf(hour));

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 33, getResources().getDisplayMetrics())
            );
            imgParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
            imageView.setLayoutParams(imgParams);
            imageView.setImageResource(R.drawable.mark_tenki_hare);

            TextView max_text = new TextView(this);
            max_text.setText("xx℃");
            max_text.setTextColor(Color.parseColor("#E43232"));
            max_text.setGravity(Gravity.CENTER);

            TextView min_text = new TextView(this);
            min_text.setText("xx℃");
            min_text.setTextColor(Color.parseColor("#3F51B5"));
            min_text.setGravity(Gravity.CENTER);

            linear.addView(hour_text);
            linear.addView(imageView);
            linear.addView(max_text);
            linear.addView(min_text);

            linear.setId(View.generateViewId());

            one_hour.addView(linear);

            hour++;
        }

        LocalDateTime day_local_time = LocalDateTime.now();
        GridLayout two_week_layout = findViewById(R.id.twoweek_grid);
        two_week_layout.removeAllViews();

        for (int n = 0; n < 14; n++) {
            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.VERTICAL);
            linear.setGravity(Gravity.CENTER);

            TextView day_text = new TextView(this);
            day_text.setGravity(Gravity.CENTER);
            day_text.setText(String.valueOf(day_local_time.getDayOfMonth()) + "\n" + day_local_time.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPAN));

            ImageView image = new ImageView(this);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 33, getResources().getDisplayMetrics())
            );
            imgParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
            image.setLayoutParams(imgParams);
            image.setImageResource(R.drawable.mark_tenki_hare);

            TextView max_text = new TextView(this);
            max_text.setText("xx℃");
            max_text.setTextColor(Color.parseColor("#E43232"));
            max_text.setGravity(Gravity.CENTER);
            TextView min_text = new TextView(this);
            min_text.setText("xx℃");
            min_text.setTextColor(Color.parseColor("#3F51B5"));
            min_text.setGravity(Gravity.CENTER);

            linear.addView(day_text);
            linear.addView(image);
            linear.addView(max_text);
            linear.addView(min_text);

            two_week_layout.addView(linear);

            day_local_time = day_local_time.plusDays(1);
        }

        LocalDateTime localDateTime_todaytomorrow = LocalDateTime.now();
        TextView today_text = findViewById(R.id.today_text);
        today_text.setText("今日（" + localDateTime_todaytomorrow.getMonth().getValue() + "/" + localDateTime_todaytomorrow.getDayOfMonth() + ")");
        localDateTime_todaytomorrow = localDateTime_todaytomorrow.plusDays(1);
        TextView tomorrow_text = findViewById(R.id.tomorrow_text);
        tomorrow_text.setText("明日（" + localDateTime_todaytomorrow.getMonth().getValue()  + "/" + localDateTime_todaytomorrow.getDayOfMonth() + ")");

    }
}