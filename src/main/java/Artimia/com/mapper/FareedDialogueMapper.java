package Artimia.com.mapper;

import Artimia.com.dtos.fareeddialogues.FareedDialogueGetDTO;
import Artimia.com.entities.FareedDialogues;

public class FareedDialogueMapper {
    public static FareedDialogueGetDTO toDto(FareedDialogues dialogue) {
        return new FareedDialogueGetDTO(
            dialogue.getDialogueId(),
            dialogue.getDialogueText(),
            dialogue.getContext()
        );
    }
}