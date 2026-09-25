package com.opsmind.deployment.service;
import com.opsmind.deployment.model.Deployment;import org.springframework.stereotype.Service;import java.util.*;import java.util.concurrent.ConcurrentHashMap;
@Service public class DeploymentService{private final Map<Long,Deployment> store=new ConcurrentHashMap<>();public Deployment deploy(Long id,boolean green){Deployment d=green?new Deployment(id,"STAGING_READY","http://localhost:9000/staging/"+id):new Deployment(id,"BLOCKED",null);store.put(id,d);return d;}public Collection<Deployment> all(){return store.values();}}
