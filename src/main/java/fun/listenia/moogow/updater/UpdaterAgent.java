package fun.listenia.moogow.updater;

import com.mongodb.client.result.UpdateResult;
import dev.morphia.UpdateOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.updates.UpdateOperator;
import fun.listenia.moogow.Moongow;
import fun.listenia.moogow.finder.CustomFilter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdaterAgent <T> {

    private final Moongow moongow;
    private final Class<T> clazz;

    private List<Filter> filters = new ArrayList<>();
    private List<UpdateOperator> customUpdates = new ArrayList<>();

    public UpdaterAgent (Moongow moongow, Class<T> clazz) {
        this.moongow = moongow;
        this.clazz = clazz;
    }

    public UpdaterAgent<T> setFilter (@NotNull CustomFilter filters) {
        this.filters = filters.getFilters();
        return this;
    }

    public UpdaterAgent<T> setUpdate (@NotNull CustomUpdate customUpdates) {
        this.customUpdates = customUpdates.getUpdates();
        return this;
    }

    public UpdateResult update (UpdateOptions options) {
        Query<T> query = moongow.getDatastore().find(clazz);
        for (Filter filter : filters)
            query.filter(filter);
        return query.update(customUpdates).execute(options);
    }



}
