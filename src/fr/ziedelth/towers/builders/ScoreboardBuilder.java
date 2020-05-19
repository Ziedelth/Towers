package fr.ziedelth.towers.builders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class ScoreboardBuilder {
    private final Scoreboard scoreboard;
    private final String title;
    private final List<Objective> objectives = new ArrayList<>();
    private final int start = 15;
    private final Map<Objective, Integer> scores = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();

    public ScoreboardBuilder(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.title = title;
    }

    public ScoreboardBuilder addTeam(TeamBuilder teamBuilder) {
        if (this.scoreboard.getTeam(teamBuilder.name) != null) return this;
        Team team = this.scoreboard.registerNewTeam(teamBuilder.name);
        team.setPrefix(teamBuilder.prefix);
        team.setSuffix(teamBuilder.suffix);
        if (teamBuilder.chatColor != null) team.setColor(teamBuilder.chatColor);
        if (!teamBuilder.hasCollision) team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        if (!teamBuilder.nametagIsVisible)
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        team.setAllowFriendlyFire(teamBuilder.allowFriendlyFire);
        this.teams.put(teamBuilder.name, team);
        return this;
    }

    public ScoreboardBuilder addObjective(DisplaySlot slot) {
        Objective objective = this.scoreboard.registerNewObjective(this.objectives.size() + "", "display");
        objective.setDisplayName(this.title);
        objective.setDisplaySlot(slot);
        this.objectives.add(objective);
        this.scores.put(objective, this.start);
        return this;
    }

    public ScoreboardBuilder addHealthObjective(DisplaySlot slot) {
        Objective objective = this.scoreboard.registerNewObjective(this.objectives.size() + "", "health");
        objective.setDisplayName("§c♥");
        objective.setDisplaySlot(slot);
        this.objectives.add(objective);
        this.scores.put(objective, this.start);
        return this;
    }

    public void updateHealth() {
        for (Objective objective : this.scores.keySet()) {
            if (!(objective.getCriteria().equalsIgnoreCase("health"))) continue;
            for (Player players : Bukkit.getOnlinePlayers())
                objective.getScore(players.getName()).setScore((int) players.getHealth());
        }
    }

    private String getEntryFromScore(Objective o, int score) {
        if (o == null) return null;
        if (!hasScoreTaken(o, score)) return null;
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
        }
        return null;
    }

    private boolean hasScoreTaken(Objective o, int score) {
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) return true;
        }
        return false;
    }

    private void replaceScore(Objective o, int score, String name) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
            if (!(getEntryFromScore(o, score).equalsIgnoreCase(name)))
                o.getScoreboard().resetScores(getEntryFromScore(o, score));
        }
        o.getScore(name).setScore(score);
    }

    public ScoreboardBuilder resetDisplayScore() {
        List<Objective> objectives = new ArrayList<>();
        this.objectives.stream().filter(objective -> Objects.equals(objective.getDisplaySlot(), DisplaySlot.SIDEBAR)).forEach(objectives::add);
        objectives.forEach(objective -> this.scores.put(objective, this.start));
        return this;
    }

    public ScoreboardBuilder addDisplayScore(String name) {
        List<Objective> objectives = new ArrayList<>();
        this.objectives.stream().filter(objective -> Objects.equals(objective.getDisplaySlot(), DisplaySlot.SIDEBAR)).forEach(objectives::add);

        objectives.forEach(objective -> {
            int score = this.scores.get(objective);
            if (score <= 0) score = this.start;
            this.replaceScore(objective, score--, name);
            this.scores.put(objective, score);
        });

        return this;
    }

    public ScoreboardBuilder setScore(DisplaySlot slot, String score, int newScore) {
        List<Objective> objectives = new ArrayList<>();
        this.objectives.stream().filter(objective -> Objects.equals(objective.getDisplaySlot(), slot)).forEach(objectives::add);
        objectives.forEach(objective -> objective.getScore(score).setScore(newScore));
        return this;
    }

    public ScoreboardBuilder buildTo(Player... players) {
        for (int i = 0; i < players.length; i++) players[i].setScoreboard(this.scoreboard);
        return this;
    }

    public ScoreboardBuilder removeTo(Player... players) {
        for (int i = 0; i < players.length; i++)
            players[i].setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        return this;
    }

    public ScoreboardBuilder addTo(String team, Player... players) {
        this.remove();

        if (this.teams.containsKey(team)) {
            Team teamScoreboard = this.teams.get(team);

            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                String name = player.getName();
                if (!teamScoreboard.hasEntry(name)) teamScoreboard.addEntry(name);
            }
        }

        return this;
    }

    private ScoreboardBuilder remove(Player... players) {
        for (String team : this.teams.keySet()) {
            Team teamScoreboard = this.teams.get(team);

            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                String name = player.getName();
                if (teamScoreboard.hasEntry(name)) teamScoreboard.removeEntry(name);
            }
        }

        return this;
    }

    public static class TeamBuilder {
        private String name;
        private ChatColor chatColor;
        private String prefix = "";
        private String suffix = "";
        private boolean hasCollision = false;
        private boolean nametagIsVisible = true;
        private boolean allowFriendlyFire = false;

        public String getName() {
            return name;
        }

        public TeamBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ChatColor getChatColor() {
            return chatColor;
        }

        public TeamBuilder setChatColor(ChatColor chatColor) {
            this.chatColor = chatColor;
            return this;
        }

        public String getPrefix() {
            return prefix;
        }

        public TeamBuilder setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public String getSuffix() {
            return suffix;
        }

        public TeamBuilder setSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public boolean isHasCollision() {
            return hasCollision;
        }

        public TeamBuilder setHasCollision(boolean hasCollision) {
            this.hasCollision = hasCollision;
            return this;
        }

        public boolean isNametagIsVisible() {
            return nametagIsVisible;
        }

        public TeamBuilder setNametagIsVisible(boolean nametagIsVisible) {
            this.nametagIsVisible = nametagIsVisible;
            return this;
        }

        public boolean isAllowFriendlyFire() {
            return allowFriendlyFire;
        }

        public TeamBuilder setAllowFriendlyFire(boolean allowFriendlyFire) {
            this.allowFriendlyFire = allowFriendlyFire;
            return this;
        }
    }
}
