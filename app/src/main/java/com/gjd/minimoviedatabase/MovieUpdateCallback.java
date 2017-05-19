package com.gjd.minimoviedatabase;

import java.util.List;

/**
 * Interface for automatically updating UI with movie data
 */

public interface MovieUpdateCallback {
    void movieDataLoaded(List<Movie> movies);
}
