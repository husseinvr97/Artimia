package Artimia.com.entities;

import Artimia.com.enums.Context;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fareed_dialogues")
public class FareedDialogues 
{

    @Id
    @Column(name = "dialogue_id")
    private Long dialogueId;

    
    @Column(name = "dialogue_text", nullable = false, columnDefinition = "TEXT")
    private String dialogueText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('WELCOME', 'CHECKOUT', 'HELP') DEFAULT 'HELP'")
    private Context context = Context.HELP;

}