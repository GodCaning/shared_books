package com.xust.wtc.Controller.user;

import com.xust.wtc.Entity.user.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Spirit on 2017/12/4.
 */
@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/addFriend", method = RequestMethod.GET,
                    consumes = "application/json", produces = "application/json")
    public Result addFriend(@RequestParam(value = "concernedId") int concernedId,
                            @RequestParam(value = "fansId") int fansId) {
        return friendService.addFriend(concernedId, fansId);
    }

    @RequestMapping(value = "/deleteFriend/{fansId}/{concernedId}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable("fansId") int fansId,
                               @PathVariable("concernedId") int concernedId) {
        return friendService.deleteFriend(concernedId,fansId);
    }

    @RequestMapping(value = "/listConcernedInfo", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    public List<Person> listConcernedInfo(@RequestParam(value = "id") int id) {
        return friendService.listConcernedInfo(id);
    }

    @RequestMapping(value = "/listFansInfo", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    public List<Person> listFansInfo(@RequestParam(value = "id") int id) {
        return friendService.listFansInfo(id);
    }


}
