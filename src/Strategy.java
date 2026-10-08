/**
 * Gives each strategy a method to pick a move.
 */
public interface Strategy
{
    /**
     * Gets the computer's move.
     *
     * @param playerMove the player's choice: R, P, or S
     * @return the computer's choice: R, P, or S
     */
    public String getMove(String playerMove);
}