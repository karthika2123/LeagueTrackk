package com.leaguetrack.service;

import com.leaguetrack.entity.Team;
import com.leaguetrack.exception.BusinessRuleException;
import com.leaguetrack.exception.ResourceNotFoundException;
import com.leaguetrack.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team registerTeam(Team team) {

        if (team.getName() == null ||
                team.getName().trim().isEmpty()) {
            throw new BusinessRuleException("Team name cannot be empty");
        }

        if (teamRepository.findByNameIgnoreCase(team.getName()).isPresent()) {
            throw new BusinessRuleException("Team already exists");
        }

        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Team not found with id: " + id));
    }
}