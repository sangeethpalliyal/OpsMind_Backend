package com.opsmind.analysis.controller;
import com.opsmind.analysis.model.AnalysisResult;import com.opsmind.analysis.repository.AnalysisResultRepository;import com.opsmind.analysis.service.*;import org.springframework.kafka.core.KafkaTemplate;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/analysis") public class AnalysisController{
 private final AnalysisResultRepository repo;private final CodeAnalyzer analyzer;private final AiReviewService ai;private final KafkaTemplate<String,String> kafka;
 public AnalysisController(AnalysisResultRepository repo,CodeAnalyzer analyzer,AiReviewService ai,KafkaTemplate<String,String> kafka){this.repo=repo;this.analyzer=analyzer;this.ai=ai;this.kafka=kafka;}
 record Webhook(String repository,String pullRequest,String code){}
 @PostMapping("/webhook") public AnalysisResult webhook(@RequestBody Webhook w){var a=analyzer.analyze(w.code());AnalysisResult r=new AnalysisResult();r.setRepository(w.repository());r.setPullRequest(w.pullRequest());r.setStatus(a.status());r.setRiskLevel(a.risk());r.setScore(a.score());r.setFindings(a.findings()+" AI: "+ai.review(w.code()));r=repo.save(r);try{kafka.send("opsmind.analysis.completed",String.valueOf(r.getId()));}catch(Exception ignored){}return r;}
 @GetMapping public List<AnalysisResult> all(){return repo.findAll();}
 @GetMapping("/{id}") public AnalysisResult one(@PathVariable Long id){return repo.findById(id).orElseThrow();}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","analysis-service","status","UP");}
}
