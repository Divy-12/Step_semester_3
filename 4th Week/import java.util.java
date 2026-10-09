import java.util.Arrays;

public class BusRoute implements Comparable<BusRoute> {
    private final String routeCode;
    private final String routeName;
    private final int priority;

    // Master Constructor using 'this' to resolve parameter naming clashes
    public BusRoute(String routeCode, String routeName, int priority) {
        if (routeCode == null || routeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Route code cannot be empty.");
        }
        if (routeName == null || routeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Route name cannot be empty.");
        }
        this.routeCode = routeCode.trim();
        this.routeName = routeName.trim();
        this.priority = priority;
    }

    // Constructor Chaining with default priority = 5 (lowest standard priority)
    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 5);
    }

    public String getRouteCode() { return routeCode; }
    public String getRouteName() { return routeName; }
    public int getPriority() { return priority; }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) return -1;

        // 1. Primary Sort: Higher priority number ranks first
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }

        // 2. Secondary Sort: Case-insensitive route code comparison
        int codeCompare = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCompare != 0) {
            return codeCompare;
        }

        // 3. Tertiary Sort: Route name comparison
        return this.routeName.compareToIgnoreCase(other.routeName);
    }

    // Stable insertion sort to guarantee deterministic behavior
    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null || routes.length <= 1) {
            return routes;
        }

        BusRoute[] sorted = Arrays.copyOf(routes, routes.length);
        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3: Bus Route Ranking Engine ===");

        BusRoute[] inputRoutes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service") // Uses 2-arg constructor, priority = 5
        };

        BusRoute[] ranked = rankRoutes(inputRoutes);
        String[] codes = new String[ranked.length];
        for (int i = 0; i < ranked.length; i++) {
            codes[i] = ranked[i].getRouteCode();
        }

        System.out.println("Ranked Route Codes: " + Arrays.toString(codes));
    }
}