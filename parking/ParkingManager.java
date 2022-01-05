package parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author chuangang.hu
 */
public class ParkingManager extends ParkingBoy {

    /**
     * 停车小弟集合
     */
    private final List<ParkingBoy> parkingBoyList;

    public ParkingManager(List<ParkingBoy> parkingBoyList, ParkingLot... parkingLot) {
        super(parkingLot);
        Objects.requireNonNull(parkingBoyList);
        this.parkingBoyList = parkingBoyList;
    }

    /**
     * 找停车场，找小弟停车场，找自己的停车场。返回一个停车场
     */
    @Override
    public Optional<IParkingAndDriveable> searchParkingLot() {
        Optional<IParkingAndDriveable> boy = Optional.ofNullable(parkingBoyList.stream().
                filter(parkingBoy -> parkingBoy.searchParkingLot().isPresent())
                .findFirst().orElse(null));
        if (boy.isPresent()){
            return boy;
        }
        return super.searchParkingLot();
    }

    /**
     * 小弟停车小弟取，小弟没有停则找经理有没有车，都没有抛异常。
     * @param ticket-
     * @return car
     */
    @Override
    public Car pickUpTheCar(Ticket ticket) {
        Optional<IParkingAndDriveable> boy = Optional.ofNullable(parkingBoyList.stream().
                filter(parkingBoy -> parkingBoy.parkingLotList.stream()
                        .anyMatch(parkingLot -> parkingLot.isValidTicket(ticket))).findFirst().orElse(null)) ;
        if (boy.isPresent()){
            return boy.get().pickUpTheCar(ticket);
        }
        return super.pickUpTheCar(ticket);
    }

    @Override
    public List<? extends IReport> getChildren() {
        List<IReport> iReports = new ArrayList<>();
        iReports.addAll(parkingLotList);
        iReports.addAll(parkingBoyList);
        return iReports;
    }

    @Override
    public String getType() {
        return "M";
    }
}
