package w7_abstract;

public class MotorBike extends Vehicle {
    private boolean hasSidecar;

    public MotorBike(String brand, String model, String registrationNumber, Person owner,
                     boolean hasSidecar) {
        super(brand, model, registrationNumber, owner);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public String getInfo() {
        return "Motor Bike:"
                + "\n\tBrand: " + super.getBrand()
                + "\n\tModel: " + super.getModel()
                + "\n\tRegistration Number: " + super.getRegistrationNumber()
                + "\n\tHas Side Car: " + this.hasSidecar
                + "\n\tBelongs to " + super.getOwner().getName() + " - "
                + super.getOwner().getAddress() + "\n";
    }

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }
}
