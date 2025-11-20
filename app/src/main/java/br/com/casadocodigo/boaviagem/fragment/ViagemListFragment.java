package br.com.casadocodigo.boaviagem.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.Arrays;
import java.util.List;

import br.com.casadocodigo.boaviagem.AnotacaoListener;
import br.com.casadocodigo.boaviagem.Constantes;

public class ViagemListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private AnotacaoListener callback;

    @Override
    public void onStart() {
        super.onStart();
        List<String> viagens = Arrays.asList("Campo Grande", "São Paulo", "Miami");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, viagens);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String viagem = (String) getListAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.VIAGEM_SELECIONADA, viagem);
        callback.viagemSelecionada(bundle);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        callback = (AnotacaoListener) activity;
    }
}
