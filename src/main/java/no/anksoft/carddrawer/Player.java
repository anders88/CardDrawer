package no.anksoft.carddrawer;

public class Player {
	private final String name;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		return nullSafeEquals(name, other.name);
	}

	private <T> boolean nullSafeEquals(T a, T b) {
		return a != null ? a.equals(b) : b == null;
	}
	
	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : -1;
	}
	
	@Override
	public String toString() {
		return "Player<" + name + ">";
	}

	public static Player withName(String name) {
		return new Player(name);
	}
}
