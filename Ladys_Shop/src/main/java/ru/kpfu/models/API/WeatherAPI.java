package ru.kpfu.models.API;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class WeatherAPI {
    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?appid=1d3d1ee8dd65190c320269c3713e4ecc&units=metric&q=";
    private static String ARTIST_SONG_URL = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key=541f41538e0e91239c3cc4624119dfd2&format=json&artist=";


    public String getWeatherData(String city) {
        return Connector(WEATHER_URL, city);
    }
    public String getSizeLoveSinger(String singer){
        return Connector(ARTIST_SONG_URL, singer);
    }

    private String Connector(String url, String param){
        HttpURLConnection weatherCon = null;
        InputStream inputStream =null;
        BufferedReader bufferedReader;

        try {
            URL weatherURL = new URL(url + param);
            weatherCon= (HttpURLConnection) weatherURL.openConnection();
            weatherCon.setRequestMethod("GET");
            weatherCon.setDoInput(true);
            weatherCon.setDoOutput(true);
            weatherCon.connect();

            StringBuffer buffer = new StringBuffer();
            inputStream = weatherCon.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                buffer.append(line+"\n");
            }
            inputStream.close();
            bufferedReader.close();
            weatherCon.disconnect();
            return buffer.toString();


//            URL weatherCity = new URL("api.openweathermap.org/data/2.5/weather?q=London");
//            weatherURL.
        }
        catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (Throwable t) {
                weatherCon.disconnect();
            }
        }

        return null;
    }


}
