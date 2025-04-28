package Artimia.com.dtos.fareeddialogues;

import Artimia.com.enums.Context;
import java.time.LocalDateTime;

public record FareedDialogueGetDTO
(
    Long dialogueId,
    String dialogueText,
    Context context,
    LocalDateTime createdAt
) {}
