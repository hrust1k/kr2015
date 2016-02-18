import java.util.Date;

public class Match {
    private String winner;
    private int winnerGoals;
    private String loser;
    private int loserGoals;
    private boolean isDraw;

    private String challenger;
    private String defender;
    private int challengerScore;
    private int defenderScore;
    private Date date;


    public Match(String challenger, String defender, int challengerScore, int defenderScore, Date date) {

        this.challenger = challenger;
        this.defender = defender;
        this.challengerScore = challengerScore;
        this.defenderScore = defenderScore;
        this.date = date;


        if (this.getChallengerScore() > this.getDefenderScore()) {
            this.winner = this.getChallenger();
            this.winnerGoals = this.getChallengerScore();
            this.loser = this.getDefender();
            this.loserGoals = this.getDefenderScore();
            this.isDraw = false;

        } else if (this.getChallengerScore() < this.getDefenderScore()) {
            this.winner = this.getDefender();
            this.winnerGoals = this.getDefenderScore();
            this.loser = this.getChallenger();
            this.loserGoals = this.getChallengerScore();
            this.isDraw = false;

        } else if (this.getChallengerScore() == this.getDefenderScore()) {
            this.winner = null;
            this.loser = null;
            this.isDraw = true;
        }
    }


    public String getWinner() throws NullPointerException {
        return winner;
    }


    public String getLoser() throws NullPointerException {
        return loser;
    }

    public String getChallenger() {
        return challenger;
    }

    public String getDefender() {
        return defender;
    }

    public int getChallengerScore() {
        return challengerScore;
    }

    public int getDefenderScore() {
        return defenderScore;
    }


    public int getWinnerGoals() throws NullPointerException {
        return winnerGoals;
    }


    public int getLoserGoals() throws NullPointerException {
        return loserGoals;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public Date getDate() {
        return date;
    }

}
