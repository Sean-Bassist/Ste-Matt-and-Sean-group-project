import java.io.*;
import java.util.*;

public class RoomReservationSystem {
	// Static variables that are used throughout the entire program
	static Scanner console = new Scanner(System.in);
	static ArrayList<Room> roomList = new ArrayList<Room>();
	static String filePath = "m:\\data\\rooms.txt";

	public static void main(String[] args) throws IOException {
		loadRooms();
		// Main menu for the program.
		String userMenuChoice;
		do {
			System.out.printf("--Room Booking System--\n\n--MAIN MENU--\n");
			System.out.printf("1 - Reserve Room\n2 - Cancel Room\n3 - View Room Reservations\nQ - Quit\nPick: ");
			userMenuChoice = console.next();
			switch (userMenuChoice) {
			// Reserve a room
			case "1":
				reserveRoom();
				printToFile();
				break;
			// Cancel Reservations
			case "2":
				cancelRoom();
				printToFile();
				break;
			// View Reservations
			case "3":
				viewRoomList();
				break;
			// Exit the program
			case "Q":
				break;
			}

		} while (!userMenuChoice.equalsIgnoreCase("Q"));
		System.out.printf("Shutting Down!");
		console.close();
		System.exit(0);
	}

	// Method read in the rooms from the file
	public static void loadRooms() throws FileNotFoundException {
		FileReader file = new FileReader(filePath);
		Scanner read = new Scanner(file);
		while (read.hasNext()) {
			try {
				String roomNumber = read.next();
				String roomType = read.next();
				float roomPrice = read.nextFloat();
				boolean roomHasBalcony = read.nextBoolean();
				boolean roomHasLounge = read.nextBoolean();
				String reserveID = read.next();
				String userPass = read.next();
				roomList.add(new Room(roomNumber, roomType, roomPrice, roomHasBalcony, roomHasLounge, reserveID, userPass));
			} catch (InputMismatchException ime) {
				System.out.printf("There was a problem with the file, please fix this and restart!");
				System.exit(0);
			}
		}
		;
		read.close();
	}

	// Method to get the users preferred room type
	public static String getUserRoomType() {
		String userRoomType;
		do {
			System.out.printf("Please enter the type of room you want\n");
			System.out.printf("Single, Double or Suite\n");
			System.out.printf("Choice: ");
			userRoomType = console.next();
		} while (!userRoomType.equalsIgnoreCase("Single") && !userRoomType.equalsIgnoreCase("double")
				&& !userRoomType.equalsIgnoreCase("suite"));
		return userRoomType;
	}

	// Method to get the users maximum price
	public static float getUserMaxPrice() {
		float userMaxPrice = 0.0f;
		do {
			try {
				System.out.printf("Please enter the max price you are willing to pay!\n");
				System.out.printf("Price: ");
				userMaxPrice = console.nextFloat();
				break;
			} catch (InputMismatchException ime) {
				System.out.println("Please enter a valid value!");
				console.next();
			}
		} while (true);
		return userMaxPrice;
	}

	// Method to ask the user if they want a balcony
	public static boolean getUserHasBalcony() {
		boolean userHasBalcony = false;
		do {
			try {
				System.out.printf("Would you like a Balcony? True or False\n");
				System.out.printf("Choice: ");
				userHasBalcony = console.nextBoolean();
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Please enter True or False\n");
				console.next();
			}
		} while (true);
		return userHasBalcony;
	}

	// Method to ask the user if they want a lounge
	public static boolean getUserHasLounge() {
		boolean userHasLounge = false;
		do {
			try {
				System.out.printf("Would you like a Lounge? True or False\n");
				System.out.printf("Choice: ");
				userHasLounge = console.nextBoolean();
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Please enter True or False\n");
				console.next();
			}
		} while (true);
		return userHasLounge;
	}

