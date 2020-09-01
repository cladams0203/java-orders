package com.lambdaschool.crudyorders.controllers;


import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentsController {


    @Autowired
    private AgentServices agentServices;

    //http://localhost:2019/agents/:id
    @GetMapping(value = "/agent/{agentid}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long agentid){
        Agent agent = agentServices.findAgentById(agentid);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
