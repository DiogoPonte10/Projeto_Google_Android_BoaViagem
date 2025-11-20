package br.com.casadocodigo.boaviagem.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.boaviagem.AnotacaoListener;
import br.com.casadocodigo.boaviagem.Constantes;
import br.com.casadocodigo.boaviagem.R;
import br.com.casadocodigo.boaviagem.domain.Anotacao;

public class AnotacaoListFragment extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private AnotacaoListener callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_anotacoes, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button button = (Button) getActivity().findViewById(R.id.nova_anotacao);
        button.setOnClickListener(this);

        getListView().setOnItemClickListener(this);
        listarAnotacoesPorViagem(getArguments());
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        callback = (AnotacaoListener) activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Anotacao anotacao = (Anotacao) getListAdapter().getItem(position);
        callback.anotacaoSelecionada(anotacao);
    }

    @Override
    public void onClick(View v) {
        callback.novaAnotacao();
    }

    private List<Anotacao> listarAnotacoes() {
        List<Anotacao> anotacoes = new ArrayList<Anotacao>();

        for (int I = 1; I <= 20; I++) {
            Anotacao anotacao = new Anotacao();

            anotacao.setDia(I);
            anotacao.setTitulo("Anotacao: " + I);
            anotacao.setDescricao("Descricao: " + I);
            anotacoes.add(anotacao);
        }

        return anotacoes;
    }

    public void listarAnotacoesPorViagem(Bundle bundle) {
        if (bundle != null && bundle.containsKey(Constantes.VIAGEM_SELECIONADA)) {
            List<Anotacao> anotacoes = listarAnotacoes();

            ArrayAdapter<Anotacao> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, anotacoes);

            setListAdapter(adapter);
        }

    }
}
