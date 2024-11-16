package com.example.employees.controller;
import com.example.employees.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/schema")
public class SchemaController {
    private final SchemaService schemaService;

    @Autowired
    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @GetMapping("/ddl")
    public Map<String, String> getSchemaDDL() {
        Map<String, String> response = new HashMap<>();
        try {
            String ddl = schemaService.getSchemaDDL();
            response.put("ddl", ddl);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
        }
        return response;
    }
}
