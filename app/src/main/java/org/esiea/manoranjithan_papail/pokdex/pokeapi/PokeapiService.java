package org.esiea.manoranjithan_papail.pokdex.pokeapi;

import org.esiea.manoranjithan_papail.pokdex.models.PokemonReponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonReponse> obtenirListePokemon(@Query("limit") int limit, @Query("offset") int offset);

}
