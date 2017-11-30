package com.branter.jiadongyan.branter;

/**
 * Created by Jerry on 11/30/17.
 */

public class FakeImg {
    static String[] img = new String[] {
            "https://d2r4vjqv0e4e2o.cloudfront.net/blog/wp-content/uploads/2016/10/24114946/Events.jpg",
            "https://media.timeout.com/images/102823416/1372/772/image.jpg",
            "http://www.bahrain101.com/wp-content/uploads/2016/10/events-1-770x433.jpg",
            "http://photos.visitphilly.com/spruce-street-harbor-park-restaurant-dusk-m.edlow-900VP.jpg",
            "http://digclub.org/wp-content/uploads/2014/02/featured-tables-night.jpg"
    };

    public static GridTest[] allposts = new GridTest[100];
    public static GridTest[] footballposts = new GridTest[100];
    public static GridTest[] concertposts = new GridTest[100];
    public static GridTest[] celticsposts = new GridTest[100];
    public static GridTest[] gourmetposts = new GridTest[100];


    public static void create() {
        GridTest football_post0 = new GridTest();
        football_post0.setEventTitle("Football Game");
        football_post0.setContent("Good game!");
        football_post0.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/KU-vs-Mizzou-Nov-29-08_%282%29.jpg/1200px-KU-vs-Mizzou-Nov-29-08_%282%29.jpg#" +
        "https://i.pinimg.com/originals/b3/fc/31/b3fc31345130e0d09b54985bedb2d95c.jpg#" +
        "http://www.ifaf.info/wp-content/uploads/2017/07/TWG-2017-Today-American-Football-Finals.jpg");
        football_post0.setHeadphoto("http://www.gx8899.com/uploads/allimg/160804/3-160P4111639.jpg");

        allposts[0] = football_post0;
        footballposts[0] = football_post0;


        GridTest concert_post0 = new GridTest();
        concert_post0.setEventTitle("Concert");
        concert_post0.setContent("So happy!");
        concert_post0.setImage("https://static.pexels.com/photos/196652/pexels-photo-196652.jpeg");
        concert_post0.setHeadphoto("http://img.qq745.com/uploads/allimg/20170816/xw1vnloe4od345.jpg");


        allposts[1] = concert_post0;
        concertposts[0] = concert_post0;


        GridTest celtics_post0 = new GridTest();
        celtics_post0.setEventTitle("Celtics Games");
        celtics_post0.setContent("Go Celtics!");
        celtics_post0.setImage("http://i2.cdn.turner.com/nba/nba/dam/assets/160118095756-evan-turner-marcus-smart-isaiah-thomas-jae-crowder-jonas-jerebko-nba-new-york-knicks-at-boston-celtics.home-t1.jpeg#" +
        "http://static2.businessinsider.com/image/56c3485d6e97c623048b7658-1190-625/the-boston-celtics-are-the-nbas-biggest-x-factor-2-days-before-the-trade-deadline.jpg");
        celtics_post0.setHeadphoto("http://p3.wmpic.me/article/2015/03/16/1426483394_eJakzHWr.jpeg");


        allposts[2] = celtics_post0;
        celticsposts[0] = celtics_post0;

        GridTest gourmet_post0 = new GridTest();
        gourmet_post0.setEventTitle("Gourmet Festival");
        gourmet_post0.setContent("Yummy!");
        gourmet_post0.setImage("http://visitbudapest.travel/images/made/images/content/body/budai-gourmet-festival1_574_383.jpg#" +
                "https://www.ggcatering.com/system/uploads/fae/image/asset/2954/Global_Gourmet_hp_banner_3.jpg");
        gourmet_post0.setHeadphoto("http://www.chedan5.com/upload/article/201701/20/1027425881759e5a8d8noHIjP.jpg");


        allposts[3] = gourmet_post0;
        gourmetposts[0] = gourmet_post0;

        GridTest football_post1 = new GridTest();
        football_post1.setEventTitle("Football Game");
        football_post1.setContent("So exicted, my team win!");
        football_post1.setImage("https://s.yimg.com/ny/api/res/1.2/qNpaQwkEO7ngAqoyzIRYxA--/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAwO2lsPXBsYW5l/http://media.zenfs.com/en_us/News/afp.com/c8d00c364d43d5bad43717587c327a268cee5bbf.jpg");
        football_post1.setHeadphoto("http://scimg.jb51.net/touxiang/201709/2017091018241774.jpg");

