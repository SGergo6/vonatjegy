package line.comparator;

import line.Line;

import java.util.Comparator;

public class LineNameComparator implements Comparator<Line> {


    @Override
    public int compare(Line l1, Line l2) {
        return l1.getName().compareTo(l2.getName());
    }
}
