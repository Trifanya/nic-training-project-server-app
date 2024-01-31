package dev.trifanya.spring_webapp.activemq.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class GetObjectListRequest {
    private String requestTitle;
    private Map<String, String> filters;
    private String sortBy;
    private String sortDir;
}
