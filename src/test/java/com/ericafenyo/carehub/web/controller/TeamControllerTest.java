/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Eric Afenyo
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

package com.ericafenyo.carehub.web.controller;

import com.ericafenyo.carehub.domain.service.TeamService;
import com.ericafenyo.carehub.fake.Generator;
import com.ericafenyo.carehub.services.AccountService;
import com.ericafenyo.carehub.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TeamControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private JwtUtils jwtUtils;  // Mock JwtUtils

    @MockBean
    private AccountService accountService;  // Mock JwtUtils

    @MockBean
    private TeamService service;  // Mock JwtUtils


    //
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void createTeam() {
//    }
//
//    @Test
//    void getTeams() {
//    }
//
//    @Test
//    void getUserById() {
//    }
//
//    @Test
//    void updateUser() {
//    }
//
//    @Test
//    void deleteTeam() {
//    }
//
//    @Test
//    void addMember() {
//    }
//
//    @Test
//    void getMember() {
//    }
//
//    @Test
//    void getMembership() {
//    }
//
//    @Test
//    void createTask() {
//    }
//
//    @Test
//    void createVitalReports() {
//        // Given a request object
//
//    }

//    @Test
//    void getVitalReports() {
//    }

    @Test
    void getVitalReports() throws Exception {
        var reports = List.of(Generator.generateFakeVitalReport(), Generator.generateFakeVitalReport());

        when(service.getVitalReports(Mockito.any(UUID.class))).thenReturn(reports);

        mvc.perform(get("/teams/{id}/vital-reports", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    var actual = objectMapper.readValue(response, List.class);
                    assert actual.size() == reports.size();
                });
    }
//
//    @Test
//    void getVitalReport() {
//    }
}