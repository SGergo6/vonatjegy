package line.vehicle;

import line.SeatOccupiedException;
import time.Time;

public class Train extends Vehicle{
    private final Wagon[] wagons;

    public Train(int wagonCount){
        super();
        wagons = new Wagon[wagonCount];
    }


    public Wagon[] getWagons() {
        return wagons;
    }
    /**
     * Lefoglalja a megadott széket a megadott kocsiban.
     * @param wagonNumber a kocsi száma
     * @param seatNumber a lefoglalandó szék
     * @throws SeatOccupiedException ha a szék már foglalt volt a lefoglalás előtt
     */
    public void reserveSeat(int wagonNumber, int seatNumber) throws SeatOccupiedException{
        wagons[wagonNumber].reserveSeat(seatNumber);
    }
    /**
     * Felszabadítja a megadott széket a megadott kocsiban.
     * @param wagonNumber a kocsi száma
     * @param seatNumber a felszabadítandó szék.
     */
    public void freeSeat(int wagonNumber, int seatNumber){
        wagons[wagonNumber].freeSeat(seatNumber);
    }

    /**
     * @return a vonat indulásának idejét az 1. állomásról
     */
    public Time getDepartureTime(){
        //A stationArrivals a vonal útvonal tömbjének sorrendjében tárolja el az időket!
        //A vonal sorrendjében halad, az induló állomás a 0. állomás
        if(!isLineReversed()) {
            return stationArrivals[0];
        } else { //Visszafelé halad, az induló állomás az utolsó állomás
            return stationArrivals[stationArrivals.length-1];
        }
    }

    /**
     * @return a vonat érkezési idejét a végállomásra
     */
    public Time getArrivalTime(){
        //A vonal sorrendjében halad, az érkezési állomás az utolsó állomás
        if(!isLineReversed()) {
            return stationArrivals[stationArrivals.length-1];
        } else { //Visszafelé halad, az érkezési állomás az 1. állomás
            return stationArrivals[0];
        }
    }

    /**
     * Megkeresi és visszaadja egy megadott állomásra történő érkezés idejét.
     * @param stationIndex az állomás sorszáma a {@code line route} tömbjében
     * @return az állomásra való érkezési időt
     */
    public Time getStationArrival(int stationIndex){
        return stationArrivals[stationIndex];
    }

}
