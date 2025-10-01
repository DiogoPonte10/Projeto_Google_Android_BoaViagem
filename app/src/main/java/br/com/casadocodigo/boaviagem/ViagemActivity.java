package br.com.casadocodigo.boaviagem;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class ViagemActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_viagem);
    }
}
