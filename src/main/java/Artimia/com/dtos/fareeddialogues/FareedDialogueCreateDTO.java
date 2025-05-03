package Artimia.com.dtos.fareeddialogues;

import Artimia.com.enums.Context;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FareedDialogueCreateDTO
(
    @NotBlank(message = "Dialogue text cannot be blank")
    String dialogueText,
    
    @NotNull(message = "Context cannot be null")
    Context context
) {}
