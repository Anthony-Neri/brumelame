import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.game.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    HeroDAO dao = new HeroDAO();
    Hero hero = dao.find(1);

    System.out.println(hero);
    Game game = new Game();
    game.home();

}
