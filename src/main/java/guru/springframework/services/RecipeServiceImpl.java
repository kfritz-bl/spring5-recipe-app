package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.CommandToRecipe;
import guru.springframework.converters.RecipeToCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final CommandToRecipe cmdToRecipe;
    private final RecipeToCommand recipeToCmd;

    public RecipeServiceImpl(RecipeRepository recipeRepo,
                             CommandToRecipe cmdToRecipe,
                             RecipeToCommand recipeToCmd) {
        this.recipeRepo = recipeRepo;
        this.cmdToRecipe = cmdToRecipe;
        this.recipeToCmd = recipeToCmd;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting all recipes");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepo.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        log.debug("Searching for recipe id " + id);
        Optional<Recipe> recipeOpt = recipeRepo.findById(id);
        if (!recipeOpt.isPresent()) throw new NotFoundException("Recipe not found for ID value: " + id);

        return recipeOpt.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        log.debug("Searching for RecipeCommand by recipe id " + id);

        return recipeToCmd.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand cmd) {
        log.debug("Saving RecipeCommand with recipe id " + cmd.getId());
        Recipe detachedRecipe = cmdToRecipe.convert(cmd);
        assert detachedRecipe != null;
        Recipe savedRecipe = recipeRepo.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return recipeToCmd.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting RecipeCommand with recipe id " + id);
        recipeRepo.deleteById(id);
    }
}
