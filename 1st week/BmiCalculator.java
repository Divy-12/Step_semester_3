import java.util.Random;

public class BmiCalculator {

    // Returns health status based on BMI value
    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi <= 24.9) {
            return "Normal";
        } else if (bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    // Calculates BMI and prints tabular wellness report
    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("==========================================================");
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-12s%n", 
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double height = heights[i];
            double weight = weights[i];
            double bmi = weight / (height * height);
            String status = getBmiStatus(bmi);

            System.out.printf("Person %-4d | %-12.2f | %-12.2f | %-8.2f | %-12s%n", 
                    (i + 1), height, weight, bmi, status);
        }
        System.out.println("==========================================================");
    }

    public static void main(String[] args) {
        int teamSize = 10;
        double[] heights = new double[teamSize];
        double[] weights = new double[teamSize];

        Random random = new Random();

        // Generate sample height (1.50m to 1.95m) and weight (45kg to 110kg) values
        for (int i = 0; i < teamSize; i++) {
            heights[i] = 1.50 + (1.95 - 1.50) * random.nextDouble();
            weights[i] = 45.0 + (110.0 - 45.0) * random.nextDouble();
        }

        System.out.println("=== Corporate Wellness Program: Department Report ===");
        printWellnessReport(heights, weights);
    }
}