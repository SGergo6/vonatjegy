package line.comparator;

import line.vehicle.Vehicle;

import java.util.Comparator;

/** Összehasonlít járműveket az indulási idejük alapján. */
public class VehicleDepartureComparator implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getDepartureTime().compareTo(o2.getDepartureTime());
    }
}
