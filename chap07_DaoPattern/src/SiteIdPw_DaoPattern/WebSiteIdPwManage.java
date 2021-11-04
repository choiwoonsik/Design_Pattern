package SiteIdPw_DaoPattern;

public class WebSiteIdPwManage {
    public static void main(String[] args) {
        DAO<SiteIdPwInfo, String> userSiteIdPwInfoDAO = new IdPwInfoDaoImpl("idPasswordTable");

        System.out.println("inserting site account info ...");
        userSiteIdPwInfoDAO.insert(
                new SiteIdPwInfo("https://www.smu.ac.kr", "smu", "abcde")
        );
        userSiteIdPwInfoDAO.insert(
                new SiteIdPwInfo("https://www.smu2.ac.kr", "smu2", "abcdefg")
        );

        System.out.println("\nchecking all info inserted ...");
        for (SiteIdPwInfo siteIdPwInfo : userSiteIdPwInfoDAO.findAll()) {
            System.out.println("reading > " + siteIdPwInfo);
        }

        System.out.println("\nupdating site account info ...");
        SiteIdPwInfo url = userSiteIdPwInfoDAO.findByKey("https://www.smu2.ac.kr");
        url.setUserId("smu1");
        userSiteIdPwInfoDAO.update(url);

        System.out.println("\nchecking info is updated ...");
        System.out.println(userSiteIdPwInfoDAO.findByKey("https://www.smu2.ac.kr"));

        System.out.println("\ndeleting info by URL ...");
        userSiteIdPwInfoDAO.deleteByKey("https://www.smu2.ac.kr");

        System.out.println("\nchecking info is deleted ...");
        for (SiteIdPwInfo siteIdPwInfo : userSiteIdPwInfoDAO.findAll()) {
            System.out.println("reading > "+ siteIdPwInfo);
        }
    }
}
