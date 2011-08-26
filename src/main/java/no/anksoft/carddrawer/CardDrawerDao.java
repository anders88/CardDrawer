package no.anksoft.carddrawer;

public interface CardDrawerDao {

	void login(Player olayers);

	PlayerStatus getStatus(Player player);

	int drawCard(Player player);

}
