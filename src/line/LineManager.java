package line;

import station.Station;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class LineManager {
    private static HashSet<Line> lines;
    private static int nextID;

    public static void initialize(){
        lines = new HashSet<>();
        nextID = 0;
    }

    public static int nextID(){
        return nextID++;
    }

    /**
     * Hozzáad egy új vonalat.
     * @param route A vonal útvonala
     * @param name A vonal neve, ez kerül ki a jármű táblájára
     * @param price A vonal megállónkénti ára
     * @return {@code false}, ha nem sikerült hozzáadni a vonalat, mert már létezik.<br>
     * {@code true}, ha sikerült hozzáadni.
     */
    public static boolean addLine(Station[] route, String name, int price){
        Line line = new Line(route, name, price);
        return lines.add(line);
    }

    /**
     * Hozzáad egy új vonalat.
     * @return {@code false}, ha nem sikerült hozzáadni a vonalat, mert már létezik.<br>
     * {@code true}, ha sikerült hozzáadni.
     */
    public static boolean addLine(Line line){
        return lines.add(line);
    }

    /**
     * Megkeres egy vonalat a neve alapján.
     * @param name vonal neve
     * @return a megtalált vonal, vagy {@code null}, ha nincs találat
     */
    public static Line searchLine(String name){
        for (Line line : lines) {
            if (line.getName().equalsIgnoreCase(name)) {
                return line;
            }
        }

        return null;
    }

    /**
     * Vonalakat keres egy általa érintett állomás alapján
     * @param searchStation az érintett állomás
     * @return talált állomások
     */
    public static Line[] searchLines(Station searchStation){
        ArrayList<Line> found = new ArrayList<>();
        for (Line line : lines) {
            for (Station station : line.getRoute()){
                if(station.equals(searchStation)){
                    found.add(line);
                    break;
                }
            }
        }

        Line[] foundArray = new Line[found.size()];
        return found.toArray(foundArray);

    }

    public static HashSet<Line> getLines() {
        return new HashSet<>(lines);
    }

    public static void setLines(HashSet<Line> lines) {
        LineManager.lines = lines;
    }
}
