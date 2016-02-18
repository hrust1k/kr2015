import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TeamDatabase {
    private Map<String, Team> teamMap = new HashMap<String, Team>();
    private ArrayList<Match> matchList = new ArrayList<Match>();
    private ArrayList<Team> teamList = new ArrayList<Team>();
    private String formattedTeamList;

    private LossCompare lossCompare = new LossCompare();
    private GoalCompare goalCompare = new GoalCompare();
    private NameCompare nameCompare = new NameCompare();
    private DrawCompare drawCompare = new DrawCompare();



    public TeamDatabase(String[] data) {
        parseData(data);
        updateTeamList();
        doSmt();
    }

    public void doSmt(){
        teamList.clear();
        teamList.addAll(teamMap.values());
        Collections.sort(teamList);
        formattedTeamList = formatTeamList(teamList);
    }

    public Map<String, Team> getTeamList() {
        return teamMap;
    }

    public String[] getTeamStrings(){
        return teamMap.keySet().toArray(new String[teamMap.size()]);
    }


    public ArrayList<Match> getMatchList() {
        return matchList;
    }



    // Takes an array of strings from a csv and splits it into teams and scores.
    // For each line, a new Match object is created.
    public void parseData(String[] data) {
        for (String string : data) {
            String[] tokens = string.split(",");
            String[] scoreStr = tokens[1].split("-");
            int[] scores = new int[2];
            for (int i = 0; i < scoreStr.length; i++) {
                scores[i] = Integer.parseInt(scoreStr[i]);
            }
            Date date;
            try {
                date = this.readDateString(tokens[3]);
                this.matchList.add(new Match(tokens[0], tokens[2], scores[0], scores[1], date));
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void updateTeamList() {
        for (Match match : matchList) {

            String challenger = match.getChallenger();
            String defender = match.getDefender();

            // Updating teamMap. The teamMap is a hashMap where the key is the
            // team name and the value is the team object.
            // This checks to see if the team has already been added, and if it
            // has not been, it adds the appropriate key/value pair.
            if (teamMap.containsKey(challenger) == false) {
                teamMap.put(challenger, new Team(challenger));
            }
            if (teamMap.containsKey(defender) == false) {
                teamMap.put(defender, new Team(defender));
            }

            // Updates draws and goals if the match was a draw. Updates wins,
            // losses and goals if not.
            if (match.isDraw() == true) {
                teamMap.get(challenger).addDraws(1);
                teamMap.get(challenger).addGoals(match.getChallengerScore());

                teamMap.get(defender).addDraws(1);
                teamMap.get(defender).addGoals(match.getDefenderScore());
            } else {
                String winner = match.getWinner();
                int winnerGoals = match.getWinnerGoals();

                String loser = match.getDefender();
                int loserGoals = match.getLoserGoals();

                teamMap.get(winner).addWins(1);
                teamMap.get(winner).addGoals(winnerGoals);

                teamMap.get(loser).addLosses(1);
                teamMap.get(loser).addGoals(loserGoals);
            }
        }

    }


    public String formatTeamList(Collection<Team> listOfTeams) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-13s\t%-7s\t%-7s\t%-7s\t%-7s\t%-7s\n", "Name", "Points", "Goals", "Wins", "Losses",
                "Draws"));
        for (Team team : listOfTeams) {
            sb.append(String.format("%-13s\t%-7d\t%-7d\t%-7d\t%-7d\t%-7d\n", team.getName(), team.getPoints(),
                    team.getGoals(), team.getWins(), team.getLosses(), team.getDraws()));
        }
        return sb.toString();

    }

    public String getFormattedTeamList() {
        return formattedTeamList;
    }

    private class NameCompare implements Comparator<Team> {

        @Override
        public int compare(Team o1, Team o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    private class LossCompare implements Comparator<Team> {

        @Override
        public int compare(Team o1, Team o2) {
            return Integer.compare(o2.getLosses(), o1.getLosses());
        }

    }

    private class DrawCompare implements Comparator<Team> {

        @Override
        public int compare(Team o1, Team o2) {
            // TODO Auto-generated method stub
            return Integer.compare(o2.getDraws(), o1.getDraws());
        }

    }

    private class GoalCompare implements Comparator<Team> {

        @Override
        public int compare(Team o1, Team o2) {
            return Integer.compare(o2.getGoals(), o1.getGoals());
        }
    }

    public String customCompare(String option) {
        switch (option) {
            case "draws":
                Collections.sort(teamList, drawCompare);
                return formatTeamList(teamList);
            case "name":
                Collections.sort(teamList, nameCompare);
                return formatTeamList(teamList);
            case "goals":
                Collections.sort(teamList, goalCompare);
                return formatTeamList(teamList);
            case "losses":
                Collections.sort(teamList, lossCompare);
                return formatTeamList(teamList);

            default:
                System.out.println("Compare option not recognised");
                return "";
        }
    }


    private Date readDateString(String dateString) throws ParseException {
       dateString = dateString.replaceAll("(?<=[0-9]{1,2})[a-z]{2}", "").trim();
       SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.ENGLISH);
       return sdf.parse(dateString);
    }

}
