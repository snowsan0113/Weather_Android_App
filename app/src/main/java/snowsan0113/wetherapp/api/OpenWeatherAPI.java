package snowsan0113.wetherapp.api;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import snowsan0113.wetherapp.util.Utility;

public class OpenWeatherAPI {

    public static OpenWeatherAPIData getData() {
        JsonObject raw_json = Utility.getRawJson("https://api.openweathermap.org/data/2.5/forecast?lat=31.522928&lon=130.558161&appid=&lang=ja");
        return new Gson().fromJson(raw_json, OpenWeatherAPIData.class);
    }

    public static class OpenWeatherAPIData {

        private static final double KELVIN = 273.15;

        @SerializedName("list") List<WeatherList> weatherList;

        public List<WeatherList> getWeatherList() {
            return weatherList;
        }

        public static class WeatherList {
            Main main;
            List<Weather> weather;
            String dt_txt;

            public Main getMain() {
                return main;
            }

            public List<Weather> getWeather() {
                return weather;
            }

            @SuppressLint("SimpleDateFormat")
            public String getStringDate(boolean raw_data) {
                if (raw_data) {
                    return dt_txt;
                }
                else {
                    SimpleDateFormat raw_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    SimpleDateFormat convert_format = new SimpleDateFormat("MM/dd");
                    try {
                        return convert_format.format(raw_format.parse(dt_txt));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @SuppressLint("SimpleDateFormat")
            public LocalDateTime getLocalDateTime() {
                SimpleDateFormat raw_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date date = raw_format.parse(dt_txt);
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            public static class Main {
                private float temp_min;
                private float temp_max;

                public double getTempMin(boolean raw_data) {
                    if (raw_data) {
                        return temp_min;
                    }
                    else {
                        return (new BigDecimal(temp_min)
                                .subtract(new BigDecimal(KELVIN))
                                .setScale(2, RoundingMode.HALF_DOWN)
                                .doubleValue());
                    }
                }

                public double getTempMax(boolean raw_data) {
                    if (raw_data) {
                        return temp_max;
                    }
                    else {
                        return (new BigDecimal(temp_max)
                                .subtract(new BigDecimal(KELVIN))
                                .setScale(2, RoundingMode.HALF_DOWN)
                                .doubleValue());
                    }
                }
            }

            public static class Weather {
                String main;
                String description;

                public String getMain() {
                    return main;
                }

                public String getDescription() {
                    return description;
                }
            }
        }
    }
}
