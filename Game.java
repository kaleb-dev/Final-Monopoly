import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

public class Game {

    // Declaring instance variables
    private int num_of_players;
    public Player[] players = null;
    public gameBoard board;
    private int turn;
    private String status;
    Player player;

    private Scanner input = new Scanner(System.in);
    private Random rand = new Random();

    // Constructor
    public Game(int newTotalPlayers) {
        System.out.println("\nWelcome to the Medical Monopoly");
        num_of_players = newTotalPlayers;
        players = new Player[newTotalPlayers];
        board = new gameBoard();
    }

    // Method to set up game
    public void setUpGame() {
        turn = 0;
        System.out.println("Enter the names of each player: ");
        for (int i = 0; i < num_of_players; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            String name = input.nextLine();
            players[i] = new Player(name, board);
        }
        System.out.println("\nLet's start the game!");
    }

    // Method to handle a turn
    public void makeTurn() {

        Player player = players[turn];
        lines();
        System.out.println(player.getName() + "'s turn!");
        System.out.println("You have $" + player.getBalance());
        lines();
        System.out.print("'r' to Roll the dice! 'q' to Quit): ");
        String cmd = input.nextLine();
        while (!cmd.equalsIgnoreCase("r") && !cmd.equalsIgnoreCase("q")) {
            System.out.print("Wrong input! Please enter either (r or q): ");
            cmd = input.nextLine();
        }
        if (cmd.equalsIgnoreCase("r")) {


            rollDice(player);
            winner(player);

        } else if (cmd.equalsIgnoreCase("q")) {} else {
            //Call on the turn for each user
            makeTurn();
        }

    }

