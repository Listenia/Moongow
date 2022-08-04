package fun.listenia.moogow;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.UpdateResult;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.UpdateOptions;
import dev.morphia.query.experimental.filters.Filter;
import fun.listenia.moogow.finder.CustomFilter;
import fun.listenia.moogow.finder.FinderAgent;
import fun.listenia.moogow.updater.CustomUpdate;
import fun.listenia.moogow.updater.UpdaterAgent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Moongow {

    private static MongoClient client;

    private final String database;
    private final Datastore datastore;

    public static void start (final String uri) {
        if (client != null)
            client.close();
        client = MongoClients.create(uri);
    }

    public static boolean isInitialized () {
        return client != null;
    }

    public static void stop () {
        if (client != null)
            client.close();
    }

    public Moongow (final String database) {
        if (client == null)
            throw new IllegalStateException("Moongow is not initialized");

        this.database = database;
        this.datastore = Morphia.createDatastore(client, database);
    }

    public String getDatabase () {
        return database;
    }

    public MongoClient getClient () {
        return client;
    }

    public Datastore getDatastore () {
        return datastore;
    }

    public void mapping (@NotNull final Class<?>... classes) {
        this.datastore.getMapper().map(classes);
        this.datastore.ensureIndexes();
    }

    public void mapping (@NotNull final String... packages) {
        for (final String packageName : packages)
            this.datastore.getMapper().mapPackage(packageName);
        this.datastore.ensureIndexes();
    }

    public void save (Object object) {
        this.datastore.save(object);
    }

    public void save (@NotNull final List<Object> objects) {
        this.datastore.save(objects);
    }

    public void save (@NotNull final Object... objects) {
        this.datastore.save(Arrays.asList(objects));
    }

    public void delete (Object object) {
        this.datastore.delete(object);
    }

    public void delete (@NotNull final Iterable<Object> objects) {
        this.datastore.delete(objects);
    }

    public void delete (@NotNull final Object... objects) {
        this.datastore.delete(Arrays.asList(objects));
    }

    public <T> FinderAgent<T> finder (final Class<T> type) {
        return new FinderAgent<>(this, type);
    }

    public <T> FinderAgent<T> finder (final Class<T> type, @NotNull Consumer<CustomFilter> query) {
        CustomFilter filters = new CustomFilter();
        query.accept(filters);

        FinderAgent<T> newFinderAgent = new FinderAgent<>(this, type);
        newFinderAgent.filter(filters.getFilters().toArray(new Filter[0]));
        return newFinderAgent;
    }

    private <T> UpdateResult update0 (Class<T> clazz, @NotNull BiConsumer<CustomFilter, CustomUpdate> consumer, UpdateOptions options) {
        CustomFilter filters = new CustomFilter();
        CustomUpdate customUpdates = new CustomUpdate();
        consumer.accept(filters, customUpdates);

        UpdaterAgent<T> updaterAgent = new UpdaterAgent<>(this, clazz);
        updaterAgent.setFilter(filters);
        updaterAgent.setUpdate(customUpdates);
        return updaterAgent.update(options);
    }

    public <T> UpdateResult updateOne (Class<T> clazz, @NotNull BiConsumer<CustomFilter, CustomUpdate> consumer) {
        return update0(clazz, consumer, new UpdateOptions().multi(false));
    }

    public <T> UpdateResult updateMany (Class<T> clazz, @NotNull BiConsumer<CustomFilter, CustomUpdate> consumer) {
        return update0(clazz, consumer, new UpdateOptions().multi(true));
    }


}
