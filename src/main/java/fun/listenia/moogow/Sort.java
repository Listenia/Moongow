package fun.listenia.moogow;

import dev.morphia.query.experimental.filters.Filter;
import fun.listenia.moogow.finder.GeoNearCustomFilter;
import org.bson.Document;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Sort {

    public enum Type {
        ASC_DESC,
        NEAR,
        TEXT
    }

    public Type type;
    public String field;
    public Object value;

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Sort ASCENDING (final String field) {
        Sort sort = new Sort();
        sort.type = Type.ASC_DESC;
        sort.field = field;
        sort.value = 1;
        return sort;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Sort DESCENDING (final String field) {
        Sort sort = new Sort();
        sort.type = Type.ASC_DESC;
        sort.field = field;
        sort.value = -1;
        return sort;
    }

    @NotNull
    public static Sort NEAR (final String field, double x, double y) {
        Sort sort = new Sort();
        sort.type = Type.NEAR;
        sort.field = field;
        sort.value = new Document();
        Document datas = new Document("type", "Point").append("coordinates", new double[]{x, y});
        ((Document) sort.value).put("$near", new Document("$geometry", datas));
        return sort;
    }

    @Deprecated
    public static Filter NEAR (final String field, @NotNull Consumer<GeoNearCustomFilter> geo) {
        GeoNearCustomFilter filter = new GeoNearCustomFilter(field);
        geo.accept(filter);
        return filter.buildFilter();
    }

    @NotNull
    public static Sort TEXT (final String field, final String text) {
        Sort sort = new Sort();
        sort.type = Type.TEXT;
        sort.value = new Document();
        ((Document) sort.value).put("$text", new Document("$search", text));
        return sort;
    }

}
