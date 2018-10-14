import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


@Getter
@Setter
@ToString
public class Coordinates {
    private Double latitude, longitude;

    @SneakyThrows
    public static Coordinates parseCoordinates(String url){
        Coordinates coords = new Coordinates();
        Document city_doc = Jsoup.connect(url).get();
        Elements geo_elements = city_doc.select(".geo");
        if (geo_elements.size() != 0){
            String[] text_coords = geo_elements.get(0).text().split("\\s+");
            coords.setLatitude(Double.parseDouble(text_coords[0].
                                      substring(0, text_coords[0].length() - 1)));
            coords.setLongitude(Double.parseDouble(text_coords[1]));
        }
        else if (city_doc.select(".mw-kartographer-maplink").size() != 0){
            coords.setLatitude(Double.parseDouble(city_doc.select(".mw-kartographer-maplink").
                                                           get(0).attr("data-lat")));
            coords.setLongitude(Double.parseDouble(city_doc.select(".mw-kartographer-maplink").
                                                            get(0).attr("data-lon")));
        }
        else{
            coords.setLatitude(null);
            coords.setLongitude(null);
        }
        return coords;
    }
}
