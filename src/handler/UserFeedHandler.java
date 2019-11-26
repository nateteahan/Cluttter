package handler;

import DatabaseAccess.FeedDAO;
import Model.Status;
import request.GetUserFeedRequest;
import response.GetUserFeedResponse;

import java.util.ArrayList;

public class UserFeedHandler {
    public GetUserFeedResponse handleFeedRequest(GetUserFeedRequest request) {
//        ArrayList<Status> statuses = new ArrayList<>();
//        Status status;

//        status = new Status("https://cdn.vox-cdn.com/thumbor/S52RPSmNS4cx7i4WR15H0A1jtYY=/0x0:2047x1080/1200x675/filters:focal(931x469:1257x795)/cdn.vox-cdn.com/uploads/chorus_image/image/58644051/InfinityWar5a4bb0e7cdea1.0.jpg","Nate", "@nateteahan", "1:48 a.m.", "Let me go to bed, for the love.", null, "https://external-preview.redd.it/TfNNGb8tpuBjf8KGSilXct5VTHP9SH2tmkc_LVK4wmY.gif?format=mp4&s=61cf99498e859c7dd72063a8c641b271dc03ffcf");
//        statuses.add(status);
//
//        status = new Status("http://i.imgur.com/bIRGzVO.jpg","Rocky", "@roscoe_evans", "6:53 p.m.", "My #CS 312 project ruined me today", "https://bangladhol.com/book_th/C29B5E81.jpg", null);
//        statuses.add(status);
//
//        status = new Status("https://www.famousbirthdays.com/faces/hunt-director-justin-image.jpg","Justin", "justinj_hunt", "8:00 p.m.", "I am going to go to bed early tonight.", null, null);
//        statuses.add(status);
//
//        status = new Status("https://cdn.vox-cdn.com/thumbor/S52RPSmNS4cx7i4WR15H0A1jtYY=/0x0:2047x1080/1200x675/filters:focal(931x469:1257x795)/cdn.vox-cdn.com/uploads/chorus_image/image/58644051/InfinityWar5a4bb0e7cdea1.0.jpg","Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", null, null);
//        statuses.add(status);
//
//        status = new Status("https://pbs.twimg.com/profile_images/919223887077916673/i3yEGPxn_400x400.jpg","Cayla", "@caylarobb", "6:53 p.m.", "Yesterday, @roscoeevans and I went to the grocery store and decided" + "" +
//                " that I needed some raspberries. I went to the fruit aisle and it turned out they had all gone bad already. Thanks @smiths", null, null);
//        statuses.add(status);
//
//        status = new Status("https://static.wixstatic.com/media/ed123f_ab419dfd3e3445dc8752924415ad60a6~mv2_d_3427_1329_s_2.jpg/v1/fill/w_640,h_392,al_c,q_80,usm_0.66_1.00_0.01/ed123f_ab419dfd3e3445dc8752924415ad60a6~mv2_d_3427_1329_s_2.webp","Jake", "@jakeweislerfilms", "7:00 a.m.", "Ladies and gentleman, the moment we have all been waiting for", null, null);
//        statuses.add(status);
//
//        status = new Status("http://dadbod2rippedbod.com/wp-content/uploads/2018/01/img-john.jpg","John", "@johnhansen", "6:20 a.m.", "I like to lift heavy things and put them down", null, null);
//        statuses.add(status);
//
//        status = new Status("https://www.uvu.edu/aviation/images/blog-images/tyler-braden2.jpg","Braden", "@b_lele", "6:53 p.m.", "Flying high in the sky", null, null);
//        statuses.add(status);
//
//        status = new Status("https://vignette.wikia.nocookie.net/disney/images/7/76/Matt_Lanter.jpg/revision/latest?cb=20190916224138","Matt", "@matt_lantis", "8:00 p.m.", "Hey guys, in case any of you didn't already know, I am dating someone now.", null, null);
//        statuses.add(status);
//
//        status = new Status("https://pbs.twimg.com/profile_images/1017243844432224256/hG6vtQF7_400x400.jpg","Bridger", "@bridger_penn", "7:45 a.m.", "You can start a venture capital firm at the age of 23 just like I did. Click the link to see how!", null, null);
//        statuses.add(status);
//
//        status = new Status("https://s3-us-west-1.amazonaws.com/co-directory-images/jim-teahan-81030696.jpg","Jim", "@dadzilla", "6:01 p.m.", "I don't think my son has slept in days. What should I do?",null, null);
//        statuses.add(status);
//
//        status = new Status("https://scontent-lax3-2.xx.fbcdn.net/v/t31.0-8/461290_538823056170094_835503862_o.jpg?_nc_cat=110&_nc_oc=AQmjEKMWhUS_gi2RooIu8ozhl-5O0BKyuZXGNmwcgndBNiW_tE8lJieL8A_8NXuguAA&_nc_ht=scontent-lax3-2.xx&oh=853cc3094a10485e61057d1a89390e7f&oe=5E57782A", "Karene", "@mamasmurf", "7:19 p.m.", "My son won't answer any of my phone calls, he must have a CS project due soon.",null ,null);
//        statuses.add(status);
//
//        status = new Status("https://kiteloft.com/media/catalog/product/cache/1/image/728x/17f82f742ffe127f42dca9de82fb58b1/d/a/davedes2_1.jpg","Samuel", "@samhansen", "3:31 p.m.", "I am so happy to be working on something other than a #CS project", null, null);
//        statuses.add(status);

        FeedDAO feedDAO = new FeedDAO();
        GetUserFeedResponse response = feedDAO.getUserFeed(request);

        return response;
    }
}
