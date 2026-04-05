package com.solvd.delivery.model.abstractClasses;

public abstract class Employee extends Person {
    private boolean occupied;
    private double salaryPerHour;

    public Employee(String name, String phoneNumber, boolean occupied, double salaryPerHour) {
        super(name, phoneNumber);
        this.occupied = occupied;
        this.salaryPerHour = salaryPerHour;
    }

    public Employee(String name, String phoneNumber, double salaryPerHour) {
        this(name, phoneNumber, false, salaryPerHour);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public abstract double getSalary();
}
