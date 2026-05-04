package fr.neri.brumelame.service;

import fr.neri.brumelame.dao.EquipmentDAO;
import fr.neri.brumelame.dao.HeroClassesDAO;
import fr.neri.brumelame.dao.HeroDAO;
import fr.neri.brumelame.domain.character.Hero;
import fr.neri.brumelame.domain.character.HeroClasse;
import fr.neri.brumelame.domain.character.Warrior;
import fr.neri.brumelame.domain.character.Wizard;
import fr.neri.brumelame.domain.equipment.OffensiveEquipment;
import fr.neri.brumelame.exception.HeroInitializationException;

public class HeroCreationService {

    private final EquipmentDAO equipmentDAO;
    private final HeroClassesDAO heroClassesDAO;
    private final HeroDAO heroDAO;

    public HeroCreationService(
            EquipmentDAO equipmentDAO,
            HeroClassesDAO heroClassesDAO,
            HeroDAO heroDAO
    ) {
        this.equipmentDAO = equipmentDAO;
        this.heroClassesDAO = heroClassesDAO;
        this.heroDAO = heroDAO;
    }

    public Hero createHero(String type, String name, int startingCellId) throws HeroInitializationException {
        String normalizedType = normalizeHeroType(type);

        HeroClasse heroClasse = loadHeroClasse(normalizedType);
        OffensiveEquipment equipment = loadStartingEquipment(normalizedType);
        Hero hero = instantiateHero(normalizedType);

        hero.setName(name);
        hero.setHealth(heroClasse.getHealth());
        hero.setMaxHealth(heroClasse.getHealth());
        hero.setAttack(heroClasse.getAttack());
        hero.setOffEquip(equipment);
        hero.setCellId(startingCellId);

        saveHero(hero);
        return hero;
    }

    private String normalizeHeroType(String type) throws HeroInitializationException {
        String normalizedType = type == null ? "" : type.trim().toLowerCase();
        if (normalizedType.isBlank()) {
            throw new HeroInitializationException("Le type de héros est obligatoire");
        }
        return normalizedType;
    }

    private HeroClasse loadHeroClasse(String normalizedType) throws HeroInitializationException {
        HeroClasse heroClasse = heroClassesDAO.findByName(normalizedType);
        if (heroClasse == null) {
            throw new HeroInitializationException("Classe de héros introuvable : " + normalizedType);
        }
        return heroClasse;
    }

    private OffensiveEquipment loadStartingEquipment(String normalizedType) throws HeroInitializationException {
        OffensiveEquipment equipment = (OffensiveEquipment) equipmentDAO.findByHeroClassAndLevelAndType(
                normalizedType, 0, "ATTACK"
        );

        if (equipment == null) {
            throw new HeroInitializationException(
                    "L'équipement initial pour la classe : " + normalizedType + " n'a pas été trouvé."
            );
        }
        return equipment;
    }

    private Hero instantiateHero(String normalizedType) throws HeroInitializationException {
        return switch (normalizedType) {
            case "wizard" -> new Wizard();
            case "warrior" -> new Warrior();
            default -> throw new HeroInitializationException("Type de héros invalide : " + normalizedType);
        };
    }

    private void saveHero(Hero hero) throws HeroInitializationException {
        try {
            hero.setId(heroDAO.create(hero));
        } catch (Exception e) {
            throw new HeroInitializationException("Impossible de sauvegarder le héros.", e);
        }
    }
}
