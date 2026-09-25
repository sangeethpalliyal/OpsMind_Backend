package com.opsmind.deployment.controller;
import com.opsmind.deployment.model.Deployment;import com.opsmind.deployment.service.DeploymentService;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/deployments") public class DeploymentController{private final DeploymentService service;public DeploymentController(DeploymentService service){this.service=service;}record Request(Long analysisId,boolean green){}
 @PostMapping public Deployment deploy(@RequestBody Request r){return service.deploy(r.analysisId(),r.green());}
 @GetMapping public Collection<Deployment> all(){return service.all();}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","deployment-service","status","UP");}}
