package station;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * Eltárol egy állomást.
 */
public class Station {
    private String name;
    /** Az állomás egyéni azonosító száma */
    private final int stationID;

    /**
     * Inicializálja az állomást.
     * @param name az állomás neve
     */
    Station(String name) {
        this.name = name;
        stationID = StationManager.nextStationID();
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getStationID() {
        return stationID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((Station)obj).name.equalsIgnoreCase(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
