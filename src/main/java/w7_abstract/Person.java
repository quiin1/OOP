package w7_abstract;

import java.util.ArrayList;
import java.util.List;

public class Person {
    String name;
    String address;
    List<Vehicle> vehicleList = new ArrayList<>();

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    /**
     * remove vehicle.
     * @param registrationNumber .
     */
    public void removeVehicle(String registrationNumber) {
        for (int i = 0; i < vehicleList.size(); i++) {
            if (vehicleList.get(i).getRegistrationNumber().equals(registrationNumber)) {
                vehicleList.remove(i);
            }
        }
    }

    /**
     * get vehicles info.
     * @return .
     */
    public String getVehiclesInfo() {
        if (vehicleList.size() == 0) {
            return getName() + " has no vehicle!";
        }
        StringBuilder result = new StringBuilder(getName() + " has:\n\n");
        for (Vehicle vehicle : vehicleList) {
            result.append(vehicle.getInfo());
        }
        return result.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
