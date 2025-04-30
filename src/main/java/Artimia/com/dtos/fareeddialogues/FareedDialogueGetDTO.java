package Artimia.com.dtos.fareeddialogues;

import Artimia.com.enums.Context;

public record FareedDialogueGetDTO
(
    Long dialogueId,
    String dialogueText,
    Context context
) {}
