package fun.listenia.moogow.finder;

import dev.morphia.DeleteOptions;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Meta;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;
import fun.listenia.moogow.Moongow;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FinderAgent<T> {

    private final Moongow moongow;
    private final Class<T> clazz;
    private final CustomFilter filters;
    private final CustomSort sorts;

    public FinderAgent (Moongow moongow, final Class<T> clazz) {
        this.moongow = moongow;
        this.clazz = clazz;
        this.filters = new CustomFilter();
        this.sorts = new CustomSort();
    }

    @NotNull
    private Query<T> query (FindOptions options) {
        Query<T> query = moongow.getDatastore().find(clazz);
        for (Filter filter : this.filters.getFilters())
            query.filter(filter);

        if (options == null)
            return query;

        if (this.sorts.getLimit() > 0) options.limit(this.sorts.getLimit());
        if (this.sorts.getSkip() > 0) options.skip(this.sorts.getSkip());

        Document sort = new Document();

        for (Map.Entry<String, Object> entry : this.sorts.getSorts().entrySet()) {
            if (entry.getValue() instanceof Boolean) {
                sort.append(entry.getKey(), ((boolean) entry.getValue()) ? 1 : -1);
            } else if (entry.getValue() instanceof String) {
                query.filter(Filters.text(entry.getValue().toString()));
                options.sort(Meta.searchScore(entry.getKey()));
            }
        }

        options.sort(sort);

        return query;
    }

    public T findOne () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.first();
    }

    public List<T> findMany() {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.stream().collect(Collectors.toList());
    }

    public Stream<T> findStream () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.stream();
    }

    public long count () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.count();
    }

    public boolean exists () {
        FindOptions options = new FindOptions();
        Query<T> query = query(options);
        return query.count() > 0;
    }

    public int deleteOne () {
        Query<T> query = query(null);
        return (int) query.delete(new DeleteOptions().multi(false)).getDeletedCount();
    }

    public long deleteMany () {
        Query<T> query = query(null);
        return query.delete(new DeleteOptions().multi(true)).getDeletedCount();
    }

    public CustomFilter getFilters () {
        return filters;
    }

    public CustomSort getSorts () {
        return sorts;
    }



}
