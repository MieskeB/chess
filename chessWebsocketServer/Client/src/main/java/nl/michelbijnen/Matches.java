package nl.michelbijnen;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Matches {
    private Matches(){
        this.ongoingMatches = new HashMap<>();
        this.ongoingSearches = new ArrayList<>();
    }
    private static Matches instance = new Matches();
    public static Matches getInstance() {
        return instance;
    }



    private ArrayList<Searching> ongoingSearches;

    private HashMap<Session, Session> ongoingMatches;

    public HashMap<Session, Session> getOngoingMatches() {
        return this.ongoingMatches;
    }

    public void addOngoingMatch(Session session1, Session session2) {
        for (Searching searching : new ArrayList<>(this.ongoingSearches)) {
            if (searching.getSession() == session1 || searching.getSession() == session2) {
                this.ongoingSearches.remove(searching);
            }
        }

        this.ongoingMatches.put(session1, session2);
    }

    public ArrayList<Searching> getOngoingSearches() {
        return this.ongoingSearches;
    }

    public void removeSessionFromOngoingSearches(Session session) {
        for (Searching searching : new ArrayList<>(this.ongoingSearches)) {
            if (searching.getSession() == session) {
                this.ongoingSearches.remove(searching);
            }
        }
    }

    public void addOngoingSearch(Session session, String username, int score) {
        Searching searching = new Searching();
        searching.setSession(session);
        searching.setUsername(username);
        searching.setScore(score);
        this.ongoingSearches.add(searching);
    }

    public Session getOpponent(Session session) {
        Session key = this.getKey(session);
        if (key != session) {
            return key;
        }
        return this.ongoingMatches.get(key);
    }

    public void removeSessionsFromOngoingMatches(Session session) {
        try {
            this.ongoingMatches.remove(this.getKey(session));
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Session getKey(Session session) {
        for (Map.Entry<Session, Session> entry : this.ongoingMatches.entrySet()) {
            if (entry.getKey() == session || entry.getValue() == session) {
                return entry.getKey();
            }
        }

        return null;
    }
}
