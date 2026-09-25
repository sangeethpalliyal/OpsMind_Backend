package com.opsmind.analysis.service;
import org.springframework.stereotype.Service;
@Service public class CodeAnalyzer{
 public AnalysisResultData analyze(String code){int score=100;StringBuilder f=new StringBuilder();
  if(code==null)code="";
  if(code.matches("(?s).*password\\s*=.*")){score-=25;f.append("Possible hard-coded password. ");}
  if(code.matches("(?s).*System\\.out\\.println.*")){score-=5;f.append("Console output detected; prefer structured logging. ");}
  if(code.matches("(?s).*SELECT .*\\+.*")){score-=30;f.append("Possible SQL injection pattern. ");}
  if(code.matches("(?s).*for\\s*\\(.*for\\s*\\(.*")){score-=15;f.append("Nested loop detected; inspect time complexity. ");}
  if(code.contains("TODO")){score-=3;f.append("TODO marker found. ");}
  String risk=score>=85?"LOW":score>=65?"MEDIUM":"HIGH";String status=score>=70?"GREEN":"RED";
  if(f.length()==0)f.append("No rule-based issues detected. Review AI output and tests before merging.");
  return new AnalysisResultData(status,risk,Math.max(score,0),f.toString());
 }
 public record AnalysisResultData(String status,String risk,int score,String findings){}
}
