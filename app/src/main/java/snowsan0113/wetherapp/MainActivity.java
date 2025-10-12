package snowsan0113.wetherapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import snowsan0113.wetherapp.listener.ButtonListener;

public class MainActivity extends AppCompatActivity {

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
        ButtonListener listener = new ButtonListener(this);
        one_button.setOnClickListener(listener);
        two_week_button.setOnClickListener(listener);
        today_tomorrow.setOnClickListener(listener);

    }
}