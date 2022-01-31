# Booking


## Table of Contents
* [Description](https://github.com/OSS-Java-Seminar/Booking#description)
* [Database model](https://github.com/OSS-Java-Seminar/Booking#database-model)
* [Technologies](https://github.com/OSS-Java-Seminar/Booking#technologies)
* [Team members](https://github.com/OSS-Java-Seminar/Booking#team-members)


### Description:
#### User management
* 2 roles: renter and user
* User login and registration

#### Posting apartments
* Renter must be logged in before he can place his apartment for booking
* Apartment needs to have a name, location, description, price, image, size, number of bedrooms and beds (max. number of people)
* Apartment can have additional information (pets allowed, wifi, kitchen, seaview  etc.)
* Renter can update his apartment's info
* In case renter changes price, it will only affect future bookings
		
#### Browsing Apartments
* Users must be logged in before they can search and overview apartments
* Must choose check-in and check-out dates and destination before search
* The app will only show apartments that are available for all the nights
* Enable filtered search (by price range, number of guests, additional information)
* Enable sorting (by price, rating)

#### Booking
* User must be logged in before he can book apartments
* His booking will be shown on his profile, where he will be able to see renter's contact info and cancellation option (at least 2 days before check-in date)
* He will have overview of all his reservations, future and past, with sorting feature
	
#### Booking management
* Renter can see all his bookings and users' contacts
* Enable sorted view (by apartment (since renter can have multiple apartments), by date range, etc.)
* Renter can see statistic data (number of reservations, earnings, etc.)
* Renter can also cancel reservation (in case his guest doesn't show up so he can open his apartment for new reservations)

#### Rating
* For 3 months after user's stay, his reservation will be shown on his profile (Past reservations), where he will be able to add his review
* He will rate multiple things (comfort, cleanness, etc.), the average grade will be counted into apartment's rating (required)
* He can write a review (optional)

### Database model
 ![database model](https://github.com/OSS-Java-Seminar/Booking/blob/main/Database%20model/database%20model.png?raw=true)

### Technologies:
Java, Spring Boot, Bootstrap, Thymeleaf, SQL Database

### Team members:
Kate Violić, Ivana Zeljić
