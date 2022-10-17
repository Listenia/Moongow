package fun.listenia.moogow.finder;

import java.util.HashMap;
import java.util.Map;

public class CustomSort {

    private Map<String, Object> sorts = new HashMap<>();
    private int limit = 0;
    private int skip = 0;

    public CustomSort asc (String field) {
        sorts.put(field, true);
        return this;
    }

    public CustomSort desc (String field) {
        sorts.put(field, false);
        return this;
    }

    public CustomSort text (String field, String value) {
        sorts.put(field, value);
        return this;
    }

    public Map<String, Object> getSorts () {
        return sorts;
    }

    public int getLimit () {
        return limit;
    }

    public int getSkip () {
        return skip;
    }

}
