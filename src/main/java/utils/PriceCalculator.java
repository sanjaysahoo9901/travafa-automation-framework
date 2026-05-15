package utils;

public class PriceCalculator {

    // ✅ Calculate nights
    public static int calculateNights(int checkInDay, int checkOutDay) {
        int nights = checkOutDay - checkInDay;
        System.out.println("📅 Nights: " + nights);
        return nights;
    }

    // ✅ Fixed — handles decimals like ₹3,638.73
    public static int cleanPrice(String priceText) {
        String cleaned = priceText.replaceAll("[₹,\\s]", "").trim();
        System.out.println("🔢 Cleaned price string: " + cleaned);
        try {
            double price = Double.parseDouble(cleaned);
            int roundedPrice = (int) Math.round(price);
            System.out.println("🔢 Rounded price: " + roundedPrice);
            return roundedPrice;
        } catch (Exception e) {
            System.out.println("⚠️ Price parse error: " + e.getMessage());
            return 0;
        }
    }
}