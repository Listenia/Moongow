package fun.listenia.moogow.finder;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import dev.morphia.DeleteOptions;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;
import fun.listenia.moogow.Moongow;
import fun.listenia.moogow.Sort;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FinderAgent<T> {

    private final Moongow moongow;
    private final Class<T> clazz;
    private final List<Filter> filters;
    private final List<Sort> sorts;

    private int limit = -1;
    private int skip = -1;

    public FinderAgent (Moongow moongow, final Class<T> clazz) {
        this.moongow = moongow;
        this.clazz = clazz;
        this.filters = new ArrayList<>();
        this.sorts = new ArrayList<>();
    }

    public FinderAgent<T> filter (Filter... filters) {
        this.filters.addAll(Arrays.asList(filters));
        return this;
    }

    public FinderAgent<T> sort (Sort... sorts) {
        this.sorts.addAll(Arrays.asList(sorts));
        return this;
    }

    public FinderAgent<T> sort (Filter... sorts) {
        this.filters.addAll(Arrays.asList(sorts));
        return this;
    }

    public FinderAgent<T> limit (int limit) {
        this.limit = limit;
        return this;
    }

    public FinderAgent<T> skip (int skip) {
        this.skip = skip;
        return this;
    }

    @NotNull
    private Query<T> query (FindOptions options) {
        Query<T> query = moongow.getDatastore().find(clazz);
        for (Filter filter : filters)
            query.filter(filter);

        if (limit > 0) options.limit(limit);
        if (skip > 0) options.skip(skip);

        for (Sort sort : sorts) {
            if (sort.type == Sort.Type.ASC_DESC) {
                options.sort(new Document(sort.field, sort.value));
            } else if (sort.type == Sort.Type.NEAR) {
                Integer[] coordinates = (Integer[]) sort.value;
                Filter filter = Filters.near(sort.field, new Point(new Position(coordinates[0], coordinates[1])));
                query.filter(filter);
            } else if (sort.type == Sort.Type.TEXT) {
                Filter filter = new Filter("$text", sort.field, sort.value) {};
                query.filter(filter);
            }
        }
        return query;
    }

    public T findOne () {
        FindOptions options = new FindOptions();
     //   options.limit(1);
        Query<T> query = query(options);
        return query.first(options);
    }

    public List<T> findMany() {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.stream().collect(Collectors.toList());
    }

    public long count () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.count();
    }

    public void deleteOne () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        query.delete(new DeleteOptions().multi(false));
    }

    public void deleteMany () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        query.delete(new DeleteOptions().multi(true));
    }



}
