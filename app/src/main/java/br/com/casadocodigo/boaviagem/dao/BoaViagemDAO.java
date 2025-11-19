package br.com.casadocodigo.boaviagem.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.casadocodigo.boaviagem.DatabaseHelper;
import br.com.casadocodigo.boaviagem.domain.Gasto;
import br.com.casadocodigo.boaviagem.domain.Viagem;

public class BoaViagemDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public BoaViagemDAO(Context context){
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb(){
        if(db == null){
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public List<Viagem> listarViagens(){
        Cursor cursor = getDb().query(DatabaseHelper.Viagem.TABELA,
                                      DatabaseHelper.Viagem.COLUNAS,
                                      null, null, null, null, null);
        List<Viagem> viagens = new ArrayList<Viagem>();
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem();
            viagens.add(viagem);
        }
        cursor.close();
        return viagens;
    }

    public Viagem buscarViagemPorId(Long id){
        Cursor cursor = getDb().query(DatabaseHelper.Viagem.TABELA,
                                      DatabaseHelper.Viagem.COLUNAS,
                                      DatabaseHelper.Viagem._ID + " = ? ",
                                      new String[]{id.toString()},
                                      null, null, null);

        if (cursor.moveToNext()) {
            Viagem viagem = criarViagem(cursor);
            cursor.close();
            return viagem;
        }

        return  null;
    }

    private Viagem criarViagem(Cursor cursor) {
        @SuppressLint("Range") Viagem viagem = new Viagem(
                cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.Viagem._ID)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.DESTINO)),

                cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.TIPO_VIAGEM)),

                new Date(cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.DATA_CHEGADA))),

                new Date(cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.DATA_SAIDA))),

                (int) cursor.getDouble(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.ORCAMENTO)),

                (double) cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.Viagem.QUANTIDADE_PESSOAS))
        );
        return viagem;
    }

    public long inserir(Viagem viagem){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Viagem.DESTINO,
                viagem.getDestino());

        values.put(DatabaseHelper.Viagem.TIPO_VIAGEM,
                viagem.getTipoViagem());

        values.put(DatabaseHelper.Viagem.DATA_CHEGADA,
                viagem.getDataChegada().getTime());

        values.put(DatabaseHelper.Viagem.DATA_SAIDA,
                viagem.getDataSaida().getTime());

        values.put(DatabaseHelper.Viagem.ORCAMENTO,
                viagem.getOrcamento());

        values.put(DatabaseHelper.Viagem.QUANTIDADE_PESSOAS,
                viagem.getQuantidadePessoas());

        return getDb().insert(DatabaseHelper.Viagem.TABELA,
                null, values);
    }

    public long atualizar(Viagem viagem){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Viagem.DESTINO,
                viagem.getDestino());

        values.put(DatabaseHelper.Viagem.TIPO_VIAGEM,
                viagem.getTipoViagem());

        values.put(DatabaseHelper.Viagem.DATA_CHEGADA,
                viagem.getDataChegada().getTime());

        values.put(DatabaseHelper.Viagem.DATA_SAIDA,
                viagem.getDataSaida().getTime());

        values.put(DatabaseHelper.Viagem.ORCAMENTO,
                viagem.getOrcamento());

        values.put(DatabaseHelper.Viagem.QUANTIDADE_PESSOAS,
                viagem.getQuantidadePessoas());

        return getDb().update(DatabaseHelper.Viagem.TABELA,
                values, DatabaseHelper.Viagem._ID + " = ?",
                new String[] {viagem.getId().toString()});
    }

    public long inserir(Gasto gasto){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Gasto.CATEGORIA,
                gasto.getCategoria());

        values.put(DatabaseHelper.Gasto.DATA,
                gasto.getData().getTime());

        values.put(DatabaseHelper.Gasto.DESCRICAO,
                gasto.getDescricao());

        values.put(DatabaseHelper.Gasto.LOCAL,
                gasto.getLocal());

        values.put(DatabaseHelper.Gasto.VALOR,
                gasto.getValor());

        values.put(DatabaseHelper.Gasto.VIAGEM_ID,
                gasto.getViagemId());

        return getDb().insert(DatabaseHelper.Gasto.TABELA,
                null, values);
    }

    public long atualizar(Gasto gasto){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Gasto.CATEGORIA,
                gasto.getCategoria());

        values.put(DatabaseHelper.Gasto.DATA,
                gasto.getData().getTime());

        values.put(DatabaseHelper.Gasto.DESCRICAO,
                gasto.getDescricao());

        values.put(DatabaseHelper.Gasto.LOCAL,
                gasto.getLocal());

        values.put(DatabaseHelper.Gasto.VALOR,
                gasto.getValor());

        values.put(DatabaseHelper.Gasto.VIAGEM_ID,
                gasto.getViagemId());

        return getDb().update(DatabaseHelper.Gasto.TABELA,
                values, DatabaseHelper.Gasto._ID + " = ?",
                new String[]{gasto.getId().toString()});
    }


    public void close(){
        helper.close();
    }
}