	// The method that reserves a room for the user
	public static void reserveRoom() {
		String userEmail;
		int counter = 0;
		System.out.printf("Please enter your email: ");
		userEmail = console.next();
		// Creates instance of the class "Preference" and inputs users requirements
		Preference roomsMatch = new Preference(getUserRoomType(), getUserMaxPrice(), getUserHasBalcony(),
				getUserHasLounge());
		System.out.printf("List of available rooms that match your query!\n\n");
		// Goes through the array and compares it to the user choice.
		for (int i = 0; i < roomList.size(); i++) {
			if (roomsMatch.compareUserAndRoom(roomList.get(i)) && roomList.get(i).getReserveID().equals("N/A")) {
				System.out.printf("Room ID: %d\n%s\n", i, roomList.get(i).toString());
				counter++;
			}
		}
		if (counter > 0) {
			String userChoice;
			int userRoomChoice = 0;
			do {
				do {
					try {
						System.out.printf("Please enter the room ID\n");
						System.out.printf("Room ID: ");
						userRoomChoice = console.nextInt();
						break;
					} catch (InputMismatchException ime) {
						System.out.printf("Invalid input\n");
						console.next();
					}
				} while (true);
				try{ 
					if (!roomList.get(userRoomChoice).getReserveID().equalsIgnoreCase("N/A")){
						System.out.printf("This room has already been booked!\n");
						System.out.printf("Please try again!\n");
					}
				} catch (IndexOutOfBoundsException iob) {
					System.out.printf("The input was out of bounds.");
					System.out.printf("Please restart the program!");
					System.exit(0);
					
				}
			} while (!roomList.get(userRoomChoice).getReserveID().equalsIgnoreCase("N/A"));
			userChoice = getUserVerification();
			if (userChoice.equalsIgnoreCase("Y")) {
				roomList.get(userRoomChoice).setReserveID(userEmail);
				System.out.printf("Your room has been reserved!\n");
			} else if (userChoice.equalsIgnoreCase("N")) {
				System.out.printf("Room Reservation Canceled!\n");
				System.out.printf("Restarting!\n");
			}
		} else {
			System.out.printf("There were no rooms matching your requirements!\n");
		}
	}

	// The method to cancel a room that has been reserved
	public static void cancelRoom() {
		String userID;
		String userChoice;
		int userRoomChoice = 0;
		int counter = 0;
		System.out.printf("Please enter you email!\n");
		System.out.printf("Email: ");
		userID = console.next();
		// Searches 
		for (int i = 0; i < roomList.size(); i++) {
			if (roomList.get(i).getReserveID().equalsIgnoreCase(userID)) {
				System.out.printf("Room ID: %d \n%s\n", i, roomList.get(i).toString());
				counter++;
			}
		}
		if (counter > 0) {
			do {
				do {
					try {
						System.out.printf("What room would you like to cancel?");
						System.out.printf("\nRoom ID: ");
						userRoomChoice = console.nextInt();
						break;
					} catch (InputMismatchException ime) {
						System.out.printf("Invalid input.\n");
						console.next();
					}
				} while (true);
				try {	
					if (!roomList.get(userRoomChoice).getReserveID().equalsIgnoreCase(userID)) {
						System.out.print("This was not your room. Please try again.\n");
					}
				} catch (IndexOutOfBoundsException iob) {
					System.out.printf("The input was out of bounds.");
					System.out.printf("Please restart the program!");
					System.exit(0);
				}
			} while (!roomList.get(userRoomChoice).getReserveID().equalsIgnoreCase(userID));
			userChoice = getUserVerification();
			if (userChoice.equalsIgnoreCase("Y")) {
				roomList.get(userRoomChoice).setReserveID("N/A");
				System.out.printf("Reservation Canceled\n");
			} else if (userChoice.equalsIgnoreCase("N")) {
				System.out.printf("Room not canceled!\n");
			}
		} else {
			System.out.printf("No rooms matching you Email were found.\n");
			System.out.printf("Please try again.\n");
		}
	}

	// The method that prints the reserved rooms and then the unreserved
	public static void viewRoomList() throws IOException {
		// Prints a list of rooms that are reserved
		System.out.printf("\n--Reserved Rooms--\n\n");
		for (Room room : roomList) {
			if (!room.getReserveID().equals("N/A")) {
				System.out.printf("%s \n", room.toString());
			}
		}
		// Prints a list of rooms that are not reserved
		System.out.printf("--Unreserved Rooms--\n\n");
		for (Room room1 : roomList) {
			if (room1.getReserveID().equals("N/A")) {
				System.out.printf("%s \n", room1.toString());
			}
		}
		System.out.printf("Press Enter to continue!\n");
		System.in.read();
	}

	// Method that asked the user if they are sure and only returns the answer
	public static String getUserVerification() {
		String userAnswer;
		do {
			System.out.printf("Are you sure? Y or N\n");
			System.out.printf("Choice: ");
			userAnswer = console.next();
		} while (!userAnswer.equalsIgnoreCase("Y") && !userAnswer.equalsIgnoreCase("N"));
		return userAnswer;
	}

	// Method to print the array to the file
	public static void printToFile() throws FileNotFoundException {
		PrintWriter filePrint = new PrintWriter(filePath);
		for (Room rooms : roomList) {
			filePrint.println(rooms.printString());
		}
		filePrint.close();
	}


}