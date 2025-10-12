package snowsan0113.wetherapp.listener;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snowsan0113.wetherapp.R;

public class ButtonListener implements View.OnClickListener {

    private Activity activity;

    public ButtonListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View click_view) {
        if (click_view.getId() == R.id.one_hour_button ||
                click_view.getId() == R.id.two_week_button ||
                click_view.getId() == R.id.today_tomorrow_button) {
            Button button = (Button) click_view;

            for (Button button1 : getButtonList()) {
                button1.setTag("off");
                button1.setBackgroundColor(Color.parseColor("#C1C3CC"));
            }

            button.setTag("on");
            button.setBackgroundColor(Color.parseColor("#408EE9"));
        }
    }

    public List<Button> getButtonList() {
        return Arrays.asList(activity.findViewById(R.id.one_hour_button),
                activity.findViewById(R.id.today_tomorrow_button),
                activity.findViewById(R.id.two_week_button));
    }
}
