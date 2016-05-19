package rest;

import database.MusicDataSet;
import main.AccountService;

import java.util.*;

/**
 * Created by user on 19.05.16.
 */
public class GameQuestions {
    private main.Context context;

    private HashMap<Long, String> rightAns;

    public ArrayList<Set<String>> answers;

    public GameQuestions() {
        final AccountService accountService = context.get(AccountService.class);

        rightAns = new HashMap<>();
        answers = new ArrayList<>();

        ArrayList<MusicDataSet> songs = accountService.getTracks();

        Random r = new Random();

        for (int i=0; i<songs.size(); i++) {
            rightAns.put(songs.get(i).getID(), songs.get(i).getAuthor());
            Set<String> ans = new HashSet<>();
            while (ans.size() < 4) {
                ans.add(accountService.getTrack((long) r.nextInt(5)).getAuthor());
            }
            answers.add(ans);
        }
    }
}
