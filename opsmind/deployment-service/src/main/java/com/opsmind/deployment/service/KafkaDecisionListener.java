package com.opsmind.deployment.service;
import org.springframework.kafka.annotation.KafkaListener;import org.springframework.stereotype.Component;import java.util.regex.*;
@Component public class KafkaDecisionListener{private final DeploymentService service;public KafkaDecisionListener(DeploymentService service){this.service=service;}
 @KafkaListener(topics="opsmind.analysis.completed",groupId="opsmind-deployment") public void onAnalysisCompleted(String id){try{service.deploy(Long.valueOf(id),true);}catch(NumberFormatException ignored){}}
}
