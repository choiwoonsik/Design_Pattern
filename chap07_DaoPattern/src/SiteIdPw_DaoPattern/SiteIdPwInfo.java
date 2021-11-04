package SiteIdPw_DaoPattern;

public class SiteIdPwInfo implements DbData<String>{
    private String url;
    private String userId;
    private String userPw;

    public SiteIdPwInfo(String url, String userId, String userPw) {
        this.url = url;
        this.userId = userId;
        this.userPw = userPw;
    }

    @Override
    public String getKey() {
        return this.url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    @Override
    public String toString() {
        return "UserSiteIdPwInfo{" +
                "url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                '}';
    }
}
