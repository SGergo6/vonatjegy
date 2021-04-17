package line.comparator;

import line.Line;

import java.util.Comparator;

public class LineTrainCountComparator implements Comparator<Line> {

    @Override
    public int compare(Line l1, Line l2) {
        return Integer.compare(l1.getTrains().size(), l2.getTrains().size());
    }
}
