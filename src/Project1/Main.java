package Project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CarRentalSystem {
	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();

	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {

		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));
		} else {
			System.out.println("Car is not available for Rent.");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
		} else {
			System.out.println("Car was not Rented");
		}
	}

	public void menu() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("===== Car Rental System =====");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); 

			if (choice == 1) {
				System.out.println("\n== Rent a Car ==\n");
				System.out.print("Enter your name: ");
				String customerName = scanner.nextLine();

				System.out.println("\nAvailable Cars:");
				for (Car car : cars) {
					if (car.isAvailable()) {
						System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
					}
				}

				System.out.print("\nEnter the car ID you want to rent: ");
				String carId = scanner.nextLine();

				System.out.print("Enter the number of days for rental: ");
				int rentalDays = scanner.nextInt();
				scanner.nextLine(); 

				Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
					System.out.println("Rental Days: " + rentalDays);
					System.out.printf("Total Price: $%.2f%n", totalPrice);

					System.out.print("\nConfirm rental (Y/N): ");
					String confirm = scanner.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, rentalDays);
						System.out.println("\nCar rented successfully.");
					} else {
						System.out.println("\nRental canceled.");
					}
				} else {
					System.out.println("\nInvalid car selection or car not available for rent.");
				}
			} else if (choice == 2) {
				System.out.println("\n== Return a Car ==\n");
				System.out.print("Enter the car ID you want to return: ");
				String carId = scanner.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}

				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCustomer();
							break;
						}
					}

					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Car returned successfully by " + customer.getName());
					} else {
						System.out.println("Car was not rented or rental information is missing.");
					}
				} else {
					System.out.println("Invalid car ID or car is not rented.");
				}
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}

		System.out.println("\nThank you for using the Car Rental System!");
	}

}

public class Main {

	public static void main(String[] args) {
		
		CarRentalSystem rentalSystem = new CarRentalSystem();
		
		Car c1 = new Car("C001", "Mahindra", "Thar", 100.0);
		Car c2 = new Car("C002", "Maruti", "Dzire", 30.0);
		Car c3 = new Car("C003", "Toyota", "Fortuner", 160.0);
		Car c4 = new Car("C004", "Toyota", "Etios", 40.0);
		Car c5 = new Car("C005", "Mahindra", "Scorpio", 90.0);
		Car c6 = new Car("C006", "Suzuki", "Waganor", 50.0);
		Car c7 = new Car("C007", "Toyota", "Innova", 140.0);
		Car c8 = new Car("C008", "Tata", "Indigo", 30.0);
		
		rentalSystem.addCar(c1);
		rentalSystem.addCar(c2);
		rentalSystem.addCar(c3);
		rentalSystem.addCar(c4);
		rentalSystem.addCar(c5);
		rentalSystem.addCar(c6);
		rentalSystem.addCar(c7);
		rentalSystem.addCar(c8);
		
		rentalSystem.menu();
	}

}
