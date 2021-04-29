package line.comparator;

import line.Line;

import java.util.Comparator;

/** Összehasonlít vonalakat az áruk alapján. */
public class PriceComparator implements Comparator<Line> {

    @Override
    public int compare(Line l1, Line l2){
        return Integer.compare(l1.getPrice(), l2.getPrice());
    }
}
