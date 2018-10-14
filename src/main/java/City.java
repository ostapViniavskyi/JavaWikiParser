import lombok.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Getter
@Setter
public class City {
    private String name;
    private String url;
    private String administrativeArea;
    private int numberOfCitizens;
    private String yearOfFound;
    private Coordinates coordinates; // Set this
    private double area;

    private static final int INFO_SIZE = 6;

    public static City parse(Element city) {
        Elements info = city.select("td");
        if (info.size() == INFO_SIZE) {
            Element name_box = info.get(1).select("a").get(0);
            City myCity = new City();
            myCity.setName(name_box.attr("title"));
            myCity.setUrl(String.format("https://uk.wikipedia.org%s", name_box.attr("href")));
            myCity.setAdministrativeArea(info.get(2).select("a").
                                              get(0).attr("title"));
            Element citizens_box = info.get(3);
            if (citizens_box.select("span").size() == 0){
                myCity.setNumberOfCitizens(Integer.
                        parseInt(citizens_box.text().replace(" ", "").
                                                     replace("\u00a0", "")));
            }
            else{
                myCity.setNumberOfCitizens(Integer.
                        parseInt(citizens_box.select("span").get(1).text().
                                              replace(" ", "")));
            }
            myCity.setYearOfFound(info.get(4).select("a").
                                       get(0).attr("title"));
            myCity.setArea(Double.parseDouble(info.get(5).text()));
            // parse coordinates
            myCity.setCoordinates(Coordinates.parseCoordinates(myCity.getUrl()));
            return myCity;
        }
        return null;
    }

    public String toString(){
        Coordinates coord = this.getCoordinates();
        return  "Information about the city:\n" +
                "Name: " + this.getName() + "\n" +
                "Year of found: " + this.getYearOfFound() + "\n" +
                "Area: " + Double.toString(this.getArea()) + "sq km" + "\n" +
                "Population: " + Integer.toString(this.getNumberOfCitizens()) + "\n" +
                "Latitude: " + Double.toString(coord.getLatitude()) + " " +
                "Longitude: " + Double.toString(coord.getLongitude());
    }

}
