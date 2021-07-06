package guru.sprfwk.repositories;

import guru.sprfwk.domain.Recipe;
import org.springframework.data.repository.CrudRepository;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
