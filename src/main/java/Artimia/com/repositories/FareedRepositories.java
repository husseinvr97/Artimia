package Artimia.com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Artimia.com.entities.FareedDialogues;
import Artimia.com.enums.Context;

public interface FareedRepositories extends JpaRepository<FareedDialogues , Long>
{
    List<FareedDialogues> findByContext(Context context);
    List<FareedDialogues> findByDialogueTextContainingIgnoreCase(String keyword);
}
 