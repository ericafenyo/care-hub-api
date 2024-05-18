/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2024 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.services.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService service;

    @PostMapping()
    public Team createUser(@RequestBody @Valid CreateTeamRequest request) throws Exception {
        return service.createTeam(request);
    }

    @GetMapping()
    public List<Team> getTeams() {
        return service.getTeams();
    }

    @GetMapping("/{id}")
    public Team getUserById(@PathVariable @Valid @NotBlank String id) throws Exception {
        return service.getTeamById(id);
    }


    @PutMapping("/{id}")
    public Team updateUser(
            @PathVariable @Valid @NotBlank String id,
            @RequestBody @Valid UpdateTeamRequest userUpdateDto
    ) {
        return service.updateTeam(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable @Valid @NotBlank String id) {
        service.deleteTeam(id);
    }
}
