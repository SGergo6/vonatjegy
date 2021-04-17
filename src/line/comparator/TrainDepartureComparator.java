package line.comparator;

import line.vehicle.Train;

import java.util.Comparator;

public class TrainDepartureComparator implements Comparator<Train> {

    @Override
    public int compare(Train o1, Train o2) {
        return o1.getDepartureTime().compareTo(o2.getDepartureTime());
    }
}