    private void rollDice(Player player) {
        // Roll the dice
        int dice = rand.nextInt(6) + 1;

        System.out.println("\n----------------------");
        System.out.println("    You rolled a " + dice);
        System.out.println("----------------------\n");
        // Move the player
        player.move(dice);
        // Landed on property
        Property property = board.searchProperty(player.getPosition());
        if (property != null) {
            System.out.println("You landed on " + property.getName());
            // Check if property is owned
            if (property.getOwner() == null) {
                // Ask player if they want to buy it
                System.out.print(
                    "This property is unowned. Do you want to buy it for $" + property.getPrice() + "? (y/n): ");
                String answer = input.nextLine();
                while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                    System.out.print("Wrong input! Please enter either (y or n): ");
                    answer = input.nextLine();
                }
                if (answer.equalsIgnoreCase("y")) {
                    // Buy the property
                    if (player.getBalance() >= property.getPrice()) {
                        player.buyProperty(property);
                        congrats();
                        spacing();
                        System.out.println("You bought " + property.getName() + " for $" + property.getPrice() + "!");
                        status += (player.getName() + " bought " + property.getName() + " for $" + property.getPrice()) +
                            "\n";
                        System.out.println(property.getDetails());
                    } else {
                        System.out.println("You don't have enough money to buy this property.");
                        status += (player.getName() + " don't have enough money to buy " + property.getName() +
                            " property for $" + property.getPrice()) + "\n";
                    }
                }
            } else {
                // Pay rent
                System.out.println("This property is owned by " + property.getOwner().getName());
                int rent = property.getRent();
                System.out.println("You must pay $" + rent + " in rent.");
                player.payRent(property.getOwner(), rent);
                System.out.println("You paid $" + rent + " to " + property.getOwner().getName());
            }
        }
    }

    public void printToFile() {

        try {
            FileWriter writer = new FileWriter("Details.txt");
            writer.write(status);
            writer.close();
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("An error occurred. Exception in file 'gameStatus'");
        }
    }

    public void winner(Player player) {
        if (player.getBalance() >= 5000) {
            System.out.println("\nYou have won " + player.getName() + "!\n");
            System.out.println("                                                                                 ");
            System.out.println("                                          (%@@&                                  ");
            System.out.println("                                   .@@@@@@@@@@@@@                                ");
            System.out.println("                                %@@@@@@@@@@@@@@@@                                ");
            System.out.println("                               %@@@@@@@@@@@@@@@@@                                ");
            System.out.println("                               .@@@@@@@@@@@@@@@@@/.                              ");
            System.out.println("                 &@&@@,         /@@@@@@@@@@&@@@@@@@@@@*                         ");
            System.out.println("              .&(%%*(@(#(      *@@@@@@@@@@#/*,,,/%@&@@                           ");
            System.out.println("              @(@     &(@    *@@@@@(...&............%                      /@@@ ");
            System.out.println("            &@/%.      ,       &@/......,@&....,.@@..%                 @%%##@&  ");
            System.out.println("           &.&....%.           & #...........&(*#%#.*..               .(....%.# ");
            System.out.println("          .%../,.% (@@@@@@@@&* &(*,....,/*%    #    %..     *@@@@@@@@@* (%&/..% ");
            System.out.println("          #(&@..# (@@@@@@@@@@@@@@@@(.....%&&@(*#*....@#@@@@@@@@@@@@@@@@* /&%&&( ");
            System.out.println("          &(@     @@@@@@@@@@@@@@@@@@@#.............@@@@@@@@@@@@@@@@@@@@@  @@@#  ");
            System.out.println("         @(&.        %@@@@@@@@@@@@@&@@@@@@@&@@@@@@@@@@@@@@@@@@@@@@@@#           ");
            System.out.println("        @/#*              &@@@@@@@@@@@@@&&#%&%%##@@@@@@@@@@@@@@&                ");
            System.out.println("       %((#                  .@@@@@@@@@@@@%&  ,%@@@@@@@@@@&@                    ");
            System.out.println("      ,&(@                       %@@@@@@@@@&  (@@@@@@@@@%                       ");
            System.out.println("      @,@                         (@@@@@@@@@  @@@@@@@@@/                        ");
            System.out.println("                                  @@@@@@@@@@  @@@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@@@  @@@@@@@@@@                        ");
            System.out.println("                                 .@@@@@@@@@@/,@@@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@@@&%@@@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@@@@@@@@@@@@@@@                        ");
            System.out.println("                                 .@@@@@@@@@@(&@@@@@@@@@@                        ");
            System.out.println("                                 .@@@@@@@@@@  @@@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@@   ,@@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@/    (@@@@@@@@                        ");
            System.out.println("                                  @@@@@@@@.    ,@@@@@@@@                        ");
            System.out.println("                                 (@@@@@@@@      @@@@@@@@/                       ");
            System.out.println("                                 @@@@@@@@@      @@@@@@@@@                       ");
            System.out.println("                                 @@@@@@@@&      @@@@@@@@@                       ");
            System.out.println("                                 @@@@@@@@,      *@@@@@@@@                       ");
            System.out.println("                                 /@@@@@@/        (@@@@@@,                       ");
            System.out.println("                                *@@@@@@@,        *@@@@@@@.                      ");
            System.out.println("                               *@@@@@@@(          %@@@@@@@.                     ");
            System.out.println("\n");
        } else {
            // Next player's turn
            turn++;
            if (turn == num_of_players) {
                turn = 0;
            }
            makeTurn();
        }

    }

    // Method to start the game
    public void start() {

        setUpGame();
        board.printBoard();
        status = "";
        makeTurn();

    }

    public void rules() {
        System.out.println(
            "1. The game board is a 6x6 grid of spaces, each representing a different medical facility or service.");
        System.out.println("2. Each player starts with a set amount of money, determined before the game begins.");
        System.out.println("3. Players take turns rolling the dice and moving their game piece around the board.");
        System.out.println(
            "4. When a player lands on an unowned space, they have the option to buy it or put it up for auction.");
        System.out.println(
            "5. If a player owns all spaces of the same color, they have a \"monopoly\" and can charge higher rent for those spaces.");
        System.out.println(
            "6. Players can also collect rent from their opponents when they land on spaces that the player owns.");
        System.out.println(
            "7. Players can also draw cards from a \"Chance\" or \"Community Chest\" deck, which can have positive or negative effects on the player's money or properties.");
        System.out.println(
            "8. Players can also build houses and hotels on their properties to increase the rent they can charge.");
        System.out.println(
            "9. The game ends when one player runs out of money or properties, or when the other player decides to quit.");
        System.out.println("10. The player with 5000 or more dollars wins the game.");
        System.out.println("Note: 2 players only can play at a time.");

    }

    public void board() {
        String[][] boardArr = new String[][] {
            {
                "□",
                "□",
                "□",
                "□",
                "□",
                "□"
            }, {
                "□",
                " ",
                " ",
                " ",
                " ",
                "□"
            }, {
                "□",
                " ",
                " ",
                " ",
                " ",
                "□"
            }, {
                "□",
                " ",
                " ",
                " ",
                " ",
                "□"
            }, {
                "□",
                " ",
                " ",
                " ",
                " ",
                "□"
            }, {
                "□",
                "□",
                "□",
                "□",
                "□",
                "□"
            }
        };
        for (int i = 0; i < boardArr.length; i++) {
            for (int j = 0; j < boardArr[i].length; j++) {
                System.out.print(boardArr[i][j] + " ");
            }
            System.out.println();
        }

        System.out.print("\nEnter location on the board you would like info on (1 through 20): ");
        int squareLocation = input.nextInt();

        for (Property property: board.getProperties()) {
            if (property.getLocation() == squareLocation) {
                System.out.println(property.toString());
            }
        }

    }

    public void spacing() {
        System.out.println("\n");
    }

    public void lines() {
        System.out.println("\n----------------------\n");
    }

    public void congrats() {
        System.out.println("\n------------------");
        System.out.println("| CONGRATULATIONS |");
        System.out.println("------------------");
    }


    public void menu() {

        // Menu options for what user would like to do for the rectangle
        System.out.print("\n----------------------------------------");
        System.out.print("\n                  Menu                  ");
        System.out.print("\n----------------------------------------\n");

        System.out.print("\n<<          1 - Play Monopoly         >>");
        System.out.print("\n<<          2 - Rules                 >>");
        System.out.print("\n<<          3 - Print to File         >>");
        System.out.print("\n<<          4 - Check Board           >>");
        System.out.print("\n<<          0 - Exit                  >>");
    }
}