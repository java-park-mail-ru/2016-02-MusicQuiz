package rest;

import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by seven-teen on 12.05.16.
 */
public class Helper {
    @Nullable
    protected static String getCookie(HttpServletRequest request){
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("MusicQuiz")) {
                    sessionID = cookie.getValue();
                    break;
                }
            }
        }
        return sessionID;
    }

    public static void sleep(long period){
        if (period <= 0) {
            return;
        }
        try{
            Thread.sleep(period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(){
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

