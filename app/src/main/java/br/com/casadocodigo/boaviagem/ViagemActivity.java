package br.com.casadocodigo.boaviagem;

import static br.com.casadocodigo.boaviagem.Constantes.NOME_CONTA;
import static br.com.casadocodigo.boaviagem.Constantes.PREFERENCIAS;
import static br.com.casadocodigo.boaviagem.Constantes.TOKEN_ACESSO;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.casadocodigo.boaviagem.calendar.CalendarService;
import br.com.casadocodigo.boaviagem.dao.BoaViagemDAO;
import br.com.casadocodigo.boaviagem.domain.Viagem;

public class ViagemActivity extends Activity {

    private Date dataChegada, dataSaida;
    private int ano, mes, dia;
    private Button dataChegadaButton, dataSaidaButton;
    private DatabaseHelper helper;
    private EditText destino, quantidadePessoas, orcamento;
    private RadioGroup radioGroup;
    private Long id;
    private CalendarService calendarService;
    private BoaViagemDAO dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_viagem);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataChegadaButton = (Button) findViewById(R.id.dataChegada);
        dataSaidaButton = (Button) findViewById(R.id.dataSaida);

        destino = (EditText) findViewById(R.id.destino);
        quantidadePessoas = (EditText) findViewById(R.id.quantidadePessoas);
        orcamento = (EditText) findViewById(R.id.orcamento);
        radioGroup = (RadioGroup) findViewById(R.id.tipoViagem);

        helper = new DatabaseHelper(this);

        dao = new BoaViagemDAO(this);

        id = getIntent().getLongExtra(Constantes.VIAGEM_ID, -1);

        if (id != -1){
            prepararEdicao();
        }

        calendarService = criarCalendarService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viagem_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        if (item.getItemId() == R.id.novo_gasto) {
            startActivity(new Intent(this, GastoActivity.class));
            return true;
        } else if (item.getItemId() == R.id.remover) {

            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (R.id.dataChegada == id) {
            return new DatePickerDialog(this, dataChegadaListener, ano, mes, dia);
        } else if (R.id.dataSaida == id){
            return new DatePickerDialog(this, dataSaidaListener, ano, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dataChegadaListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dataChegada = criarData(year, month, dayOfMonth);
            dataChegadaButton.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

    private DatePickerDialog.OnDateSetListener dataSaidaListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dataSaida = criarData(year, month, dayOfMonth);
            dataSaidaButton.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

    private Date criarData(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTime();
    }

    public void salvarViagem(View view) {
        Viagem viagem = new Viagem();
        viagem.setDestino(destino.getText().toString());
        viagem.setDataChegada(dataChegada);
        viagem.setDataSaida(dataSaida);
        viagem.setOrcamento(
                Double.valueOf(orcamento.getText().toString()));
        viagem.setQuantidadePessoas(
                Integer.valueOf(quantidadePessoas.getText().toString()));

        int tipo = radioGroup.getCheckedRadioButtonId();

        if(tipo == R.id.lazer){
            viagem.setTipoViagem(Constantes.VIAGEM_LAZER);
        }else{
            viagem.setTipoViagem(Constantes.VIAGEM_NEGOCIOS);
        }

//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("destino", destino.getText().toString());
//        values.put("data_chegada", dataChegada.getTime());
//        values.put("data_saida", dataSaida.getTime());
//        values.put("orcamento", orcamento.getText().toString());
//        values.put("quantidade_pessoas", quantidadePessoas.getText().toString());
//
//        int tipo = radioGroup.getCheckedRadioButtonId();
//        if (tipo == R.id.lazer) {
//            values.put("tipo_viagem", Constantes.VIAGEM_LAZER);
//        } else if (tipo == R.id.negocios) {
//            values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);
//        }

        long resultado;

        if (id == -1) {
            resultado = dao.inserir(viagem);
            new Task().execute(viagem);
        } else {
            resultado = dao.atualizar(viagem);
        }

        if(resultado != 1) {
            Toast.makeText(this, getString(R.string.registro_salvo),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.erro_salvar),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void prepararEdicao() {
        Viagem viagem = dao.buscarViagemPorId(id);
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT tipo_viagem, destino, data_chegada," +
//                "data_saida, quantidade_pessoas, orcamento FROM viagem WHERE _id = ?",
//                new String[] { id });
//
//        cursor.moveToFirst();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (viagem.getTipoViagem() == Constantes.VIAGEM_LAZER){
            radioGroup.check(R.id.lazer);
        } else {
            radioGroup.check(R.id.negocios);
        }

        destino.setText(viagem.getDstino());
        dataChegada = viagem.getDataChegada();
        dataSaida = viagem.getDataSaida();
        dataChegadaButton.setText(dateFormat.format(dataChegada));
        dataSaidaButton.setText(dateFormat.format(dataSaida));
        quantidadePessoas.setText(viagem.getQuantidadePessoas().toString());
        orcamento.setText(viagem.getOrcamenteo().toString());
    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    private CalendarService criarCalendarService() {
        SharedPreferences preferencias = getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        String nomeConta = preferencias.getString(NOME_CONTA, null);
        String tokenAcesso = preferencias.getString(TOKEN_ACESSO, null);

        return new CalendarService(nomeConta, tokenAcesso);
    }

    private class Task extends AsyncTask<Viagem, Void, Void> {
        @Override
        protected Void doInBackground(Viagem... viagens) {
            Viagem viagem = viagens[0];
            calendarService.criarEvento(viagem);
            return null;
        }
    }
}
