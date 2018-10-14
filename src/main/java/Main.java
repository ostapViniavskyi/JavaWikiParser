import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        // get html from Wikipedia page
        String url = "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";
        Document doc = Jsoup.connect(url).get();
        Elements cities = doc.select("table tr");
        // parse info about cities from html
        City[] parsedCities = new City[cities.size()];
        int counter = 0;
        System.out.println("Wait until cities are being parsed...");
        for (Element city : cities) {
            City myCity = City.parse(city);
            if (myCity != null) {
                parsedCities[counter] = myCity;
                counter++;
            }
        }
        System.out.println("All Ukrainian cities are parsed!");
        // infinite loop to read users queries and output info
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Please enter the city's name (exit to stop): ");
            String cityName = scanner.nextLine();
            // break loop if "exit" was met
            if (cityName.equals("exit")) break;
            // find the city from saved
            City city = null;
            for (int i = 0; i < counter; i++){
                if (parsedCities[i].getName().equals(cityName)){
                    city = parsedCities[i];
                    break;
                }
            }
            if (city == null){
                System.out.println("No such city found! Try again...");
            }
            // print information about the city and weather forecast
            else{
                System.out.println(city.toString());
                System.out.println("Forecast info:");
                System.out.println(WeatherForecaster.getCityForecast(city));
            }
        }
    }
}