package com.github.zxkane.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kane on 2016/12/14.
 */
@RestController
@RequestMapping("/path1")
@Slf4j
public class Controller1 {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Model1 {
        String key1;
    }

    @Secured("ROLE_STAFF")
    @RequestMapping(value = "/withPageable", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> servlet1(@AuthenticationPrincipal UserDetails activeUser,
                                      Pageable pageable) {
        if (activeUser.getUsername().equals("staff"))
            return ResponseEntity.ok("staff");
        return ResponseEntity.ok("nonstaff");
    }

    @Secured("ROLE_STAFF")
    @RequestMapping(value = "/withoutPageable", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> servlet2(@AuthenticationPrincipal UserDetails activeUser) {
        if (activeUser.getUsername().equals("staff"))
            return ResponseEntity.ok("staff");
        return ResponseEntity.ok("nonstaff");
    }
}
