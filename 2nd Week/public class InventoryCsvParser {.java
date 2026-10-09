public class InventoryCsvParser {

    public static void parseInventoryRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");

        // Validate that exactly 3 fields are present
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String productName = fields[0].trim();
        String sku = fields[1].trim();
        String qty = fields[2].trim();

        System.out.println("Product: " + productName + " | SKU: " + sku + " | Qty: " + qty);
    }

    public static void main(String[] args) {
        System.out.println("=== Product Inventory CSV Parser ===");

        System.out.print("Test 1: ");
        parseInventoryRecord("Wireless Mouse,WM-2201,150");

        System.out.print("Test 2: ");
        parseInventoryRecord("Wireless Mouse,150");
    }
}