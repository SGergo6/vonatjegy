package line.comparator;

import line.Line;

import java.util.Comparator;

public class LineVehicleCountComparator implements Comparator<Line> {

    @Override
    public int compare(Line l1, Line l2) {
        return Integer.compare(l1.getVehicles().size(), l2.getVehicles().size());
    }
}
