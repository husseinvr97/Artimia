package Artimia.com.entities;

import Artimia.com.enums.Context;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "fareed_dialogues")
public class FareedDialogues 
{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dialogue_id")
    private Long dialogueId;

    @Getter
    @Setter
    @Column(name = "dialogue_text", nullable = false, columnDefinition = "TEXT")
    private String dialogueText;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('WELCOME', 'CHECKOUT', 'HELP') DEFAULT 'HELP'")
    private Context context = Context.HELP;

    @Getter
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}