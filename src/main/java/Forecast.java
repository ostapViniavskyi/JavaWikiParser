import lombok.*;

@Getter
@Setter
public class Forecast {
    private double avgTemp, minTemp, maxTemp, visionKm, maxWindKph, totalPrecipMm;
    private int humidity;
    private String condition;

    @Override
    public String toString(){
        return  "Weather condition: " + this.getCondition() + "\n" +
                "Average temperature: " + Double.toString(this.getAvgTemp()) + "C\n" +
                "Minimal temperature: " + Double.toString(this.getMinTemp()) + "C\n" +
                "Maximal temperature: " + Double.toString(this.getMaxTemp()) + "C\n" +
                "Average humidity: " + Integer.toString(this.getHumidity()) + "%\n" +
                "Vision: " + Double.toString(this.getVisionKm()) + " km\n" +
                "Max wind speed: " + Double.toString(this.getMaxWindKph()) + " kph\n" +
                "Total precipitation: " + Double.toString(this.getTotalPrecipMm()) + " mm";
    }
}
