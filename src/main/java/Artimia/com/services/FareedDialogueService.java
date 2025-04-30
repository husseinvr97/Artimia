package Artimia.com.services;

import Artimia.com.dtos.fareeddialogues.FareedDialogueGetDTO;
import Artimia.com.entities.FareedDialogues;
import Artimia.com.enums.Context;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.mapper.FareedDialogueMapper;
import Artimia.com.repositories.FareedRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FareedDialogueService {

    private final FareedRepositories dialogueRepository;

    public List<FareedDialogueGetDTO> getAllDialogues() {
        return dialogueRepository.findAll().stream()
            .map(FareedDialogueMapper::toDto)
            .toList();
    }

    public FareedDialogueGetDTO getDialogueById(Long id) {
        return dialogueRepository.findById(id)
            .map(FareedDialogueMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Dialogue not found"));
    }

    public List<FareedDialogueGetDTO> getDialoguesByContext(Context context) {
        return dialogueRepository.findByContext(context).stream()
            .map(FareedDialogueMapper::toDto)
            .toList();
    }

    public FareedDialogueGetDTO updateDialogue(Long id, String text, Context context) {
        FareedDialogues dialogue = dialogueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dialogue not found"));
        
        dialogue.setDialogueText(text);
        dialogue.setContext(context);
        
        return FareedDialogueMapper.toDto(dialogueRepository.save(dialogue));
    }
}