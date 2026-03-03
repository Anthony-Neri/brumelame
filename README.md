# Brumelame

Un jeu de rôle en ligne de commande développé en Java.

## Structure du projet

```
src/
├── fr/neri/brumelame/
│   ├── app/
│   │   └── Main.java
│   ├── domain/
│   │   ├── Character.java
│   │   ├── DefensiveEquipement.java
│   │   ├── OffensiveEquipement.java
│   │   ├── Potion.java
│   │   ├── Shield.java
│   │   ├── Spell.java
│   │   ├── Warrior.java
│   │   ├── Weapon.java
│   │   └── Wizard.java
│   ├── game/
│   │   ├── Dice.java
│   │   └── Game.java
│   └── ui/
│       └── Menu.java
```

## Diagramme de classes

```mermaid
classDiagram

    class Main {
        +main()$ void
    }

    class Game {
        -FINAL_CELL: int$
        -menu: Menu
        -character: Character
        -dice: Dice
        +Game()
        +home() void
        +startGame() void
        +mainMenu() void 
        +characterMenu() void
        +createCharacter(String type, String name) Character
        +play() void
        -moveCharacter() void
    }

    class Character {
        <<abstract>>
        -type: String
        -name: String
        -health: int
        -attack: int
        -equipement: OffensiveEquipement
        -cell: int
        +Character(String type, String name, int health, int attack, OffensiveEquipement equipement)
        +getName() String
        +getHealth() int
        +getAttack() int
        +getEquipement() OffensiveEquipement
        +getCell() int
        +setName(String name) void
        +setHealth(int health) void
        +setAttack(int attack) void
        +setEquipement(OffensiveEquipement equipement) void
        +setCell(int cell) void
        +toString() String
    }

    class Warrior {
        +Warrior(String name)
    }

    class Wizard {
        +Wizard(String name)
    }

    class OffensiveEquipement {
        <<abstract>>
        -type: String
        -name: String
        -bonusAttack: int
        +OffensiveEquipement(String name, String type, int attack)
        +getName() String
        +getType() String
        +getBonusAttack() int
        +toString() String
    }

    class Weapon {
        +Weapon(String name, String type, int bonusAttack)
    }

    class Spell {
        +Spell(String name, String type, int bonusAttack)
    }

    class DefensiveEquipement {
        <<abstract>>
        -type: String
        -name: String
        -bonus: int
        +DefensiveEquipement(String name, String type, int defense)
        +getName() String
        +getType() String
        +getBonus() int
        +toString() String
    }

    class Shield {
        +Shield(String name, int bonus)
    }

    class Potion {
        +Potion(String name, int bonus)
    }

    class Menu {
        -input: Scanner
        +Menu()
        +askMainStartChoice() int
        +askMainChoice() int
        +askCharacterType() int
        +askCharacterMenuChoice() int
        +askNameCharacter() String
        +askAction(int characterPosition) int
        +printCharacter(Character character) void
        +end() void
        +quit() void
        -askInt(String message, int minValue, int maxValue) int
        -askString(String message) String
    }

    class Dice {
        -minValue: int
        -maxValue: int
        +Dice(int minValue, int maxValue)
        +roll() int
    }

    Main ..> Game : uses
    Game --> Menu : has
    Game --> Character : has
    Game --> Dice : has
    Character <|-- Warrior : extends
    Character <|-- Wizard : extends
    Character --> OffensiveEquipement : has
    OffensiveEquipement <|-- Weapon : extends
    OffensiveEquipement <|-- Spell : extends
    DefensiveEquipement <|-- Shield : extends
    DefensiveEquipement <|-- Potion : extends
    Warrior ..> Weapon : creates
    Wizard ..> Spell : creates
```

## Comment jouer

1. Compiler le projet
2. Exécuter [`Main.java`](src/fr/neri/brumelame/app/Main.java)
3. Suivre les instructions à l'écran :
   - Créer un personnage (Sorcier ou Guerrier)
   - Nommer votre personnage
   - Commencer le jeu et avancer sur le plateau (64 cases)

## Classes principales

- [`Game`](src/fr/neri/brumelame/game/Game.java) : Gère la logique du jeu
- [`Character`](src/fr/neri/brumelame/domain/Character.java) : Classe abstraite pour les personnages
- [`Warrior`](src/fr/neri/brumelame/domain/Warrior.java) : Classe guerrier (10 PV, 5 ATK)
- [`Wizard`](src/fr/neri/brumelame/domain/Wizard.java) : Classe sorcier (6 PV, 8 ATK)
- [`Menu`](src/fr/neri/brumelame/ui/Menu.java) : Gère l'interface utilisateur
- [`Dice`](src/fr/neri/brumelame/game/Dice.java) : Simule un dé pour le déplacement
