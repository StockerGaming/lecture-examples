package de.unistuttgart.iste.pe2.Assignment;

public class SumAndAverage {
    private Integer sum;
    private Float average;

    public SumAndAverage(Integer sum, Float average) {
        this.sum = sum;
        this.average = average;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "Sum: " + sum + ", Average: " + average;
    }
}
