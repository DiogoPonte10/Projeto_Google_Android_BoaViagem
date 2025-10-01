package br.com.casadocodigo.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void selecionarOpcao(View view) {
        if (view.getId() == R.id.nova_viagem) {
            startActivity(new Intent(this, ViagemActivity.class));
        } else if (view.getId() == R.id.novo_gasto) {
            startActivity(new Intent(this, GastoActivity.class));
        } else if (view.getId() == R.id.minhas_viagens) {
            startActivity(new Intent(this, ViagemListActivity.class));

        }
    }
}