        allposts[4] = football_post1;
        footballposts[1] = football_post1;

        GridTest concert_post1 = new GridTest();
        concert_post1.setEventTitle("Concert");
        concert_post1.setContent("Enjoy the music tonight!");
        concert_post1.setImage("https://static.pexels.com/photos/154147/pexels-photo-154147.jpeg#" +
        "https://media.timeout.com/images/102503695/image.jpg");
        concert_post1.setHeadphoto("http://img.171u.com/image/1605/1608552241288.jpeg");


        allposts[5] = concert_post1;
        concertposts[1] = concert_post1;

        GridTest celtics_post1 = new GridTest();
        celtics_post1.setEventTitle("Celtics Games");
        celtics_post1.setContent("I bet with my friends Celtics will win tonight");
        celtics_post1.setImage("http://ara-rowing.org/wp-content/uploads/2017/03/boston-celtics.jpg");
        celtics_post1.setHeadphoto("http://img.sc115.com/tx/ka/pic/1509ov0xknruucw.jpg");


        allposts[6] = celtics_post1;
        celticsposts[1] = celtics_post1;

        GridTest gourmet_post1 = new GridTest();
        gourmet_post1.setEventTitle("Gourmet Festival");
        gourmet_post1.setContent("Like these dishes!");
        gourmet_post1.setImage("http://www.hannasgourmetcatering.com/Hanna_s_Gourmet-19.jpg#" +
                "http://cdn.skim.gs/image/upload/v1456337831/msi/grilled-salmon-gourmet-dinner-horiz_uyauuc.jpg");
        gourmet_post1.setHeadphoto("http://www.feizl.com/upload2007/2011_02/110217230339492.jpg");


        allposts[7] = gourmet_post1;
        gourmetposts[1] = gourmet_post1;

        GridTest football_post2 = new GridTest();
        football_post2.setEventTitle("Football Game");
        football_post2.setContent("Go ahead for win, warriors");
        football_post2.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Larry_Fitzgerald_catches_TD_at_2009_Pro_Bowl.jpg/1200px-Larry_Fitzgerald_catches_TD_at_2009_Pro_Bowl.jpg");
        football_post2.setHeadphoto("http://img2.kanqq.com/upload/2017/1123/7f10f4844cc8e491b79a9557f814db30.jpg");

        allposts[8] = football_post2;
        footballposts[2] = football_post2;

        GridTest concert_post2 = new GridTest();
        concert_post2.setEventTitle("Concert");
        concert_post2.setContent("Excellent concert!");
        concert_post2.setImage("https://static.pexels.com/photos/248963/pexels-photo-248963.jpeg");
        concert_post2.setHeadphoto("http://www.onegreen.net/QQ/UploadFiles/201710/2017100710155917.jpg");

        allposts[9] = concert_post2;
        concertposts[2] = concert_post2;

        GridTest celtics_post2 = new GridTest();
        celtics_post2.setEventTitle("Celtics Games");
        celtics_post2.setContent("We can win final chanmpionship");
        celtics_post2.setImage("https://cdn.vox-cdn.com/uploads/chorus_asset/file/8431909/usa_today_9804106.jpg");
        celtics_post2.setHeadphoto("http://www.gx8899.com/uploads/allimg/160804/3-160P4111640.jpg");


        allposts[10] = celtics_post2;
        celticsposts[2] = celtics_post2;

        GridTest gourmet_post2 = new GridTest();
        gourmet_post2.setEventTitle("Gourmet Festival");
        gourmet_post2.setContent("Never tried such kinds of food, so delicious");
        gourmet_post2.setImage("http://www.ramblgyrl.com/wp-content/uploads/2013/04/Gourmet-Chicken.jpg#" +
                "https://pantograph0.goldbely.com/uploads/merchant/main_image/559/hancock-gourmet-lobster-co.c62365d58493722415029905459b0cc6.jpg#" +
                "http://www.mulierchile.com/gourmet/gourmet-002.jpg");
        gourmet_post2.setHeadphoto("http://img.qq745.com/uploads/allimg/141017/1-14101F94147.jpg");


        allposts[11] = gourmet_post2;
        gourmetposts[2] = gourmet_post2;
    }
}
