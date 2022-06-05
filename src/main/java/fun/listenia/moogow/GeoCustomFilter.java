package fun.listenia.moogow;

import com.mongodb.client.model.geojson.*;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoCustomFilter {

    private List<Filter> filters = new ArrayList<>();

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

    public List<Filter> getFilters () {
        return filters;
    }

}
