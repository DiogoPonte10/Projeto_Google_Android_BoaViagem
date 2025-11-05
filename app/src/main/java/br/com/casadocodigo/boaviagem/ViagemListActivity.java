package br.com.casadocodigo.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemListActivity extends ListActivity implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener {

    private List<Map<String, Object>> viagens;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;
    private int viagemSelecionada;
    private DatabaseHelper helper;
    private SimpleDateFormat dateFormat;
    private Double valorLimite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new DatabaseHelper(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

        String valor = preferencias.getString("valor_limite", "-1");
        valorLimite = Double.valueOf(valor);

        String[] de = {"imagem", "destino", "data", "total", "barraProgresso"};
        int[] para = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor, R.id.barraProgresso};

        SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(),
                R.layout.lista_viagem, de ,para);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
//        setListAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, listarViagens()));
//        ListView listView = getListView();
//        listView.setOnItemClickListener(this);

        this.alertDialog = criaAlertDialog();
        this.dialogConfirmacao = criaDialogConfirmacao();

    }

    private List<Map<String, Object>> listarViagens() {
        viagens = new ArrayList<Map<String, Object>>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, " +
                "data_chegada, data_saida, orcamento FROM viagem", null);

        cursor.moveToFirst();

        viagens = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < cursor.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();

            String id = cursor.getString(0);
            int tipoViagem = cursor.getInt(1);
            String destino = cursor.getString(2);
            long dataChegada = cursor.getLong(3);
            long dataSaida = cursor.getLong(4);
            double orcamento = cursor.getDouble(5);

            item.put("id", id);

            if (tipoViagem == Constantes.VIAGEM_LAZER) {
                item.put("imagem", R.drawable.lazer);
            } else {
                item.put("imagem", R.drawable.negocios);
            }

            item.put("destino", destino);

            Date dataChegadaDate = new Date(dataChegada);
            Date dataSaidaDate = new Date(dataSaida);

            String periodo = dateFormat.format(dataChegadaDate) + " a " +
                    dateFormat.format(dataSaidaDate);

            item.put("data", periodo);

            double totalGasto = calcularTotalGasto(db, id);

            item.put("total", "Gasto total R$ " + totalGasto);

            double alerta = orcamento * valorLimite / 100;
            Double [] valores = new Double[] {orcamento, alerta, totalGasto};
            item.put("barraProgresso", valores);

            viagens.add(item);

            cursor.moveToNext();
        }
        cursor.close();

        return viagens;
//        Map<String, Object> item = new HashMap<String, Object>();
//        item.put("imagem", R.drawable.negocios);
//        item.put("destino", "São Paulo");
//        item.put("data", "02/02/2022 a 04/02/2022");
//        item.put("total", "Gasto total R$ 314,98");
//        item.put("barraProgresso", new Double[] {500.0, 450.0, 314.98});
//        viagens.add(item);
//
//        item = new HashMap<String, Object>();
//        item.put("imagem", R.drawable.lazer);
//        item.put("destino", "Maceió");
//        item.put("data", "14/05/2022 a 22/05/2022");
//        item.put("total", "Gasto total R$ 25.834,67");
//        item.put("barraProgresso", new Double[] {15000.0, 5450.0, 25834.67});
//        viagens.add(item);
    }
//    private List<String> listarViagens() {
//        return Arrays.asList("São Paulo", "Bonito", "Maceió", "Fortaleza");
//    }

    private double calcularTotalGasto(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM gasto WHERE ciagem_id = ?",
                new String[]{ id });

        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.viagemSelecionada = position;
        alertDialog.show();

//        Map<String, Object> map = viagens.get(position);
//        String destino = (String) map.get("destino");
//        String mensagem = "Viagem selecionada: " + destino;
//        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, GastoListActivity.class));

//        TextView textView = (TextView) view;
//        String mensagem = "Viagem selecionada: " + textView.getText();
//
//        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, GastoListActivity.class));
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                startActivity(new Intent(this, ViagemActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, GastoActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GastoListActivity.class));
                break;
            case 3:
                dialogConfirmacao.show();
                break;
                case DialogInterface.BUTTON_POSITIVE:
                    viagens.remove(this.viagemSelecionada);
                    getListView().invalidateViews();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialogConfirmacao.dismiss();
                    break;
        }
    }

    private AlertDialog criaAlertDialog() {
        final CharSequence[] itens = {getString(R.string.editar),
                                      getString(R.string.novo_gasto),
                                      getString(R.string.gastos_realizados),
                                      getString(R.string.remover)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(itens, this);

        return builder.create();
    }

    private AlertDialog criaDialogConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_viagem);
        builder.setPositiveButton(R.string.sim, this);
        builder.setNegativeButton(R.string.nao, this);

        return builder.create();
    }

}
