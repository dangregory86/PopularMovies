package gregory.dan.popularmovies.sqlData;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Daniel Gregory on 21/07/2018.
 */
@Database(entities = MovieFavourites.class, version = 3, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();

    // making the database a singleton
    private static MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movies")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
