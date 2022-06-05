package fun.listenia.moogow;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import dev.morphia.query.Type;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomFilter {

    List<Filter> filters = new ArrayList<>();

    public CustomFilter eq (String field, Object value) {
        filters.add(Filters.eq(field, value));
        return this;
    }

    public CustomFilter ne (String field, Object value) {
        filters.add(Filters.ne(field, value));
        return this;
    }

    public CustomFilter type (String field, Type value) {
        filters.add(Filters.type(field, value));
        return this;
    }

    public CustomFilter near (String field, Point value) {
        filters.add(Filters.near(field, value));
        return this;
    }

    public CustomFilter near (String field, Position value) {
        filters.add(Filters.near(field, new Point(value)));
        return this;
    }

    public CustomFilter near (String field, double x, double y) {
        filters.add(Filters.near(field, new Point(new Position(x, y))));
        return this;
    }

    public CustomFilter near (String field, int value) {
        filters.add(Filters.size(field, value));
        return this;
    }

    public CustomFilter where (String field) {
        filters.add(Filters.where(field));
        return this;
    }

    public CustomFilter gte (String field, Number value) {
        filters.add(Filters.gte(field, value));
        return this;
    }

    public CustomFilter lte (String field, Number value) {
        filters.add(Filters.lte(field, value));
        return this;
    }

    public CustomFilter gt (String field, Number value) {
        filters.add(Filters.gt(field, value));
        return this;
    }

    public CustomFilter lt (String field, Number value) {
        filters.add(Filters.lt(field, value));
        return this;
    }

    public CustomFilter box (String field, Point p1, Point p2) {
        filters.add(Filters.box(field, p1, p2));
        return this;
    }

    public CustomFilter box (String field, double x1, double y1, double x2, double y2) {
        Point p1 = new Point(new Position(x1, y1));
        Point p2 = new Point(new Position(x2, y2));
        filters.add(Filters.box(field, p1, p2));
        return this;
    }

    public CustomFilter exists (String field) {
        filters.add(Filters.exists(field));
        return this;
    }

    public CustomFilter in (String field, Object... values) {
        filters.add(Filters.in(field, List.of(values)));
        return this;
    }

    public CustomFilter notIn (String field, Object value) {
        filters.add(Filters.nin(field, value));
        return this;
    }

    public CustomFilter size (String field, int value) {
        filters.add(Filters.size(field, value));
        return this;
    }

    public CustomFilter isEmpty (String field) {
        filters.add(Filters.size(field, 0));
        return this;
    }

    public CustomFilter geo (@NotNull Consumer<GeoCustomFilter> geo) {
        GeoCustomFilter geoCustomFilter = new GeoCustomFilter();
        geo.accept(geoCustomFilter);
        filters.addAll(geoCustomFilter.getFilters());
        return this;
    }

    public CustomFilter addFilter (Filter filter) {
        filters.add(filter);
        return this;
    }

    public List<Filter> getFilters () {
        return filters;
    }

}
