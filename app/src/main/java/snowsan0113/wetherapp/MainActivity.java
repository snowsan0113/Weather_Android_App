package snowsan0113.wetherapp;

import android.graphics.Color;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

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
    }
}