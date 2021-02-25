package circle;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CircleUtils {

    /**
     * Calculate the area of a circle with the specified radius.
     *
     * @param radius the radius of the circle
     * @return the calculated area
     */
    public static double calculateArea(double radius) {
        return Math.PI * radius * radius;
    }

    /**
     * Get a radius from the user.
     *
     * @return the radius
     */
    public static double getRadiusFromUser() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.print("Enter a radius: ");
                    return Double.parseDouble(sc.nextLine());
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.println("Error reading input.");
                } catch (NullPointerException | NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

}
