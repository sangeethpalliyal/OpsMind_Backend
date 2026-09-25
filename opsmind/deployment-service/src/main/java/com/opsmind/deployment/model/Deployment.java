package com.opsmind.deployment.model;
import java.time.Instant;
public class Deployment{private Long analysisId;private String status;private String environmentUrl;private Instant createdAt=Instant.now();public Deployment(){}public Deployment(Long id,String status,String url){analysisId=id;this.status=status;environmentUrl=url;}public Long getAnalysisId(){return analysisId;}public String getStatus(){return status;}public String getEnvironmentUrl(){return environmentUrl;}public Instant getCreatedAt(){return createdAt;}}
