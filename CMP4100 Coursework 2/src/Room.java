public class Room {

	private String roomNumber;
	private String roomType;
	private float roomPrice;
	private boolean roomHasBalcony;
	private boolean roomHasLounge;
	private String reserveID;
	private String userPass;

	public Room(String roomNumber, String roomType, float roomPrice, boolean roomHasBalcony, boolean roomHasLounge,
			String reserveID, String userPass) {

		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.roomHasBalcony = roomHasBalcony;
		this.roomHasLounge = roomHasLounge;
		this.reserveID = reserveID;
	}

	public String printString() {
		return roomNumber + " " + roomType + " " + roomPrice + " " + roomHasBalcony + " " + roomHasLounge + " "
				+ reserveID;
	}

	@Override
	public String toString() {
		return "Room Number = " + roomNumber + "\nRoom Type = " + roomType + "\nRoom Price = " + roomPrice 
				+ "\nRoom Has A Balcony = " + roomHasBalcony + "\nRoom Has Lounge = " + roomHasLounge
				+ "\nRoom Reserved by = " + reserveID + "\n";
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public float getRoomPrice() {
		return roomPrice;
	}

	public boolean getRoomHasBalcony() {
		return roomHasBalcony;
	}

	public boolean getRoomHasLounge() {
		return roomHasLounge;
	}

	public String getReserveID() {
		return reserveID;
	}

	public void setReserveID(String reserveID) {
		this.reserveID = reserveID;
	}
	
	public void setUserPass(String userPass) {
		
	}
	
	public void getUserPass() {
		
	}

}