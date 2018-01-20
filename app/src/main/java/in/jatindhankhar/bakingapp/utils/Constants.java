package in.jatindhankhar.bakingapp.utils;

/**
 * Created by jatin on 1/19/18.
 */

public final class Constants {
    private Constants(){
        // Avoid uncessary Instantiation
    }
    // Use this instead of original shortened url to avoid redirection overhead
    public static final String API_BASE_URL="https://d17h27t6h515a5.cloudfront.net";
    public static final String JSON_PATH = "/topher/2017/May/59121517_baking/baking.json";
    public static final int TAB_ITEMS = 2;
    public static final String JSON_STRING = "json_string";
    public static final String PREF_FILE = "BakePref"; // TODO - Set name programmatically
    public static final String IS_CACHED = "is_cached";


}
