package com.opsmind.analysis.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="analysis_results") public class AnalysisResult{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private String repository;private String pullRequest;private String status;private String riskLevel;private int score;@Column(length=5000)private String findings;private Instant createdAt=Instant.now();
 public Long getId(){return id;}public String getRepository(){return repository;}public String getPullRequest(){return pullRequest;}public String getStatus(){return status;}public String getRiskLevel(){return riskLevel;}public int getScore(){return score;}public String getFindings(){return findings;}public Instant getCreatedAt(){return createdAt;}
 public void setRepository(String v){repository=v;}public void setPullRequest(String v){pullRequest=v;}public void setStatus(String v){status=v;}public void setRiskLevel(String v){riskLevel=v;}public void setScore(int v){score=v;}public void setFindings(String v){findings=v;}
}
