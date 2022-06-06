package fun.listenia.moogow.finder;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;

import java.util.ArrayList;
import java.util.List;

public class GeoNearCustomFilter {

    private String field;
    private int x, y;
    private int minDistance, maxDistance;

    public GeoNearCustomFilter (String fiel) {
        this.field = field;
    }

    public GeoNearCustomFilter setX (int x) {
        this.x = x;
        return this;
    }

    public GeoNearCustomFilter setY (int y) {
        this.y = y;
        return this;
    }

    public GeoNearCustomFilter setMinDistance (int minDistance) {
        this.minDistance = minDistance;
        return this;
    }

    public GeoNearCustomFilter setMaxDistance (int maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }


    public Filter buildFilter () {
        List<Filter> filters = new ArrayList<>();
        filters.add(Filters.near(field, new Point(new Position(x, y))));
        if (minDistance != 0) {
            filters.add(Filters.minDistance(field, minDistance));
        }
        if (maxDistance != 0) {
            filters.add(Filters.maxDistance(field, maxDistance));
        }
        return Filters.and(filters.toArray(new Filter[0]));
    }

}
