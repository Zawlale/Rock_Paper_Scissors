/**
 * Picks a move that beats the player's current choice.
 */
public class Cheat implements Strategy
{
    /**
     * Gets the winning move against the player.
     *
     * @param playerMove the player's current choice
     * @return the move that beats the player
     */
    @Override
    public String getMove(String playerMove)
    {
        String computerMove = "";

        switch (playerMove)
        {
            case "R":
                computerMove = "P";
                break;
            case "P":
                computerMove = "S";
                break;
            case "S":
                computerMove = "R";
                break;
            default:
                throw new IllegalArgumentException("Invalid move");
        }

        return computerMove;
    }
}