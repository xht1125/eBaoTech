package parking;

/**
 * @author chuangang.hu
 */

public interface IParkingAndDriveable {

    /**
     * 停车
     * @param car-
     * @return ticket
     */

    Ticket parking(Car car);

    /**
     * 取车
     * @param ticket -
     * @return car
     */

    Car pickUpTheCar(Ticket ticket);
}
