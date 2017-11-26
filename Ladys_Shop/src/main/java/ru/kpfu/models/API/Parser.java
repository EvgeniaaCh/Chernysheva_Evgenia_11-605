package ru.kpfu.models.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class Parser {
    private String stringTemp;

    public String getWeather(String data) throws JSONException {

        JSONObject jsonObj = new JSONObject(data);

        int temp = (int) getFloat("temp", getObject("main", jsonObj));
        stringTemp = String.valueOf(temp);

        return stringTemp;
    }

    public String getSizeListener(String data) throws JSONException {
        JSONObject jsonObj = new JSONObject(data);
        JSONObject artistObj = jsonObj.getJSONObject("artist");
        JSONObject statsObj = artistObj.getJSONObject("stats");
        long playcount = statsObj.getLong("playcount");
        return String.valueOf(playcount);
    }

    public String getNameArtist(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONObject artistObj = jsonObject.getJSONObject("artist");
        String nameArtist = artistObj.getString("name");
        return nameArtist;
    }
    public String getImgArtist(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONObject artistObj = jsonObject.getJSONObject("artist");
        JSONArray imgArr = artistObj.getJSONArray("image");
        JSONObject imgObj = imgArr.getJSONObject(3);
        String img = imgObj.getString("#text");
        return img;
    }

    private JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getJSONObject(tagName);
    }

    private float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }
}