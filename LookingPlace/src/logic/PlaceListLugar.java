package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

public class PlaceListLugar {


public List<String> getRoutes(String lugarInicial, String lugarFinal) {
    Query query = new Query("conexiones", new Term[]{new Atom(lugarInicial), new Atom(lugarFinal), new Variable("Ruta")});
    List<String> routeList = new ArrayList<>();
    if (query.hasSolution()) {
        Map<String, Term> solution = query.oneSolution();
        Term rutaTerm = solution.get("Ruta");
        if (rutaTerm.isList()) {
            List<String> ruta = new ArrayList<>();
            for (Term term : rutaTerm.toTermArray()) {
                ruta.add(term.name());
            }
            routeList = ruta;
        }
    }
    return routeList;
}

}
