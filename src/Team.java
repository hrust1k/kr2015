public class Team implements Comparable<Team> {
    private int wins = 0;
    private int draws = 0;
    private int losses = 0;

    private int points = 0;
    private int goals = 0;

    private String name;

    public Team(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }


    public void addWins(int wins) {
        this.wins = this.wins + wins;
        this.calcPoints();
    }

    public int getDraws() {
        return draws;
    }

    public void addDraws(int draw) {
        this.draws = this.draws + draw;
        this.calcPoints();
    }

    public int getLosses() {
        return losses;
    }

    public void addLosses(int losses) {
        this.losses = this.losses + losses;
    }

    public int getGoals() {
        return goals;
    }


    public void addGoals(int goals) {
        this.goals = this.goals + goals;
        this.calcPoints();
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean equals(Team otherTeam) {
        return (this.getName() == otherTeam.getName());
    }


    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Team [name=" + name + "]. Points: " + points + " Wins: " + wins;
    }


    public void calcPoints() {
        this.points = 3 + this.getWins() + this.getDraws();
    }



    @Override
    public int compareTo(Team t) {
        // TODO Auto-generated method stub
        if (points == t.getPoints()) {
            return Integer.compare(t.getGoals(), goals);
        } else {
            return Integer.compare(t.getPoints(), points);
        }

    }

}
