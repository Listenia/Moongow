package fun.listenia.moogow.utils;

import com.mongodb.client.model.geojson.MultiPolygon;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.PolygonCoordinates;
import com.mongodb.client.model.geojson.Position;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GeoUtil {

    @NotNull
    @Contract("_ -> new")
    public static MultiPolygon fusion (@NotNull Polygon... polygons) {
        List<PolygonCoordinates> coordinates = new ArrayList<>();
        for (Polygon polygon : polygons)
            coordinates.add(polygon.getCoordinates());
        return new MultiPolygon(coordinates);
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Polygon reduce (@NotNull Polygon polygon, Polygon toRemove) {
        List<Position> positions = new ArrayList<>();
        for (Position position : polygon.getCoordinates().getExterior()) {
            if (!toRemove.getCoordinates().getExterior().contains(position))
                positions.add(position);
        }
        return new Polygon(positions);
    }

    public static boolean isInside (@NotNull Polygon polygon, @NotNull Position position) {
        return polygon.getCoordinates().getExterior().contains(position);
    }

    public static boolean isInside (@NotNull Polygon polygon, @NotNull Polygon polygon2) {
        for (Position position : polygon2.getCoordinates().getExterior())
            if (!polygon.getCoordinates().getExterior().contains(position))
                return false;
        return true;
    }

}
