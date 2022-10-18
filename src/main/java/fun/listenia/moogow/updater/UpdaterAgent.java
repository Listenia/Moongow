package fun.listenia.moogow.updater;

import com.mongodb.client.result.UpdateResult;
import dev.morphia.UpdateOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filter;
import fun.listenia.moogow.Moongow;
import fun.listenia.moogow.finder.CustomFilter;

public class UpdaterAgent <T> {

    private final Moongow moongow;
    private final Class<T> clazz;
    private final CustomFilter filters;
    private final CustomUpdate updates;

    public UpdaterAgent (Moongow moongow, Class<T> clazz) {
        this.moongow = moongow;
        this.clazz = clazz;

        this.filters = new CustomFilter();
        this.updates = new CustomUpdate();
    }

    public CustomFilter getFilters () {
        return filters;
    }

    public CustomUpdate getUpdates () {
        return updates;
    }


    public UpdateResult update (UpdateOptions options) {
        Query<T> query = moongow.getDatastore().find(clazz);
        for (Filter filter : filters.getFilters())
            query.filter(filter);
        return query.update(updates.getUpdates()).execute(options);
    }



}
