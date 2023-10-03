package logic;

import org.jpl7.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author DARWIN
 */

public class PlaceExtractor {

    public static List<String> getPlaceNames() {
        List<String> placeNames = new ArrayList<>();

        Variable X = new Variable("X");
        Query query = new Query("obtener_nombre_lugar", new Term[]{X});

        while (query.hasMoreSolutions()) {
            Map<String, Term> solution = query.nextSolution();
            String placeName = solution.get("X").toString();
            placeNames.add(placeName);
        }

        return placeNames;
    }

    public static void main(String[] args) {
        List<String> places = getPlaceNames();
        for (String place : places) {
            System.out.println(place);
        }
    }
}
