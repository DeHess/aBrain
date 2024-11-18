package ch.zhaw.pm3.aBrain.backend.gamification;

/**
 * The type Reward system.
 */
public class RewardSystem {
    /**
     * The enum Accomplishment.
     */
// TODO noch verfeinern
    /*
    - Regularly = the same amount as last week
    - On time  = +- 20 min
    *  */
    enum Accomplishment{
        /**
         * Meds on time accomplishment.
         */
        MEDS_ON_TIME(1),
        /**
         * Performed sports accomplishment.
         */
        PERFORMED_SPORTS(2),
        /**
         * Entered daily mood accomplishment.
         */
        ENTERED_DAILY_MOOD(1),
        /**
         * Performed sports regularly accomplishment.
         */
        PERFORMED_SPORTS_REGULARLY(5);
    
        private int points;
    
        Accomplishment(int points) {
            this.points = points;
        }
    
        /**
         * Gets points.
         *
         * @return the points
         */
        public int getPoints() {
            return points;
        }
    }
}
