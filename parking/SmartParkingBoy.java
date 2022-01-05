package parking;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author chuangang.hu
 */
public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ParkingLot... parkingLot) {
        super(parkingLot);
    }

    /**
     * 查找空闲位最多的停车场停车场
     * @return parkingLot
     */
    @Override
    public Optional<IParkingAndDriveable> searchParkingLot(){
        return Optional.ofNullable(parkingLotList.stream()
                .filter(ParkingLot::canParkCar)
                .max(Comparator.comparingInt(ParkingLot::emptyParkingSpace))
                .orElse(null));
    }
}