package in.jatindhankhar.bakingapp.network;

import java.util.List;

import in.jatindhankhar.bakingapp.model.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;

import static in.jatindhankhar.bakingapp.utils.Constants.JSON_PATH;

/**
 * Created by jatin on 1/19/18.
 */

public interface RecipeClient {
    // Use . since base url and target url are same
    @GET(JSON_PATH)
    Call<List<Recipes>> processRecipes();
}
