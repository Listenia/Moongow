package fun.listenia.moogow.updater;

import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperator;
import dev.morphia.query.experimental.updates.UpdateOperators;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomUpdate {

    private final List<UpdateOperator> updates = new ArrayList<>();

    public @NotNull CustomUpdate set (@NotNull String field, @NotNull Object value) {
        updates.add(UpdateOperators.set(field, value));
        return this;
    }

    public @NotNull CustomUpdate unset (@NotNull String field) {
        updates.add(UpdateOperators.unset(field));
        return this;
    }

    public @NotNull CustomUpdate inc (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.inc(field, value));
        return this;
    }

    public @NotNull CustomUpdate decr (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.dec(field, value));
        return this;
    }

    public @NotNull CustomUpdate mul (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.mul(field, value));
        return this;
    }

    public @NotNull CustomUpdate div (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.mul(field, 1 / value.doubleValue()));
        return this;
    }

    public @NotNull CustomUpdate push (@NotNull String field, Object value) {
        updates.add(UpdateOperators.push(field, value));
        return this;
    }

    public @NotNull CustomUpdate push (@NotNull String field, Object... values) {
        updates.add(UpdateOperators.push(field, values));
        return this;
    }

    public @NotNull CustomUpdate addToSet (@NotNull String field, Object value) {
        updates.add(UpdateOperators.addToSet(field, value));
        return this;
    }

    public @NotNull CustomUpdate popLast (@NotNull String field) {
        updates.add(UpdateOperators.pop(field));
        return this;
    }

    public @NotNull CustomUpdate popFirst (@NotNull String field) {
        updates.add(UpdateOperators.pop(field).removeFirst());
        return this;
    }

    public @NotNull CustomUpdate pull (@NotNull String field, @NotNull Object value) {
        updates.add(UpdateOperators.pull(field, Filters.eq(field, value)));
        return this;
    }

    public @NotNull CustomUpdate pull (@NotNull String field, @NotNull Object... values) {
        updates.add(UpdateOperators.pull(field, Filters.in(field, Arrays.asList(values))));
        return this;
    }

    public @NotNull CustomUpdate pullAll (@NotNull String field, @NotNull Object... values) {
        updates.add(UpdateOperators.pullAll(field, Arrays.asList(values)));
        return this;
    }

    public @NotNull CustomUpdate rename (@NotNull String field, @NotNull String newField) {
        updates.add(UpdateOperators.rename(field, newField));
        return this;
    }

    public @NotNull CustomUpdate min (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.min(field, value));
        return this;
    }

    public @NotNull CustomUpdate max (@NotNull String field, @NotNull Number value) {
        updates.add(UpdateOperators.max(field, value));
        return this;
    }

    public @NotNull List<UpdateOperator> getUpdates () {
        return updates;
    }

}
