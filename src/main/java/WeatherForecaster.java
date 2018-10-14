import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class WeatherForecaster {

    public static String getCityForecast(City city){
        Double lat = city.getCoordinates().getLatitude();
        Double lon = city.getCoordinates().getLongitude();
        JSONObject response_json;
        String err_msg = "An error occurred while connecting to weather forecasting service";
        if (lat == null || lon == null){
            return "Sorry, we don't support weather forecast for this city =(";
        }
        String url = "http://api.apixu.com/v1/forecast.json";
        try {
            HttpResponse<JsonNode> response = Unirest.get(url).
                    queryString("key", "c9664d3d5e3f49a8b2e113626180610").
                    queryString("q", String.format("%f %f", lat, lon)).asJson();
            response_json = response.getBody().getObject();
        }
        catch (UnirestException ex){
            return err_msg;
        }
        if (response_json == null) return err_msg;
        JSONObject forecast_json = response_json.
                getJSONObject("forecast").
                getJSONArray("forecastday").
                getJSONObject(0).
                getJSONObject("day");
        Forecast forecast = new Forecast();
        forecast.setAvgTemp(forecast_json.getDouble("avgtemp_c"));
        forecast.setMinTemp(forecast_json.getDouble("mintemp_c"));
        forecast.setMaxTemp(forecast_json.getDouble("maxtemp_c"));
        forecast.setVisionKm(forecast_json.getDouble("avgvis_km"));
        forecast.setMaxWindKph(forecast_json.getDouble("maxwind_kph"));
        forecast.setTotalPrecipMm(forecast_json.getDouble("totalprecip_mm"));
        forecast.setHumidity(forecast_json.getInt("avghumidity"));
        forecast.setCondition(forecast_json.getJSONObject("condition").
                getString("text"));;
        return forecast.toString();
    }
}
