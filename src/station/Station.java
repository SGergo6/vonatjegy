package station;

import java.io.Serializable;
import java.util.Objects;

/** Állomás tárolására használható osztály. */
public class Station implements Comparable<Station>, Serializable {
    /** Állomás neve */
    private String name;

    /**
     * Létrehoz egy új állomást.
     * @param name az állomás neve
     */
    public Station(String name) {
        this.name = name;
    }

    /**
     * Átállítja az állomás nevét.
     * @param name új név
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return Állomás neve */
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

    /** Betűrend alapján összehasonlítja a 2 állomás nevét. */
    @Override
    public int compareTo(Station o) {
        return this.getName().compareTo(o.getName());
    }
}
