package br.com.casadocodigo.boaviagem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.casadocodigo.boaviagem.R;
import br.com.casadocodigo.boaviagem.domain.Anotacao;

public class AnotacaoFragment extends Fragment implements View.OnClickListener {
    private EditText dia, titulo, descricao;
    private Button botaoSalvar;
    private Anotacao anotacao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.anotacao, container, false);
    }

    public void setAnotacao(Anotacao anotacao) {
        this.anotacao = anotacao;
    }

    @Override
    public void onStart() {
        super.onStart();

        dia = (EditText) getActivity().findViewById(R.id.dia);
        titulo = (EditText) getActivity().findViewById(R.id.titulo);
        descricao = (EditText) getActivity().findViewById(R.id.descricao);
        botaoSalvar = (Button) getActivity().findViewById(R.id.salvar);
        botaoSalvar.setOnClickListener(this);

        if (anotacao != null) {
            prepararEdicao(anotacao);
        }
    }

    public void prepararEdicao(Anotacao anotacao) {
        setAnotacao(anotacao);
        dia.setText(anotacao.getDia().toString());
        titulo.setText(anotacao.getTitulo());
        descricao.setText(anotacao.getDescricao());
    }

    @Override
    public void onClick(View v) {
    }

    public void criarNovaAnotacao() {
        anotacao = new Anotacao();
        dia.setText("");
        titulo.setText("");
        descricao.setText("");
    }
}




