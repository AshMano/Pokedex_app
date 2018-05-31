package org.esiea.manoranjithan_papail.pokdex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.esiea.manoranjithan_papail.pokdex.models.Pokemon;
import org.esiea.manoranjithan_papail.pokdex.models.PokemonReponse;
import org.esiea.manoranjithan_papail.pokdex.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RealPokedex extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListePokemonAdapter listePokemonAdapter;

    private int offset;

    private boolean readyToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realpokedex);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listePokemonAdapter = new ListePokemonAdapter(this);
        recyclerView.setAdapter(listePokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (readyToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " C'est fini");

                            readyToLoad = false;
                            offset += 20;
                            obtenirData(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        readyToLoad = true;
        offset = 0;
        obtenirData(offset);
    }

    private void obtenirData(int offset) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonReponse> pokemonReponseCall = service.obtenirListePokemon(20, offset);

        pokemonReponseCall.enqueue(new Callback<PokemonReponse>() {
            @Override
            public void onResponse(Call<PokemonReponse> call, Response<PokemonReponse> response) {
                readyToLoad = true;
                if (response.isSuccessful()) {

                    PokemonReponse pokemonReponse = response.body();
                    ArrayList<Pokemon> listePokemon = pokemonReponse.getResults();

                    listePokemonAdapter.ajouterListePokemon(listePokemon);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonReponse> call, Throwable t) {
                readyToLoad = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }


}
