package fun.listenia.moogow.finder;

import com.mongodb.client.model.geojson.*;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoCustomFilter {

    private final List<Filter> filters = new ArrayList<>();

    public GeoCustomFilter inPolygon (String field, Point value) {
        filters.add(Filters.geoIntersects(field, value));
        return this;
    }

    public GeoCustomFilter inPolygon (String field, Polygon value) {
        filters.add(Filters.geoIntersects(field, value));
        return this;
    }

    public GeoCustomFilter inPolygon (String field, Position... values) {
        Polygon polygon = new Polygon(Arrays.asList(values));
        filters.add(Filters.geoIntersects(field, polygon));
        return this;
    }

    public GeoCustomFilter inPolygon (String field, List<Position> values) {
        Polygon polygon = new Polygon(values);
        filters.add(Filters.geoIntersects(field, polygon));
        return this;
    }

    public GeoCustomFilter inPolygon (String field, double x1, double y1, double x2, double y2) {
        Position p1 = new Position(x1, y1);
        Position p2 = new Position(x2, y2);
        Position p3 = new Position(x1, y2);
        Position p4 = new Position(x2, y1);
        Polygon polygon = new Polygon(Arrays.asList(p1, p2, p3, p4));
        filters.add(Filters.geoWithin(field, polygon));
        return this;
    }

    public List<Filter> getFilters () {
        return filters;
    }

}
