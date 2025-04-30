package Artimia.com.controllers;

import Artimia.com.dtos.fareeddialogues.FareedDialogueGetDTO;
import Artimia.com.enums.Context;
import Artimia.com.services.FareedDialogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fareed-dialogues")
@RequiredArgsConstructor
public class FareedController {

    private final FareedDialogueService dialogueService;

    @GetMapping
    public List<FareedDialogueGetDTO> getAllDialogues() {
        return dialogueService.getAllDialogues();
    }

    @GetMapping("/{id}")
    public FareedDialogueGetDTO getDialogueById(@PathVariable Long id) {
        return dialogueService.getDialogueById(id);
    }

    @GetMapping("/context/{context}")
    public List<FareedDialogueGetDTO> getDialoguesByContext(@PathVariable Context context) {
        return dialogueService.getDialoguesByContext(context);
    }

    @PutMapping("/{id}")
    public FareedDialogueGetDTO updateDialogue(
        @PathVariable Long id,
        @RequestParam String text,
        @RequestParam Context context
    ) {
        return dialogueService.updateDialogue(id, text, context);
    }

}
