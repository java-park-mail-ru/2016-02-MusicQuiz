package rest;

import database.MusicDataSet;
import main.AccountService;
import main.Context;

import java.util.*;

/**
 * Created by user on 19.05.16.
 */
public class GameQuestions {
    private Context context;

    private ArrayList<Long> tracksId;

    private HashMap<Long, String> rightAns;

    public ArrayList<Set<String>> answers;

    public GameQuestions() {
        final AccountService accountService = context.get(AccountService.class);

        rightAns = new HashMap<>();
        answers = new ArrayList<>();

        ArrayList<MusicDataSet> songs = accountService.getTracks();

        Random r = new Random();

        for (MusicDataSet song : songs) {
            rightAns.put(song.getID(), song.getAuthor());
            tracksId.add(song.getID());
            Set<String> ans = new HashSet<>();
            MusicDataSet track = accountService.getTrack((long) r.nextInt(5));
            if (track == null)
                return;
            while (ans.size() < 4) {
                try {
                    ans.add(track.getAuthor());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            answers.add(ans);
        }
    }

    public long getIdQuestion(int n) {
        return tracksId.get(n);
    }

    public Set<String> getAnswers(int n) {
        return answers.get(n);
    }
}
