package fun.listenia.moogow;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.experimental.filters.Filter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;


public class Moongow {

    private static MongoClient client;

    private final String database;
    private final Datastore datastore;

    public static void init (final String uri) {
        if (client != null)
            client.close();
        client = MongoClients.create(uri);
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

    public void delete (Object object) {
        this.datastore.delete(object);
    }

    public void delete (@NotNull final Iterable<Object> objects) {
        this.datastore.delete(objects);
    }

    public <T> Finder<T> finder (final Class<T> type) {
        return new Finder<>(this, type);
    }

    public <T> Finder<T> finder (final Class<T> type, @NotNull Consumer<CustomFilter> query) {
        CustomFilter filters = new CustomFilter();
        query.accept(filters);

        Finder<T> newFinder = new Finder<>(this, type);
        newFinder.filter(filters.getFilters().toArray(new Filter[0]));
        return newFinder;
    }



}
