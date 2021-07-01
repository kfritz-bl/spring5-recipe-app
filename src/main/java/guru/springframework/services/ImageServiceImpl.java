package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepo;

    public ImageServiceImpl(RecipeRepository recipeSvc) {
        this.recipeRepo = recipeSvc;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipeOpt = recipeRepo.findById(recipeId);

            if (!recipeOpt.isPresent()) {
                //todo impl error handling
                log.error("recipe id not found. Id: " + recipeId);
                throw new NotFoundException();
            }

            Recipe recipe = recipeOpt.get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) byteObjects[i++] = b;

            recipe.setImage(byteObjects);

            recipeRepo.save(recipe);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }
}
