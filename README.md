# Travafa Hotel Booking Automation Framework

## About
Selenium TestNG automation framework for Travafa travel 
booking platform. Covers end-to-end hotel booking flow 
with price verification.

## Technologies Used
- Java 11
- Selenium WebDriver 4.18
- TestNG 7.9
- Apache POI (Excel Data Driven Testing)
- Extent Reports
- WebDriverManager
- Page Object Model (POM)
- ThreadLocal (Parallel Execution)

## Framework Structure
src/
├── pages/
│   ├── HomePage.java
│   ├── HotelResultsPage.java
│   ├── HotelListPage.java
│   ├── GuestDetailsPage.java
│   └── CheckoutPage.java
├── tests/
│   ├── BaseTest.java
│   └── TravafaHotelTest.java
└── utils/
    ├── BasePage.java
    ├── ExcelReader.java
    ├── ExtentReportManager.java
    ├── PriceCalculator.java
    └── ScreenshotUtil.java

## Test Coverage
- Hotel search with destination selection
- Date selection using rmdp calendar
- Nationality selection using React Select
- Guest details form filling
- Price verification on checkout page
- Book Now Pay Later flow

## How to Run
mvn test

## Author
Sanjay | QA Engineer | Bengaluru
2 Years Experience | 30+ Projects Tested
