import java.util.Random;

/**
 * Picks a random move for the computer.
 */
public class RandomStrategy implements Strategy
{
    Random rnd = new Random();

    /**
     * Randomly picks rock, paper, or scissors.
     *
     * @param playerMove the player's choice, not used here
     * @return the computer's random move
     */
    @Override
    public String getMove(String playerMove)
    {
        String computerMove = "";
        int choice = rnd.nextInt(3);

        switch (choice)
        {
            case 0:
                computerMove = "R";
                break;
            case 1:
                computerMove = "P";
                break;
            case 2:
                computerMove = "S";
                break;
        }

        return computerMove;
    }
}