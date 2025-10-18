package snowsan0113.wetherapp.listener;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
                for (LinearLayout layout : getLayOut()) {
                    layout.setVisibility(View.GONE);
                }
            }

            button.setTag("on");
            button.setBackgroundColor(Color.parseColor("#408EE9"));
            if (click_view.getId() == R.id.one_hour_button) {
                getLayOut().get(0).setVisibility(View.VISIBLE);
            }
            else if (click_view.getId() == R.id.today_tomorrow_button) {
                getLayOut().get(1).setVisibility(View.VISIBLE);
            }
            else if (click_view.getId() == R.id.two_week_button) {
                getLayOut().get(2).setVisibility(View.VISIBLE);
            }
        }
        else if (click_view.getId() == R.id.weather_location_button) {
            FrameLayout main_ac = activity.findViewById(R.id.main);
            View loc_layout = activity.findViewById(R.id.location_layout);
            loc_layout.bringToFront();
            View main1 = activity.findViewById(R.id.main1);
            main1.setVisibility(View.INVISIBLE);
            loc_layout.setVisibility(View.VISIBLE);
        }
        else if (click_view.getId() == R.id.weather_location_backbutton) {
            View loc_layout = activity.findViewById(R.id.location_layout);
            loc_layout.setVisibility(View.INVISIBLE);
            View main1 = activity.findViewById(R.id.main1);
            main1.setVisibility(View.VISIBLE);
        }
    }

    public List<Button> getButtonList() {
        return Arrays.asList(activity.findViewById(R.id.one_hour_button),
                activity.findViewById(R.id.today_tomorrow_button),
                activity.findViewById(R.id.two_week_button));
    }

    public List<LinearLayout> getLayOut() {
        return Arrays.asList(activity.findViewById(R.id.onehour_layout),
                activity.findViewById(R.id.today_tomorrow_layout),
                activity.findViewById(R.id.twoweek_layout));
    }
}
