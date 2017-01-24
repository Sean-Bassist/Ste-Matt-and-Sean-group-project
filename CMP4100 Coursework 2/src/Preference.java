
public class Preference {

	private String roomType;
	private float maxPrice;
	private boolean roomHasBalcony;
	private boolean roomHasLounge;

	public Preference(String roomType, float maxPrice, boolean roomHasBalcony, boolean roomHasLounge) {
		this.roomType = roomType;
		this.maxPrice = maxPrice;
		this.roomHasBalcony = roomHasBalcony;
		this.roomHasLounge = roomHasLounge;
	}

	public boolean compareUserAndRoom(Room room) {
		boolean meetRequirements = false;
		if (room.getRoomType().equalsIgnoreCase(roomType) && room.getRoomPrice() < maxPrice
				&& room.getRoomHasBalcony() == roomHasBalcony && room.getRoomHasLounge() == roomHasLounge) {
			meetRequirements = true;
		}
		return meetRequirements;
	}

}