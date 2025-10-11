package br.com.casadocodigo.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        } else {
            startActivity(new Intent(this, ConfiguracoesActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        finish();
        return true;
    }
}
