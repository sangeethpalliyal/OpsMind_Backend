package com.opsmind.legacy;
import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestController @RequestMapping("/legacy") public class LegacyController{
 @GetMapping public String home(){return "<html><body><h1>OpsMind Legacy Enterprise Portal</h1><p>This simulates a legacy Servlet/JSP-style corporate system.</p><p>Try /legacy/code</p></body></html>";}
 @GetMapping("/code") public Map<String,String> code(){return Map.of("language","legacy Java","status","READY_FOR_AI_REFACTOR","example","public void process(){ System.out.println(\\\"legacy\\\"); }");}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","legacy-service","status","UP");}
}
