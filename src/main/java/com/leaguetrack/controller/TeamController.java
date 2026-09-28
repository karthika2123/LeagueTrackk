package com.leaguetrack.controller;

import com.leaguetrack.entity.Team;
import com.leaguetrack.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Team registerTeam(@RequestBody Team team) {
        return teamService.registerTeam(team);
    }

    @GetMapping
    public List<Team> getTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable Long id) {
        return teamService.getTeam(id);
    }
}