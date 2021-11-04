package w7_abstract;

public class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String brand, String model, String registrationNumber, Person owner,
               int numberOfDoors) {
        super(brand, model, registrationNumber, owner);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String getInfo() {
        return "Car:"
                + "\n\tBrand: " + super.getBrand()
                + "\n\tModel: " + super.getModel()
                + "\n\tRegistration Number: " + super.getRegistrationNumber()
                + "\n\tNumber of Doors: " + this.numberOfDoors
                + "\n\tBelongs to " + super.getOwner().getName() + " - "
                + super.getOwner().getAddress() + "\n";
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
}
