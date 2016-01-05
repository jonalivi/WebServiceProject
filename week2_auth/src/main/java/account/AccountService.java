package account;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addUser(UserProfile profile) {
        loginToProfile.put(profile.getLogin(), profile);
    }
    
    public void deleteUser(String sessionId) {
        String login = getUserBySessionId(sessionId).getLogin();
        deleteSession(sessionId);
        loginToProfile.remove(login);
    }

    public void addSession(String sessionId, UserProfile profile) {
        sessionIdToProfile.put(sessionId, profile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

}
