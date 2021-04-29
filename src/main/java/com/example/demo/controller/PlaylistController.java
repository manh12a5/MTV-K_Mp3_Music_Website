package com.example.demo.controller;

import com.example.demo.model.PlayList;
import com.example.demo.model.user.User;
import com.example.demo.service.playlist.PlaylistService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<PlayList>> showAll() {
        List<PlayList> playLists = playlistService.findAll();
        if (playLists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(playLists, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PlayList> createPlaylist(@RequestBody PlayList playList) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        playList.setTimeCreate(timestamp);
        playlistService.save(playList);
        return new ResponseEntity<>(playList, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PlayList> editPlaylist(@PathVariable Long id, @RequestBody PlayList playList) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        playList.setTimeUpdate(timestamp);
        playList.setId(id);
        if (playList.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            playlistService.save(playList);
            return new ResponseEntity<>(playList, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlayList> deletePlaylist(@PathVariable Long id) {
        playlistService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PlayList>> findAllByUserUsername(@PathVariable String username) {
        List<PlayList> playlists = playlistService.findAllByUserUsername(username);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @PostMapping("/user/create/{username}")
    public ResponseEntity<PlayList> createNewPlayListByUser(@RequestBody PlayList playList, @PathVariable String username){
        User user = userService.findUserByUserName(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        playList.setTimeUpdate(timestamp);
        playList.setUser(user);
        return new ResponseEntity<>(playlistService.save(playList), HttpStatus.CREATED);
    }

    @DeleteMapping("/user/create/{username}/{id}")
    public ResponseEntity<PlayList> deletePlayListByUser(@PathVariable Long id, @PathVariable String username){
            playlistService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
