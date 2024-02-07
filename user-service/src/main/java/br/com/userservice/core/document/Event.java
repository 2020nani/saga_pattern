package br.com.userservice.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
        @Id
        private String id;
        private String userId;
        private String transactionId;
        private User payload;
        private String source;
        private String status;
        private List<History> eventHistory;
        private LocalDateTime createdAt;
}
