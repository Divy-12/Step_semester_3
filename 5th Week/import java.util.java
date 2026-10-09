import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientVitals {
    private final List<Double> readings;

    // Constructor seeds history by delegating to recordReading to ensure zero logic duplication
    public PatientVitals(double[] initialReadings) {
        this.readings = new ArrayList<>();
        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    // Records a single reading; rejects values <= 0 or > 45°C
    public void recordReading(double reading) {
        if (reading > 0.0 && reading <= 45.0) {
            readings.add(reading);
        }
    }

    // Computes average of stored valid readings
    public double getAverage() {
        if (readings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double val : readings) {
            sum += val;
        }
        return sum / readings.size();
    }

    // Returns a defensive copy to prevent external mutation
    public double[] getAllReadings() {
        double[] copy = new double[readings.size()];
        for (int i = 0; i < readings.size(); i++) {
            copy[i] = readings.get(i);
        }
        return copy;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3 Tests ===");
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println("Readings after filtering: " + Arrays.toString(v.getAllReadings()));

        double[] copy = v.getAllReadings();
        copy[0] = 999.0; // Mutate array copy
        System.out.println("Defensive copy test (original unchanged): " + Arrays.toString(v.getAllReadings()));
    }
}