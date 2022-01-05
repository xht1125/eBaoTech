package parking;

import exception.AllParkingLotIsFullException;
import exception.InvalidTicketException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author chuangang.hu
 */
public class ParkingBoy implements IParkingAndDriveable,IReport {

    protected List<ParkingLot> parkingLotList;

    protected StringBuffer parkingLotReport = new StringBuffer();
    public ParkingBoy(ParkingLot... parkingLot) {
        this.parkingLotList = Arrays.asList(parkingLot);
    }

    /**
     * 查找停车场
     * @return parkingLot
     */
    public Optional<IParkingAndDriveable> searchParkingLot(){
        return Optional.ofNullable(parkingLotList.stream().
                filter(ParkingLot::canParkCar)
                .findFirst().orElse(null));
    }

    /**
     * 找到相应的停车场并进行停车
     * @param car-
     * @return  Ticket
     */
    @Override
    public Ticket parking(Car car) {
         return searchParkingLot()
                 .orElseThrow(()->new AllParkingLotIsFullException("没有空余的停车场"))
                 .parking(car);
    }

    /*
    不需要按顺序取车
     */

    @Override
    public Car pickUpTheCar(Ticket ticket) {
        return parkingLotList.stream()
                .filter(parkingLot -> parkingLot.isValidTicket(ticket))
                .findFirst().orElseThrow(()->new InvalidTicketException("无效的票据"))
                .pickUpTheCar(ticket);
    }

    @Override
    public List<? extends IReport> getChildren() {
        return parkingLotList;
    }

    @Override
    public String getType() {
        return "B";
    }

    /**
     * 得到停车场的容量。
     * @return parkingLotCount
     */
    @Override
    public int calculateParkingLotCount() {
        return getChildren().stream()
                .mapToInt(IReport::calculateParkingLotCount).sum();
    }

    /**
     * 计算车辆数
     * @return carCount
     */
    @Override
    public int calculateCarCount() {
        return getChildren().stream()
                .mapToInt(IReport::calculateCarCount).sum();
    }
}
