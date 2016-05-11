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

}
