package gregory.dan.popularmovies;

/**
 * Created by Daniel Gregory on 22/07/2018.
 */
public class MovieTrailer {

    public String getTrailerCode() {
        return trailerCode;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String trailerCode;
    public String trailerName;

    public MovieTrailer(String _trailerCode, String _trailerName){
        trailerCode = _trailerCode;
        trailerName = _trailerName;
    }
}
