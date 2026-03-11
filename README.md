# Brumelame

Un jeu de rôle en ligne de commande développé en Java.

## Structure du projet

```
src/
├── fr/neri/brumelame/
│   ├── app/
│   │   └── Main.java
│   ├── domain/
│   │   ├── hero/
│   │   │   ├── Character.java
│   │   │   ├── Warrior.java
│   │   │   └── Wizard.java
│   │   └── equipment/
│   │       ├── DefensiveEquipment.java
│   │       ├── OffensiveEquipment.java
│   │       ├── Potion.java
│   │       ├── Shield.java
│   │       ├── Spell.java
│   │       └── Weapon.java
│   ├── game/
│   │   ├── Board.java
│   │   ├── Dice.java
│   │   ├── Game.java
│   │   └── cell/
│   │       ├── Bonus.java
│   │       ├── Cell.java
│   │       ├── EmptyCell.java
│   │       ├── Enemy.java
│   │       └── WeaponCell.java
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
        -hero: Character
        -dice: Dice
        -board: Board
        +Game()
        +home() void
        +startGame() void
        +mainMenu() void 
        +characterMenu() void
        +createCharacter(String type, String name) Character
        +play() void
        -moveCharacter() void
    }

    class Board {
        -cells: Cell[]
        +Board()
        +getCell(int index) Cell
    }

    class Cell {
        <<abstract>>
        -name: String
        +Cell(String name)
        +getName() String
    }

    class EmptyCell {
        +EmptyCell()
    }

    class Bonus {
        +Bonus()
    }

    class Enemy {
        +Enemy()
    }

    class WeaponCell {
        +WeaponCell()
    }

    class Character {
        <<abstract>>
        -type: String
        -name: String
        -health: int
        -attack: int
        -equipement: OffensiveEquipment
        -cell: int
        +Character(String type, String name, int health, int attack, OffensiveEquipment equipement)
        +getName() String
        +getHealth() int
        +getAttack() int
        +getEquipement() OffensiveEquipment
        +getCell() int
        +setName(String name) void
        +setHealth(int health) void
        +setAttack(int attack) void
        +setEquipement(OffensiveEquipment equipement) void
        +setCell(int cell) void
        +toString() String
    }

    class Warrior {
        +Warrior(String name)
    }

    class Wizard {
        +Wizard(String name)
    }

    class OffensiveEquipment {
        <<abstract>>
        -type: String
        -name: String
        -bonusAttack: int
        +OffensiveEquipment(String name, String type, int attack)
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

    class DefensiveEquipment {
        <<abstract>>
        -type: String
        -name: String
        -bonus: int
        +DefensiveEquipment(String name, String type, int defense)
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
        +printCharacter(Character hero) void
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
    Game --> Board : has
    Board --> Cell : contains
    Cell <|-- EmptyCell : extends
    Cell <|-- Bonus : extends
    Cell <|-- Enemy : extends
    Cell <|-- WeaponCell : extends
    Character <|-- Warrior : extends
    Character <|-- Wizard : extends
    Character --> OffensiveEquipment : has
    OffensiveEquipment <|-- Weapon : extends
    OffensiveEquipment <|-- Spell : extends
    DefensiveEquipment <|-- Shield : extends
    DefensiveEquipment <|-- Potion : extends
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
   - Rencontrer des cases spéciales : bonus, ennemis, armes...

## Classes principales

### Application
- [`Main`](src/fr/neri/brumelame/app/Main.java) : Point d'entrée de l'application

### Personnages (`domain/hero`)
- [`Character`](src/fr/neri/brumelame/domain/hero/Character.java) : Classe abstraite pour les personnages
- [`Warrior`](src/fr/neri/brumelame/domain/hero/Warrior.java) : Classe guerrier (10 PV, 5 ATK)
- [`Wizard`](src/fr/neri/brumelame/domain/hero/Wizard.java) : Classe sorcier (6 PV, 8 ATK)

### Équipements (`domain/equipment`)
- [`OffensiveEquipment`](src/fr/neri/brumelame/domain/equipment/OffensiveEquipment.java) : Classe abstraite pour les équipements offensifs
- [`Weapon`](src/fr/neri/brumelame/domain/equipment/Weapon.java) : Arme pour les guerriers
- [`Spell`](src/fr/neri/brumelame/domain/equipment/Spell.java) : Sort pour les sorciers
- [`DefensiveEquipment`](src/fr/neri/brumelame/domain/equipment/DefensiveEquipment.java) : Classe abstraite pour les équipements défensifs
- [`Shield`](src/fr/neri/brumelame/domain/equipment/Shield.java) : Bouclier
- [`Potion`](src/fr/neri/brumelame/domain/equipment/Potion.java) : Potion de soin

### Jeu (`game`)
- [`Game`](src/fr/neri/brumelame/game/Game.java) : Gère la logique du jeu
- [`Board`](src/fr/neri/brumelame/game/Board.java) : Représente le plateau de jeu
- [`Dice`](src/fr/neri/brumelame/game/Dice.java) : Simule un dé pour le déplacement

### Cases (`game/cell`)
- [`Cell`](src/fr/neri/brumelame/game/cell/Cell.java) : Classe abstraite pour les cases du plateau
- [`EmptyCell`](src/fr/neri/brumelame/game/cell/EmptyCell.java) : Case vide
- [`Bonus`](src/fr/neri/brumelame/game/cell/Bonus.java) : Case bonus
- [`Enemy`](src/fr/neri/brumelame/game/cell/Enemy.java) : Case ennemi
- [`WeaponCell`](src/fr/neri/brumelame/game/cell/WeaponCell.java) : Case arme

### Interface utilisateur (`ui`)
- [`Menu`](src/fr/neri/brumelame/ui/Menu.java) : Gère l'interface utilisateur en ligne de commande


mkdir out
javac -d out $(find src -name "*.java")
cd out
jar cvf fr.neri.brumelame.jar .
java -cp fr.neri.brumelame.jar fr.neri.brumelame.app.Main
