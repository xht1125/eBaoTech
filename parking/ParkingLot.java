package parking;

import exception.AllParkingLotIsFullException;
import exception.InvalidTicketException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chuangang.hu
 */
public class ParkingLot implements IParkingAndDriveable,IReport {
    //容量

    private final int volume;

    //票据

    private final Map<Ticket, Car> ticketBindingCar;

    public ParkingLot(int volume) {
        this.volume = volume;
        ticketBindingCar = new HashMap<>(volume);
    }

    /**
     * 停车
     * @param car-
     * @return ticket
     */
    @Override
    public Ticket parking(Car car) {
        if (canParkCar()){
            Ticket ticket = new Ticket();
            ticketBindingCar.put(ticket, car);
            return ticket;
        }
        throw new AllParkingLotIsFullException("停车场满了，停不到了");
    }

    /**
    *取车
     */
    @Override
    public Car pickUpTheCar(Ticket ticket) {
        if (isValidTicket(ticket)) {
            return ticketBindingCar.remove(ticket);
        }
        throw new InvalidTicketException("无效票据");
    }

    /**
     * 有效的票据，能取得车辆
     */
    public boolean isValidTicket(Ticket ticket){
        return ticketBindingCar.containsKey(ticket);
    }

    /**
     * 车位没有满，能停车
     */
    public boolean canParkCar() {
        return ticketBindingCar.size() < volume;
    }

    /**
     * 返回停车场的空车位
     */
    public int emptyParkingSpace(){
        return (volume - ticketBindingCar.size());
    }

    /**
     * 返回停车场的空置率
     */
    public double vacancyRate(){
        return (double)emptyParkingSpace() / (double)volume;
    }


    @Override
    public List<? extends IReport> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String getType() {
        return "P";
    }

    /**
     * 得到停车场的容量。
     * @return parkingLotCount
     */
    @Override
    public int calculateParkingLotCount() {
        return volume;
    }

    /**
     * 计算车辆数
     * @return carCount
     */
    @Override
    public int calculateCarCount() {
        return ticketBindingCar.size();
    }
}