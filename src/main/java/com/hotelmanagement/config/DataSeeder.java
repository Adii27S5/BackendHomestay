package com.hotelmanagement.config;

import com.hotelmanagement.model.*;
import com.hotelmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Seeder for initializing the TourNest database with regional content.
 * Uses a robust setter-based approach to ensure compatibility across all model versions.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private AppUserRepository userRepository;
    @Autowired private AttractionRepository attractionRepository;
    @Autowired private HotelRepository hotelRepository;
    @Autowired private HomestayRepository homestayRepository;
    @Autowired private FoodRepository foodRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private SearchHistoryRepository searchHistoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (attractionRepository.count() > 0) {
            System.out.println("[DataSeeder] Persistence maintained. Database already seeded.");
            return;
        }

        System.out.println("[DataSeeder] Initializing new regional data ecosystem...");
        
        seedUsers();
        seedAttractions();
        seedHotels();
        seedHomestays();
        seedFoods();
        seedExtraData();
    }

    private void seedExtraData() {
        if (bookingRepository.count() == 0) {
            Booking b1 = new Booking(null, "The Grand Palace", "John Doe", "user@test.com", "2024-05-15", "Confirmed", "8500.0", "varanasi-boutique.png", "Varanasi", "The Grand Palace", "host@test.com", 1L, "2024-05-15", 2, "", "");
            bookingRepository.save(b1);
        }
        if (favoriteRepository.count() == 0) {
            Favorite f1 = new Favorite("user@test.com", 1L, "ATTRACTION");
            favoriteRepository.save(f1);
        }
        if (searchHistoryRepository.count() == 0) {
            SearchHistory sh1 = new SearchHistory("user@test.com", "Varanasi");
            searchHistoryRepository.save(sh1);
        }
        if (reviewRepository.count() == 0) {
            attractionRepository.findById(1L).ifPresent(a -> {
                Review r1 = new Review(a, "user@test.com", "John Doe", 5.0, "Great experience!");
                reviewRepository.save(r1);
            });
        }
    }

    private void seedUsers() {
        List<AppUser> users = new ArrayList<>();
        users.add(createU("TourNest Admin", "admin@tournest.com", "ADMIN", "Active"));
        users.add(createU("Host Aditya", "host@test.com", "HOST", "Active"));
        users.add(createU("John Doe", "user@test.com", "USER", "Active"));
        userRepository.saveAll(users);
    }

    private AppUser createU(String fullName, String email, String role, String status) {
        AppUser u = new AppUser();
        u.setFullName(fullName);
        u.setName(fullName.split(" ")[0]);
        u.setEmail(email);
        u.setPassword("password123"); // Required by schema
        u.setRole(role);
        u.setStatus(status);
        u.setJoined("April 2024");
        u.setBookingsCount(0);
        u.setLanguage("en");
        u.setCurrency("INR");
        u.setTheme("light");
        return u;
    }

    private void seedAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(createA("ganges-aarti.png", "Ganges Evening Aarti", "Varanasi", "2 hours", 5.0, "Spiritual", "A magnificent spiritual ceremony with clay lamps and incense.", "Heritage North", "Year-round"));
        attractions.add(createA("jaipur-haveli.jpg", "Amber Fort Heritage Walk", "Jaipur", "4 hours", 4.8, "Heritage", "Explore the grand architecture and hidden tunnels.", "Royal West", "Oct - March"));
        attractions.add(createA("tea-safari.png", "Tea Plantation Safari", "Munnar", "3 hours", 4.7, "Nature", "Walk through emerald tea gardens.", "Coastal South", "Sept - March"));
        attractions.add(createA("manali-snow.jpg", "Snow Valley Paragliding", "Manali", "4 hours", 4.8, "Adventure", "Soar above the snow-capped Himalayan peaks.", "Himalayan North", "April - June"));
        attractions.add(createA("ladakh-bikers.jpg", "Khardung La Pass Ride", "Ladakh", "Full Day", 4.9, "Adventure", "Highest motorable roads in the world.", "Himalayan North", "June - Sept"));
        attractions.add(createA("taj-mahal-sunrise.png", "Taj Mahal Sunrise", "Agra", "3 hours", 5.0, "Heritage", "Majestic beauty glowing at sunrise.", "Heritage North", "Oct - March"));
        attractions.add(createA("darjeeling-train.png", "Darjeeling Toy Train", "Darjeeling", "2 hours", 4.7, "Heritage", "UNESCO heritage train through misty hills.", "Himalayan East", "Oct - Dec"));
        attractions.add(createA("qutub-minar.png", "Qutub Minar Heritage", "Delhi", "3 hours", 4.8, "Heritage", "Tallest brick minaret in the world.", "Heritage North", "Oct - March"));
        attractions.add(createA("dal-lake.png", "Dal Lake Shikara Ride", "Srinagar", "2 hours", 4.9, "Nature", "Floating through the heart of valley.", "Himalayan North", "April - Oct"));
        attractions.add(createA("golden-temple.png", "Golden Temple Spiritual", "Amritsar", "3 hours", 5.0, "Spiritual", "Soul-purifying experience at the shrine.", "Heritage North", "Sept - March"));
        
        // Requested New Tours
        attractions.add(createA("bhopal-boat.png", "Bhopal Upper Lake Boating", "Bhopal", "2 hours", 4.7, "Adventure", "Speed boating and sunset views at the 'Bada Talaab'.", "Central North", "Year-round"));
        attractions.add(createA("mahabalipuram.png", "Shore Temple Ruins Walk", "Mahabalipuram", "3 hours", 4.9, "Heritage", "UNESCO site featuring 8th-century granite carvings.", "Coastal South", "Nov - Feb"));
        
        // Food Specific Tours for Discovery Filter
        attractions.add(createA("banarasi-paan.png", "Varanasi Street Food Trail", "Varanasi", "3 hours", 5.0, "Food", "Taste the legendary Paan, Malaiyyo, and Kachori Sabzi.", "Heritage North", "Oct - March"));
        attractions.add(createA("chole-bhature.png", "Old Delhi Culinary Walk", "Delhi", "4 hours", 4.9, "Food", "Explore the Narrow lanes of Chandni Chowk for iconic tastes.", "Heritage North", "Oct - March"));
        
        attractionRepository.saveAll(attractions);
    }

    private Attraction createA(String image, String title, String loc, String dur, double rat, String cat, String desc, String region, String season) {
        Attraction a = new Attraction();
        a.setImage(image);
        a.setTitle(title);
        a.setLocation(loc);
        a.setDuration(dur);
        a.setRating(rat);
        a.setCategory(cat);
        a.setDescription(desc);
        a.setRegion(region);
        a.setBestSeason(season);
        a.setApproved(true);
        return a;
    }

    private void seedHotels() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(createH("The Grand Palace", "Varanasi", 8500.0, 4.9, "varanasi-boutique.png", "Heritage North", "Year-round", 45));
        hotels.add(createH("Royal Heritage Resort", "Jaipur", 12000.0, 4.8, "jaipur-heritage.png", "Royal West", "Oct - March", 30));
        hotels.add(createH("Munnar Tea Garden Resort", "Munnar", 7500.0, 4.7, "munnar-resort.png", "Coastal South", "Sept - March", 25));
        hotels.add(createH("Himalayan View Hotel", "Manali", 6000.0, 4.8, "manali-snow.jpg", "Himalayan North", "April - June", 20));
        hotels.add(createH("The Oberoi Amarvilas", "Agra", 45000.0, 5.0, "oberoi-amarvilas.png", "Heritage North", "Oct - March", 102));
        hotels.add(createH("The Leela Palace", "Delhi", 25000.0, 4.9, "leela-palace.png", "Heritage North", "Year-round", 150));
        hotels.add(createH("Taj Lake Palace", "Udaipur", 35000.0, 5.0, "udaipur-floating-palace.png", "Royal West", "Sept - March", 65));
        hotels.add(createH("ITC Grand Chola", "Chennai", 18000.0, 4.8, "itc-grand-chola.png", "Coastal South", "Nov - Feb", 200));
        hotels.add(createH("Glenburn Tea Estate", "Darjeeling", 22000.0, 4.9, "glenburn-tea.png", "Himalayan East", "Oct - April", 12));
        hotels.add(createH("Radisson Blu Amritsar", "Amritsar", 9500.0, 4.8, "amritsar-stay.png", "Heritage North", "Sept - March", 120));
        
        hotelRepository.saveAll(hotels);
    }

    private Hotel createH(String name, String loc, double price, double rat, String img, String region, String season, int capacity) {
        Hotel h = new Hotel();
        h.setName(name);
        h.setLocation(loc);
        h.setPrice(price);
        h.setRating(rat);
        h.setImage(img);
        h.setRegion(region);
        h.setBestSeason(season);
        h.setHotelCapacityLimit(capacity);
        return h;
    }

    private void seedHomestays() {
        List<Homestay> homestays = new ArrayList<>();
        homestays.add(createStay("varanasi-ghat-stay.png", "Aroma Riverfront Haven", "Varanasi", 4.8, 3500.0, "Anandji", 4, 2, 2, "Authentic stay by the river.", "Heritage", "Year-round"));
        homestays.add(createStay("varanasi-riverside.png", "Kashi Temple Heritage Stay", "Varanasi", 4.9, 4200.0, "Shyamji", 2, 1, 1, "Stay close to the holy Kashi Vishwanath temple.", "Spiritual", "Year-round"));
        homestays.add(createStay("delhi-lodge.png", "Qutub View Homestay", "Delhi", 4.7, 3800.0, "Sanjay", 3, 1, 1, "Modern terrace view of the historic minaret.", "Heritage", "Year-round"));
        homestays.add(createStay("munnar-mist.png", "Tea Mist Cottage", "Munnar", 4.9, 5500.0, "Mariamma", 6, 3, 3, "Wake up to misty tea valley views.", "Nature", "Sept - March"));
        homestays.add(createStay("munnar-valley.png", "Munnar Valley Homestay", "Munnar", 4.7, 4800.0, "Joseph", 4, 2, 2, "Secluded villa amidst cardamom hills.", "Nature", "Year-round"));
        homestays.add(createStay("shimla-snow.png", "Snow Peaks Manor", "Manali", 4.8, 6500.0, "Meena", 5, 2, 2, "Panoramic views of Himalayan peaks.", "Mountain", "Dec - Feb"));
        homestays.add(createStay("udaipur-lake.png", "Lakefront Palace Stay", "Udaipur", 4.9, 9500.0, "Karan Singh", 4, 2, 2, "Royal experience overlooking Lake Pichola.", "Heritage", "Sept - March"));
        homestays.add(createStay("amritsar-stay.png", "Golden Temple Heritage Villa", "Amritsar", 5.0, 4200.0, "Sodhi Ji", 4, 2, 2, "Steps away from the holy shrine.", "Heritage", "Sept - March"));
        homestays.add(createStay("dal-lake.png", "Srinagar Royal Houseboat", "Srinagar", 4.9, 8500.0, "Bhat", 6, 3, 2, "Luxury carved wood stay on the water.", "Nature", "April - Oct"));
        
        homestayRepository.saveAll(homestays);
    }

    private Homestay createStay(String img, String title, String loc, double rat, double price, String host, int guests, int beds, int baths, String desc, String category, String season) {
        Homestay s = new Homestay();
        s.setImage(img);
        s.setTitle(title);
        s.setLocation(loc);
        s.setRating(rat);
        s.setPrice(price);
        s.setHost(host);
        s.setGuests(guests);
        s.setBedrooms(beds);
        s.setBathrooms(baths);
        s.setAmenities(Arrays.asList("WiFi", "Local Meals", "Mountain View"));
        s.setCategory(category);
        s.setMaxCapacity(guests + 2);
        s.setDescription(desc);
        s.setRegion(category);
        s.setBestSeason(season);
        s.setApproved(true);
        return s;
    }

    private void seedFoods() {
        List<Food> foods = new ArrayList<>();
        foods.add(createFood("Banarasi Paan", "Varanasi", "Legendary betel leaf.", "banarasi-paan.png", 50.0, 5.0, "Heritage North"));
        foods.add(createFood("Kachori Sabzi", "Varanasi", "Classic Banaras breakfast.", "varanasi_chat.png", 80.0, 4.9, "Heritage North"));
        foods.add(createFood("Malaiyyo", "Varanasi", "Winter special milk froth dessert.", "malaiyyo-dessert.png", 60.0, 5.0, "Heritage North"));
        
        foods.add(createFood("Dal Baati Churma", "Jaipur", "Rajasthani classic.", "dal-baati-churma.png", 350.0, 4.8, "Royal West"));
        foods.add(createFood("Appam with Stew", "Munnar", "Kerala breakfast.", "appam-stew.png", 250.0, 4.9, "Coastal South"));
        
        foods.add(createFood("Kashmiri Rogan Josh", "Manali", "Slow-cooked lamb.", "kashmiri-rogan-josh.png", 650.0, 4.9, "Himalayan North"));
        foods.add(createFood("Agra Petha", "Agra", "Famous pumpkin sweet.", "agra-petha.png", 150.0, 4.9, "Heritage North"));
        foods.add(createFood("Bedai with Jalebi", "Agra", "Spicy puri and sweet jalebi.", "bedai-jalebi.png", 120.0, 4.8, "Heritage North"));
        
        foods.add(createFood("Chole Bhature", "Delhi", "Iconic Delhi street food.", "chole-bhature.png", 180.0, 4.9, "Heritage North"));
        foods.add(createFood("Nihari", "Delhi", "Slow-cooked beef stew.", "nihari.png", 350.0, 4.8, "Heritage North"));
        foods.add(createFood("Paranthe Wali Gali Breakfast", "Delhi", "Crispy deep-fried parathas.", "delhi-haveli.png", 150.0, 4.7, "Heritage North"));
        
        foods.add(createFood("Darjeeling Momos", "Darjeeling", "Himalayan steamed dumplings.", "darjeeling-momos.png", 120.0, 4.9, "Himalayan East"));
        foods.add(createFood("Amritsari Kulcha", "Amritsar", "Crispy bread with chole.", "amritsar-kulcha.png", 150.0, 5.0, "Heritage North"));
        foods.add(createFood("Bhopali Poha Jalebi", "Bhopal", "Iconic MP breakfast.", "poha-jalebi.png", 60.0, 4.9, "Central North"));
        
        foodRepository.saveAll(foods);
    }

    private Food createFood(String title, String loc, String desc, String img, double price, double rat, String region) {
        Food f = new Food();
        f.setTitle(title);
        f.setLocation(loc);
        f.setDescription(desc);
        f.setImage(img);
        f.setPrice(price);
        f.setRating(rat);
        f.setRegion(region);
        f.setCategory("Traditional");
        return f;
    }
}
